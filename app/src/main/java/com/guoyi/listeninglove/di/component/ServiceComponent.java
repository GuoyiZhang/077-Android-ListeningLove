package com.guoyi.listeninglove.di.component;

import android.content.Context;


import com.guoyi.listeninglove.di.module.ServiceModule;
import com.guoyi.listeninglove.di.scope.ContextLife;
import com.guoyi.listeninglove.di.scope.PerService;

import dagger.Component;


/**
 * Created by lw on 2017/1/19.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
