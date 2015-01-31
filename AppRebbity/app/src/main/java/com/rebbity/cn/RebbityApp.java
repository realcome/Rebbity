package com.rebbity.cn;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by Tyler on 15/1/2.
 */
public class RebbityApp extends Application {
    private static Context g_context = null;
    private static boolean g_isFlymeDevice;

    @Override
    public void onCreate() {
        super.onCreate();
        g_context = this;
        g_isFlymeDevice = judgeFlymeDevice();
    }

    public static Context getG_context(){
        return g_context;
    }

    public static boolean isFlyme() {
        return g_isFlymeDevice;
    }

    private static boolean judgeFlymeDevice() {
        String device = "";
        try {
            String devicename="ro.product.device";
            Method method = Class.forName("android.os.SystemProperties").getMethod("get",String.class);
            device=(String) method.invoke(null, devicename);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(device.contains("mx"))
            return true;
        else
            return false;
    }
}
