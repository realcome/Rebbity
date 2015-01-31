package com.rebbity.cn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Tyler on 15/1/1.
 */
public class SplashActivity extends BaseActivity {
    public static final int MIN_DURATION_DISP = 1500;
    private boolean _is_data_loading = false;
    private boolean _is_delay_time_out = false;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (_is_data_loading) {
                _is_delay_time_out = true;
            } else {
                switchActivity();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(runnable, MIN_DURATION_DISP);
    }

    private void switchActivity()
    {
        Intent localIntent = new Intent(this, MainActivity.class);
        startActivity(localIntent);
        overridePendingTransition(R.anim.slide_in, R.anim.fade_exit);
        finish();
    }



    public void loadData()
    {
        this._is_data_loading = true;
    }


    @Override
    protected void onDestroy() {
        this.handler.removeCallbacks(this.runnable);
        super.onDestroy();
    }
}
