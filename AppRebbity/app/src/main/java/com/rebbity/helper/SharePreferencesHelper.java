package com.rebbity.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * Created by Tyler on 15/1/1.
 */
public class SharePreferencesHelper {
	private final static String SHAREDPREFERENCES_NAME = "pref";
	private final static String kFirstStart = "kFirst";
	
	// judge is first start or not.
	public static boolean isFirstStart(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		return preferences.getBoolean(kFirstStart, true);
	}
	
	// change first start state to false.
	public static void changeFirstStart(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
        editor.putBoolean(kFirstStart, false);
        editor.commit();               
	}
}
