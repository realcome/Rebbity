package com.rebbity.config;

import android.content.Context;

import com.rebbity.helper.SharePreferencesHelper;

/**
 * Created by Tyler on 15/1/1.
 */
public class APP_PREF {
	public static boolean IsFirst(Context context){
		return SharePreferencesHelper.isFirstStart(context.getApplicationContext());
	}
	
	public static boolean ChangeFirst(Context context){
		if(!SharePreferencesHelper.isFirstStart(context.getApplicationContext())){
			return true;
		}
		
		SharePreferencesHelper.changeFirstStart(context);
		return true;
	}
}
