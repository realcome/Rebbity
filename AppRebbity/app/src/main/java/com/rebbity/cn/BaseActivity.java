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

import com.rebbity.config.APP_PREF;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by Tyler on 15/1/1.
 */
public class BaseActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
