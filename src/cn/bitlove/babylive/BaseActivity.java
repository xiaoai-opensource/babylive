package cn.bitlove.babylive;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Activity 基类
 * */
public class BaseActivity extends Activity {
	protected Context mContext = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
	}
}
