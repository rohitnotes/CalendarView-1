package com.zcw;

import android.content.Context;
import android.util.DisplayMetrics;

public final class DpUtil {
	private static float density = 0;
	private static float scaledDensity = 0;
	private DpUtil(){}
	
	/**
	 * @param context
	 */
	public static void init(Context context){
		final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		density = displayMetrics.density;
		scaledDensity = displayMetrics.scaledDensity;
		
		System.out.println("density:"+density+",scaledDensity:"+scaledDensity);
	}
	
	/**
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue){
		if(density == 0){
			init(context);
		}
		return dip2px(dpValue);
	}
	
	/**
	 * @param density
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float density, float dpValue){
		return (int)(dpValue * density + 0.5f);
	}
	
	/**
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue){
		return dip2px(density, dpValue);
	}
	
	/**
	 * @param context
	 * @param scaledDensity
	 * @return
	 */
	public static float sp2px(Context context, float spValue) {
		if(scaledDensity == 0){
			init(context);
		}
		return sp2px(spValue);
	}

	/**
	 * @param spValue
	 * @return
	 */
	public static float sp2px(float spValue) {
		return sp2px(scaledDensity, spValue);
	}
	
	/**
	 * @param spValue
	 * @param scaledDensity
	 * @return
	 */
	public static float sp2px(float spValue, float scaledDensity) {
		return (spValue * scaledDensity);
	}
	
}
