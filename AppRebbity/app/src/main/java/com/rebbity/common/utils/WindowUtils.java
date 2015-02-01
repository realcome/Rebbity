/*
 * Copyright (c) 2015.
 *
 * If you want to use this file, you must retain this part of the statement。
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rebbity.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by Tyler on 15/2/1.
 */
public class WindowUtils {
    public static void attachView(Window window, View view) {
        ViewGroup rootView = (ViewGroup) window.getDecorView();

        rootView.addView(view);
    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getActionBarSize(Context context)
    {
        int[] attrs = { android.R.attr.actionBarSize };
        TypedArray values = context.getTheme().obtainStyledAttributes(attrs);
        try
        {
            return values.getDimensionPixelSize(0, 0);//第一个参数数组索引，第二个参数 默认值
        }
        finally
        {
            values.recycle();
        }
    }

    public static void setDarkStatusIconForFlyme(Activity activity, boolean isDark) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            Field localField1 = layoutParams.getClass().getDeclaredField("meizuFlags");
            Field localField2 = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            localField1.setAccessible(true);
            localField2.setAccessible(true);
            int mask = localField2.getInt(null);
            int value = localField1.getInt(layoutParams);

            int dstVal = value & (mask ^ 0xFFFFFFFF);

            if (isDark) {
                dstVal = value | mask;
            }

            localField1.setInt(layoutParams, dstVal);
            window.setAttributes(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
