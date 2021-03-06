package cn.bitlove.babylive.activity;

import cn.bitlove.babylive.util.ManageActivity;

import cn.bitlove.babylive.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;

/**
 * Activity 基类
 * */
public class BaseActivity extends Activity implements OnClickListener {
	protected Context mContext = this;
	protected Activity mActivity = this;
	protected ManageActivity ma;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		ma=ManageActivity.getInstance(mContext);
	}
	/**
	 * 初始化ActionBar
	 * */
	protected void initActionBar() {
		/*RelativeLayout rl = (RelativeLayout)findViewById(R.id.titleBar);
		ViewGroup vg = (ViewGroup)LayoutInflater.from(mContext).inflate(R.layout.actionbar_common, null);
		rl.addView(vg);
		View backUp = rl.findViewById(R.id.backUp);
		backUp.setOnClickListener(this);
		View newRecord = rl.findViewById(R.id.menuNewRecord);
		newRecord.setOnClickListener(this);
		View newProfile = rl.findViewById(R.id.menuProfile);
		newProfile.setOnClickListener(this);
		
		View test = rl.findViewById(R.id.menuTest);
		test.setOnClickListener(this);*/
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backUp:
			mActivity.finish();
			break;
		case R.id.menuNewRecord:
			ma.startNewRecordActivity();
			break;
		case R.id.menuProfile:
			ma.startNewProfile();
			break;
		case R.id.menuTest:
			Intent intent = new Intent(this,MainFragmentActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
}
