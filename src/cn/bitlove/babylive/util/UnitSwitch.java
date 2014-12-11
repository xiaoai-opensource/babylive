package cn.bitlove.babylive.util;

import android.content.Context;

/**
 * 单位转换
 * */
public class UnitSwitch {
	/**
	 * px转dip
	 * */
	public static int px2dp(Context context,float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
	}
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dp2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    } 
}
