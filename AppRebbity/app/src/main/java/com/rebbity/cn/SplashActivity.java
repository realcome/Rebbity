package com.rebbity.cn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.rebbity.widget.Rainbow;
import com.rebbity.widget.RainbowBar;

/**
 * Created by Tyler on 15/1/1.
 */
public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();
    RainbowBar bar;
    Rainbow anime;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);
        bar = (RainbowBar)findViewById(R.id.rainbowbar);


        anime = bar.getRainbow();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        anime.setBounds(0, 0, dm.widthPixels, dm.heightPixels);

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anime.start();
            }
        });

        Button stop = (Button) findViewById(R.id.stop);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rainbow anime = bar.getRainbow();
                anime.stop();

                startActivity(new Intent(SplashActivity.this, SettingsActivity.class));
            }
        });


    }
}
