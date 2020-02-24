package com.guoyi.listeninglove.ui.download.ui;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.ui.base.BaseLazyFragment;
import com.guoyi.listeninglove.common.Constants;
import com.guoyi.listeninglove.ui.main.PageAdapter;

import butterknife.BindView;

/**
 * Created by yonglong on 2016/11/26.
 */

public class DownloadFragment extends BaseLazyFragment {
    @BindView(R.id.m_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static DownloadFragment newInstance(Boolean isCache) {
        Bundle args = new Bundle();
        DownloadFragment fragment = new DownloadFragment();
        args.putBoolean(Constants.KEY_IS_CACHE, isCache);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    public void initViews() {
        mToolbar.setTitle(getResources().getString(R.string.item_download));
        if (getActivity() != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(mToolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initInjector() {

    }

    private void setupViewPager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getChildFragmentManager());
//        adapter.addFragment(DownloadedFragment.newInstance(true), getString(R.string.cache_complete));
        adapter.addFragment(DownloadedFragment.Companion.newInstance(false), getString(R.string.download_complete));
        adapter.addFragment(DownloadManagerFragment.Companion.newInstance(), getString(R.string.download_processing));
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onLazyLoad() {
        setupViewPager(viewPager);
        mTabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
    }
}
