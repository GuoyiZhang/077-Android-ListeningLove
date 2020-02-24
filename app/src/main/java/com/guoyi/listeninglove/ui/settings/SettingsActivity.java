package com.guoyi.listeninglove.ui.settings;


import android.content.Intent;
import android.view.MenuItem;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity {


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.Companion.newInstance()).commit();
    }

    @Override
    protected void initInjector() {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
