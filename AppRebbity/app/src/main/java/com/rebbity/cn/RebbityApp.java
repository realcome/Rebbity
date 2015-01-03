package com.rebbity.cn;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tyler on 15/1/2.
 */
public class RebbityApp extends Application {
    private static Context g_context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        g_context = this;
    }

    public static Context getG_context(){
        return g_context;
    }
}
