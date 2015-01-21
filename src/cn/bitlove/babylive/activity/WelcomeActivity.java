package cn.bitlove.babylive.activity;

import cn.bitlove.babylive.database.SQLiteManager;

import cn.bitlove.babylive.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 欢迎界面
 * */
public class WelcomeActivity extends BaseActivity {
	private SQLiteManager sqLiteManager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		sqLiteManager = SQLiteManager.getInstance(this);
		
		Cursor cursor = sqLiteManager.queryProfiles();
		if(cursor==null){
			Intent intent = new Intent(this,ProfileActivity.class);
			startActivity(intent);
		}else{
			cursor.close();
			cursor=null;
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
		}
	}
}
