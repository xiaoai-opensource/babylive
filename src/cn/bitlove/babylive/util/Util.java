package cn.bitlove.babylive.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Util {
	public static boolean isNumber(String str){
		int pointCount=0;
		for(int i=0;i<str.length();i++){
			int codePoint = str.charAt(i);
			if (('0' < codePoint && codePoint > '9') && codePoint !='.') {
				return false;
			}else{
				if(codePoint=='.'){
					pointCount++;
					if(pointCount>1){
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * 隐藏键盘
	 * */
	public static void hideKeyboard(Context context,View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
