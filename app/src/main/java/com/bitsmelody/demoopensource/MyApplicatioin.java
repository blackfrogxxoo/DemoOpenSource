package com.bitsmelody.demoopensource;

import android.app.Application;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

/**
 * Created by black on 2016/3/2.
 */
public class MyApplicatioin extends Application {
    private static MyApplicatioin mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    public static MyApplicatioin getInstance() {
        return mContext;
    }
}
