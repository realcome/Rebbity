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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.meizu.flyme.blur.drawable.BlurDrawable;
import com.meizu.flyme.reflect.ActionBarProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by Tyler on 15/2/1.
 */
public class ActionBarUtils {

    public static void setNaviBackButtonDrawable(ActionBar actionBar, Drawable drawable) {
        try {
            Class.forName("com.android.internal.app.ActionBarImpl").getMethod("setBackButtonDrawable", new Class[] { Drawable.class }).invoke(actionBar, new Object[]{drawable});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void setFullWindow(Activity activity, boolean isfullwindow) {
        try {
            int feature;
            Object localObject;
            int origin;
            if (Build.VERSION.SDK_INT < 19)
            {
                feature = WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
                localObject = activity.getWindow().getAttributes().getClass().getDeclaredField("meizuFlags");
            } else {
                Field localField = activity.getWindow().getAttributes().getClass().getDeclaredField("flags");
                localObject = localField;
                feature = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            }



            ((Field)localObject).setAccessible(true);
            origin = ((Field)localObject).getInt(activity.getWindow().getAttributes());

            int params = origin & (feature ^ 0xFFFFFFFF);

            if (isfullwindow) {
                params = origin | feature;
            }

            ((Field)localObject).setInt(activity.getWindow().getAttributes(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setEnabledBackWhenOverlay(ActionBar paramActionBar, boolean paramBoolean) {
        Class localClass;
        try {
            localClass = Class.forName("com.android.internal.app.ActionBarImpl");
            Class[] arrayOfClass = new Class[1];
            arrayOfClass[0] = Boolean.TYPE;
            Method localMethod = localClass.getMethod("setEnabledBackWhenOverlay", arrayOfClass);
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(paramBoolean);
            localMethod.invoke(paramActionBar, arrayOfObject);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static void hideTopActionBar(Activity paramActivity)
    {
//        ActionBar localActionBar = paramActivity.getActionBar();
//        int i = localActionBar.getDisplayOptions();
//        if ((i & 0x10) == 0)
//            localActionBar.setDisplayOptions(i | 0x10);
//        View localView1 = localActionBar.getCustomView();
//        if (localView1 == null)
//        {
//            localView1 = new View(paramActivity);
//            localActionBar.setCustomView(localView1, new ActionBar.LayoutParams(-1, -1, 17));
//        }
//        View localView2 = (View)localView1.getParent();
//        if (localView2 != null)
//        {
//            View localView3 = (View)localView2.getParent();
//            if (localView3 != null)
//                localView3.setVisibility(8);
//        }
    }

    public static int getStatusBarHeight(Context context) {
        Class localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int size = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
            return context.getResources().getDimensionPixelSize(size);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return 144;
    }

    public static int getActionBarHeight(Context context, ActionBar actionBar)
    {
        TypedValue localTypedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, localTypedValue, true))
            return TypedValue.complexToDimensionPixelSize(localTypedValue.data, context.getResources().getDisplayMetrics());

        if (actionBar != null)
            return actionBar.getHeight();

        return 0;
    }

    private static Drawable createDrawable(int bgcolor, int dividerColor, Rect paramRect)
    {
        ColorDrawable localColorDrawable = new ColorDrawable(dividerColor);
        BlurDrawable localBlurDrawable = new BlurDrawable();
        localBlurDrawable.setColorFilter(bgcolor, PorterDuff.Mode.SRC_OVER);
        LayerDrawable localLayerDrawable = new LayerDrawable(new Drawable[] { localBlurDrawable, localColorDrawable });
        localLayerDrawable.setLayerInset(1, paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
        return localLayerDrawable;
    }

    public static void setActionModeHeaderHidden(ActionBar paramActionBar, boolean paramBoolean)
    {
        Class localClass;
        try
        {
            localClass = Class.forName("com.android.internal.app.ActionBarImpl");
            Class[] arrayOfClass = new Class[1];
            arrayOfClass[0] = Boolean.TYPE;
            Method localMethod = localClass.getMethod("setActionModeHeaderHidden", arrayOfClass);
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(paramBoolean);
            localMethod.invoke(paramActionBar, arrayOfObject);
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
    }
}
