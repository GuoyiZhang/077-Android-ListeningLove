package com.guoyi.listeninglove.ui.music.local.fragment;

import android.content.Context;
import android.content.Intent;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.ui.base.BaseActivity;
import com.guoyi.listeninglove.ui.base.BasePresenter;
import com.guoyi.listeninglove.common.Extras;
import com.guoyi.listeninglove.common.NavigationHelper;
import com.guoyi.listeninglove.common.NavigationHelper;

/**
 * Created by Monkey on 2015/6/29.
 */
public class LocalVideoActivity extends BaseActivity<BasePresenter> {

    @Override
    protected int getLayoutResID() {
        return R.layout.include_main;
    }

    @Override
    protected String setToolbarTitle() {
        return getResources().getString(R.string.item_video);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        NavigationHelper.INSTANCE.navigateToVideo(this, null);
    }

    @Override
    protected void initInjector() {

    }

    public static void newInstance(Context context, String folderPath) {
        Intent intent = new Intent(context, LocalVideoActivity.class);
        intent.putExtra(Extras.FOLDER_PATH, folderPath);
        context.startActivity(intent);
    }
}
