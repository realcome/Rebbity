package com.rebbity.cn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rebbity.widget.Rainbow;
import com.rebbity.widget.RainbowBar;

/**
 * Created by Tyler on 15/1/1.
 */
public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
