package com.guoyi.listeninglove.di.module;

import android.app.Activity;
import android.content.Context;

import com.guoyi.listeninglove.di.scope.ContextLife;
import com.guoyi.listeninglove.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lw on 2017/1/19.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
