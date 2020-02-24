package com.guoyi.listeninglove.ui.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.guoyi.listeninglove.MusicApp;
import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.di.component.ActivityComponent;
import com.guoyi.listeninglove.di.component.DaggerActivityComponent;
import com.guoyi.listeninglove.di.module.ActivityModule;
import com.guoyi.listeninglove.event.MetaChangedEvent;
import com.guoyi.listeninglove.player.PlayManager;
import com.guoyi.listeninglove.ui.theme.ThemeStore;
import com.guoyi.listeninglove.utils.LogUtil;
import com.guoyi.listeninglove.IMusicService;
import com.guoyi.listeninglove.event.MetaChangedEvent;
import com.guoyi.listeninglove.utils.AnimationUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

import static com.guoyi.listeninglove.utils.AnimationUtils.animateView;

/**
 * 基类
 *
 * @author yonglong
 * @date 2016/8/3
 */
public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements ServiceConnection, BaseContract.BaseView {

    @Nullable
    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;

    @Nullable
    @BindView(R.id.loadingView)
    public ViewStub loadingStubView;

    public View loadingView;

    public View emptyStateView;
    public View errorPanelRoot;
    public Button errorButtonRetry;
    public TextView errorTextView;
    public ProgressBar loadingProgressBar;

    @Nullable
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    protected Handler mHandler;
    private Unbinder unbinder;
    private PlayManager.ServiceToken mToken;
    public Boolean isPause = true;

    private List<Disposable> disposables = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setUpTheme();
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mToken = PlayManager.bindToService(this, this);
        setContentView(R.layout.activity_base);
        View view = LayoutInflater.from(this).inflate(getLayoutResID(), findViewById(R.id.rootParent));
//        //初始化黄油刀控件绑定框架
//        unbinder = ButterKnife.bind(this, view);
        //初始化黄油刀控件绑定框架
        unbinder = ButterKnife.bind(this);
        mHandler = new Handler();
        initActivityComponent();
        initInjector();
        initToolBar();
        attachView();
        initView();
    }

    private void initLoading() {
        if (loadingView == null && loadingStubView != null) {
            loadingView = loadingStubView.inflate();
            emptyStateView = loadingView.findViewById(R.id.empty_state_view);
            errorPanelRoot = loadingView.findViewById(R.id.error_panel);
            errorButtonRetry = loadingView.findViewById(R.id.error_button_retry);
            errorTextView = loadingView.findViewById(R.id.error_message_view);
            loadingProgressBar = loadingView.findViewById(R.id.loading_progress_bar);
        }
    }

    /**
     * 初始化Dagger
     */
    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MusicApp.getInstance().getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    private void initToolBar() {
        if (hasToolbar() && mToolbar != null) {
            if (setToolbarTitle() != null)
                mToolbar.setTitle(setToolbarTitle());
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (mSwipeRefreshLayout != null) {
            //设置刷新球颜色
            mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#05b962"), Color.parseColor("#F4B400"), Color.parseColor("#DB4437"));
            mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 更新title
     *
     * @param title
     */
    protected void updateTitle(String title) {
        if (hasToolbar() && mToolbar != null) {
            if (setToolbarTitle() != null)
                mToolbar.setTitle(title);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutResID();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initInjector();

    protected void listener() {
    }


    protected void retryLoading() {
    }

    protected boolean hasToolbar() {
        return true;
    }

    protected String setToolbarTitle() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mToken != null) {
            PlayManager.unbindFromService(mToken);
            mToken = null;
        }
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        detachView();
    }


    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        initLoading();
        if (emptyStateView != null) AnimationUtils.animateView(emptyStateView, false, 150);
        if (loadingProgressBar != null) AnimationUtils.animateView(loadingProgressBar, true, 400);
        AnimationUtils.animateView(errorPanelRoot, false, 150);
    }

    @Override
    public void hideLoading() {
        if (emptyStateView != null) AnimationUtils.animateView(emptyStateView, false, 150);
        if (loadingProgressBar != null) AnimationUtils.animateView(loadingProgressBar, false, 0);
        AnimationUtils.animateView(errorPanelRoot, false, 150);
    }

    @Override
    public void showEmptyState() {
        initLoading();
        if (emptyStateView != null) AnimationUtils.animateView(emptyStateView, true, 200);
        if (loadingProgressBar != null) AnimationUtils.animateView(loadingProgressBar, false, 0);
        AnimationUtils.animateView(errorPanelRoot, false, 150);
    }

    @Override
    public void showError(String message, boolean showRetryButton) {
        initLoading();
        hideLoading();
        if (errorTextView != null)
            errorTextView.setText(message);
        if (errorButtonRetry != null)
            errorButtonRetry.setOnClickListener(v -> retryLoading());
        if (showRetryButton) AnimationUtils.animateView(errorButtonRetry, true, 600);
        else AnimationUtils.animateView(errorButtonRetry, false, 0);
        AnimationUtils.animateView(errorPanelRoot, true, 300);
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isPause = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isPause = true;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        PlayManager.mService = IMusicService.Stub.asInterface(iBinder);
        listener();
        initData();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        PlayManager.mService = null;
        LogUtil.d("BaseActivity", "onServiceDisconnected");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDefaultEvent(MetaChangedEvent event) {
    }

    private void setUpTheme() {
        ThemeStore.THEME_MODE = ThemeStore.getThemeMode();
        if (ThemeStore.THEME_MODE == ThemeStore.NIGHT) {
            setTheme(R.style.MyThemeDark);
        } else {
            setTheme(R.style.MyThemeBlue);
        }
    }


    /**
     * https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     * 屏幕适配
     */
    private void setCustomDensity(Activity activity) {
        DisplayMetrics appDisplayMetrics = MusicApp.getAppContext().getResources().getDisplayMetrics();
        //density = px/dp dp值是设计图的宽度
        float targetDensity = appDisplayMetrics.widthPixels / 360f;
        int targetDensityDpi = (int) (160 * targetDensity);
        float targetScaledDensity = targetDensity * (appDisplayMetrics.scaledDensity / appDisplayMetrics.density);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        density = targetDensity;
    }

    private float density = 0f;

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1f) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        //同步这只density，解决横屏息屏后UI问题，问题原因是息屏后density变化导致， 推出原因是谷歌广告造成，目前只想到这种方式来解决
        if (density != 0f) {
            res.getDisplayMetrics().density = density;
        }
        return res;
    }

}
