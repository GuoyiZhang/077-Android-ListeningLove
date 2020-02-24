package com.guoyi.listeninglove.di.component;

import android.content.Context;

import com.guoyi.listeninglove.di.module.ApplicationModule;
import com.guoyi.listeninglove.di.scope.ContextLife;
import com.guoyi.listeninglove.di.scope.PerApp;
import dagger.Component;


/**
 * Created by lw on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}