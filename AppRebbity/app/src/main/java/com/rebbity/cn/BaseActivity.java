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
import com.rebbity.config.APP_PREF;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by Tyler on 15/1/1.
 */
public class BaseActivity extends ActionBarActivity{
    public static final int TOOLBAR_SPLIT_HEIGHT = 3;
    public static final int TOOLBAR_BG_ALPHA = 100;

    private BlurDrawable m_toolbar_bg_drawable;
    private Drawable m_toolbar_split_drawable;

    private int m_toolbar_bg_color;
    private int m_toolbar_split_color;

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);
        this.m_toolbar_bg_color = getResources().getColor(R.color.windowbg);
        this.m_toolbar_split_color = getResources().getColor(R.color.toolbar_split_default);
        Toolbar localToolbar = (Toolbar)findViewById(R.id.toolbar);
        if (localToolbar != null)
            setSupportActionBar(localToolbar);
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
        ActionBar localActionBar = getSupportActionBar();
        if (localActionBar != null)
            localActionBar.setDisplayShowTitleEnabled(false);
    }

    public void setToolbarLogo(int paramInt)
    {
        ActionBar localActionBar = getSupportActionBar();
        if (localActionBar != null)
            localActionBar.setLogo(getResources().getDrawable(paramInt));
    }

    public void invalidateToolbar()
    {
        if (this.m_toolbar_bg_drawable == null) {
            BlurDrawable localBlurDrawable = new BlurDrawable();
            this.m_toolbar_bg_drawable = localBlurDrawable;
        }

        if (this.m_toolbar_split_drawable == null) {
            ColorDrawable localColorDrawable = new ColorDrawable();
            this.m_toolbar_split_drawable = localColorDrawable;
        }

        this.m_toolbar_bg_drawable.setColorFilter(this.m_toolbar_bg_color, PorterDuff.Mode.SRC_OVER);
        this.m_toolbar_bg_drawable.setAlpha(TOOLBAR_BG_ALPHA);
        this.m_toolbar_split_drawable.setColorFilter(this.m_toolbar_split_color, PorterDuff.Mode.SRC_OVER);
        Drawable[] arrayOfDrawable = new Drawable[2];
        arrayOfDrawable[0] = this.m_toolbar_bg_drawable;
        arrayOfDrawable[1] = this.m_toolbar_split_drawable;
        LayerDrawable localLayerDrawable = new LayerDrawable(arrayOfDrawable);
        ActionBar localActionBar = getSupportActionBar();
        int bar_height = 0;

        if (localActionBar != null)
            bar_height = getSupportActionBar().getHeight();
        TypedValue localTypedValue = new TypedValue();
        boolean canget = getTheme().resolveAttribute(android.R.attr.actionBarSize, localTypedValue, true);
        int bar_height2 = 0;
        if (canget)
            bar_height2 = TypedValue.complexToDimensionPixelSize(localTypedValue.data, getResources().getDisplayMetrics());
        int dst_height = Math.max(bar_height, bar_height2);
        localLayerDrawable.setLayerInset(0, 0, 0, 0, TOOLBAR_SPLIT_HEIGHT);
        localLayerDrawable.setLayerInset(1, 0, dst_height - TOOLBAR_SPLIT_HEIGHT, 0, 0);
        localActionBar.setSplitBackgroundDrawable(this.m_toolbar_bg_drawable);
        localActionBar.setBackgroundDrawable(localLayerDrawable);
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
}
