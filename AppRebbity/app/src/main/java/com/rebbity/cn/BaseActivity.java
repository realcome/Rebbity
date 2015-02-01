/*
 * Copyright (c) 2015.
 *
 * If you want to use this file, you must retain this part of the statement。
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rebbity.cn;

import com.meizu.flyme.blur.drawable.BlurDrawable;
import com.meizu.flyme.reflect.ActionBarProxy;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.rebbity.common.utils.ActionBarUtils;
import com.rebbity.common.utils.WindowUtils;
import com.rebbity.config.APP_PREF;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Tyler on 15/1/1.
 */
public class BaseActivity extends Activity{
    public static final int TOOLBAR_SPLIT_HEIGHT = 3;
    private int m_toolbar_bg_color;
    private int m_toolbar_split_color;
    private int m_toolbar_height;

    @Override
    public void setContentView(int layoutResID)
    {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        if (isFlyme()) {
            getWindow().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        }

        super.setContentView(layoutResID);
        int bgcolor = getResources().getColor(R.color.windowbg);
        this.m_toolbar_bg_color = Color.argb(0x88, Color.red(bgcolor), Color.green(bgcolor), Color.blue(bgcolor));
        this.m_toolbar_split_color = getResources().getColor(R.color.toolbar_split_default);
        this.m_toolbar_height = 0;
        ActionBarUtils.setDarkStatusIconForFlyme(this, true);
        invalidateToolbar();
    }

    public void setSplitColor(int color, boolean immediately)
    {
        this.m_toolbar_split_color = color;
        if (immediately)
            invalidateToolbar();
    }

    public void setToolbarColor(int color, boolean immediately)
    {
        this.m_toolbar_bg_color = color;
        if (immediately)
            invalidateToolbar();
    }

    public void hideTitle()
    {
        ActionBar localActionBar = getActionBar();
        if (localActionBar != null)
            localActionBar.setDisplayShowTitleEnabled(false);
    }

    public void setToolbarLogo(int paramInt)
    {
        ActionBar localActionBar = getActionBar();
        if (localActionBar != null)
            localActionBar.setLogo(getResources().getDrawable(paramInt));
    }

    public void invalidateToolbar()
    {
        if (getActionBar() == null) {
            return;
        }

        ActionBar localActionBar = getActionBar();
        int bar_height = 0;

        if (localActionBar != null)
            bar_height = ActionBarProxy.getActionBarHeight(this, localActionBar);

        bar_height = Math.max(m_toolbar_height, bar_height);
        showToast(this, "" + bar_height + " " + ActionBarUtils.getStatusBarHeight(this), Toast.LENGTH_LONG);


        BlurDrawable bgdrawable = new BlurDrawable();
        bgdrawable.setColorFilter(m_toolbar_bg_color, PorterDuff.Mode.SRC_OVER);
        Drawable[] arrayOfDrawable = new Drawable[2];
        arrayOfDrawable[0] = bgdrawable;
        arrayOfDrawable[1] = new ColorDrawable(m_toolbar_split_color);

        ViewGroup rootlayout = (ViewGroup) findViewById(R.id.root_layout);

        int bgViewHeight = bar_height + ActionBarUtils.getStatusBarHeight(this);
        if (rootlayout != null) {
            localActionBar.setBackgroundDrawable(new ColorDrawable(0x00000000));
            transStatusBarAndNaviBar();


            LayerDrawable localLayerDrawable = new LayerDrawable(arrayOfDrawable);
            localLayerDrawable.setLayerInset(0, 0, 0, 0, TOOLBAR_SPLIT_HEIGHT);
            localLayerDrawable.setLayerInset(1, 0, bgViewHeight - TOOLBAR_SPLIT_HEIGHT, 0, 0);
            View view = new View(this);

            view.setBackground(localLayerDrawable);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bgViewHeight));
            rootlayout.addView(view);
        } else {
            if (!isFlyme()) {
                transStatusBarAndNaviBar();
            }

            LayerDrawable localLayerDrawable = new LayerDrawable(arrayOfDrawable);
            localLayerDrawable.setLayerInset(0, 0, 0, 0, TOOLBAR_SPLIT_HEIGHT);
            localLayerDrawable.setLayerInset(1, 0, bar_height - TOOLBAR_SPLIT_HEIGHT, 0, 0);
            localActionBar.setBackgroundDrawable(localLayerDrawable);
        }

        localActionBar.setSplitBackgroundDrawable(bgdrawable);

    }

    public static Toast showToast(Context context, String text, int duration) {
		// This function is used for debug log display.
		Toast.makeText(context, text, duration).show();
		return null;
	}
	
	public static Toast showToast(Context context, int res, int duration) {
		// This function is used for debug log display.
		Toast.makeText(context, res, duration).show();
		return null;
	}
	
	public static Class<?> getStartActivity(Context context) {
		if(APP_PREF.IsFirst(context)){
			return com.rebbity.cn.GuideActivity.class;
		}else{
			return com.rebbity.cn.SplashActivity.class;
		}
	}
	
	public static boolean changeStartState(Context context) {
		return APP_PREF.ChangeFirst(context);
	}

    public static boolean isSupportTransStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return false;
        }

        return true;
    }

    public static boolean isSupportMaterialDesign() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return true;
        }

        return false;
    }

    private void transStatusBarAndNaviBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        BlurDrawable naviDl = new BlurDrawable();
        naviDl.setColorFilter(this.m_toolbar_bg_color, PorterDuff.Mode.SRC_OVER);
        tintManager.setNavigationBarTintDrawable(naviDl);
    }

    public static boolean isFlyme() {
        return RebbityApp.isFlyme();
    }

    public static int getActionBarHeight(Activity ac) {
        ActionBar localActionBar = ac.getActionBar();
        int bar_height = 0;

        if (localActionBar != null)
            bar_height = ActionBarProxy.getActionBarHeight(ac, localActionBar);
        return bar_height;
    }
}
