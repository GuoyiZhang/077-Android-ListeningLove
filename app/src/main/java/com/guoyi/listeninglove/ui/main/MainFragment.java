package com.guoyi.listeninglove.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.ui.base.BaseFragment;
import com.guoyi.listeninglove.ui.music.charts.fragment.ChartsDetailFragment;
import com.guoyi.listeninglove.ui.music.discover.DiscoverFragment;
import com.guoyi.listeninglove.ui.music.mv.MvFragment;
import com.guoyi.listeninglove.ui.music.my.MyMusicFragment;
import com.google.android.material.tabs.TabLayout;
import com.guoyi.listeninglove.ui.music.charts.fragment.ChartsDetailFragment;
import com.guoyi.listeninglove.ui.music.discover.DiscoverFragment;
import com.guoyi.listeninglove.ui.music.my.MyMusicFragment;

import butterknife.BindView;

/**
 * 作者：yonglong on 2016/8/8 17:47
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
@SuppressWarnings("ConstantConditions")
public class MainFragment extends BaseFragment {
    @BindView(R.id.m_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    public void initViews() {
        if (getActivity() != null) {
            mToolbar.setTitle(R.string.app_name);
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(mToolbar);
            final ActionBar toggle = appCompatActivity.getSupportActionBar();
            if (toggle != null) {
                toggle.setHomeAsUpIndicator(R.drawable.ic_menu_white);
                toggle.setDisplayHomeAsUpEnabled(true);
            }
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        setupViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    mTabLayout.setVisibility(View.GONE);
                    mToolbar.setTitle("音乐MV");
                } else {
                    mTabLayout.setVisibility(View.VISIBLE);
                    mToolbar.setTitle("乐听乐爱");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initInjector() {

    }

    private void setupViewPager(ViewPager mViewPager) {
        PageAdapter mAdapter = new PageAdapter(getChildFragmentManager());
        mAdapter.addFragment(MyMusicFragment.Companion.newInstance(), getContext().getString(R.string.my));
        mAdapter.addFragment(DiscoverFragment.Companion.newInstance(), getContext().getString(R.string.discover));
        mAdapter.addFragment(ChartsDetailFragment.Companion.newInstance(), getContext().getString(R.string.charts));
        mAdapter.addFragment(MvFragment.newInstance(), getContext().getString(R.string.mv));
        mViewPager.setAdapter(mAdapter);
    }

}

