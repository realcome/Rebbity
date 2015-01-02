package com.rebbity.global;

import com.rebbity.config.APP_PREF;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast; // TYLER_TODO

/**
 * Created by Tyler on 15/1/1.
 */
public class BaseActivity extends ActionBarActivity{
	public static Toast showToast(Context context, String text, int duration){
		// This function is used for debug log display.
		Toast.makeText(context, text, duration).show();
		return null;
	}
	
	public static Toast showToast(Context context, int res, int duration){
		// This function is used for debug log display.
		Toast.makeText(context, res, duration).show();
		return null;
	}
	
	public static Class<?> getStartActivity(Context context){
		if(APP_PREF.IsFirst(context)){
			return com.rebbity.global.GuideActivity.class;
		}else{
			return com.rebbity.global.SplashActivity.class;
		}
	}
	
	public static boolean changeStartState(Context context){
		return APP_PREF.ChangeFirst(context);
	}

    public static boolean isSupportTransStatusBar(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return false;
        }

        return true;
    }

    public static boolean isSupportMaterialDesign(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return true;
        }

        return false;
    }
}
