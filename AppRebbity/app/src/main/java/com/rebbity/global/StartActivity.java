package com.rebbity.global;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Tyler on 15/1/1.
 */
public class StartActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.startActivity(new Intent(this, getStartActivity(this)));
        //overridePendingTransition(R.anim.popup_pop_show, R.anim.popup_pop_hide);
        this.finish();
    }
}
