package com.rebbity.cn;

import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Tyler on 15/1/1.
 */
public class GuideActivity extends BaseActivity {
	private Handler mHandler = new Handler();
	
	private Runnable mTaskChangeFirst = new Runnable(){

		@Override
		public void run() {
			BaseActivity.changeStartState(GuideActivity.this);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		mHandler.post(mTaskChangeFirst);
		
	}

	@Override
	protected void onDestroy() {
		mHandler.removeCallbacks(mTaskChangeFirst);
		super.onDestroy();
	}
	
	
	
}
