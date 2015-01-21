package cn.bitlove.babylive.widget;

import cn.bitlove.babylive.activity.NewRecordActivity;
import cn.bitlove.babylive.activity.ProfileActivity;

import cn.bitlove.babylive.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
/**
 * 应用标题部分
 * */
public class TitleBar extends RelativeLayout {
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		final Context mContext = context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService	(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.actionbar_common,this);
		Button menuProfile = (Button)findViewById(R.id.menuProfile);
		menuProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,ProfileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.getApplicationContext().startActivity(intent);		
			}
		});

		Button newRecord = (Button)findViewById(R.id.menuNewRecord);
		newRecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,NewRecordActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.getApplicationContext().startActivity(intent);						
			}
		});
	}

}
