package com.rebbity.cn;

import android.os.Bundle;
import android.os.Handler;

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
