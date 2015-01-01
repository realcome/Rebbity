package com.rebbity.config;

/**
 * Created by Tyler on 15/1/1.
 */
public class APP_MODE {
	public final static int APP_MODE_NORMAL = 0x0001;
	public final static int APP_MODE_PREMIUM = APP_MODE_NORMAL << 1;
	public final static int APP_MODE_MAX = APP_MODE_PREMIUM;
	
	public final static int APP_MODE = APP_MODE_NORMAL;
	
}
