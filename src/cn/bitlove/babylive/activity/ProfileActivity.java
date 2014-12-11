package cn.bitlove.babylive.activity;

import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.entity.Profile;
import cn.bitlove.babylive.util.DateTimeManager;
import cn.bitlove.babylive.util.Util;
import cn.bitlove.remind.ToastReminder;

import cn.bitlove.babylive.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 宝贝档案表
 * */
public class ProfileActivity extends BaseActivity implements OnClickListener {
	SQLiteManager mSqLiteManager=null;
	private Button btnOK =null;
	private Button btnCancel = null;
	private TextView tvName = null;
	private TextView tvBirthday = null;
	private TextView tvBirthTime = null;
	private TextView tvSex = null;
	private TextView tvWeight = null;
	private TextView tvNote = null;
	private DateTimeManager dtm = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		init();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOK:
			Profile profile = checkBeforeSave();
			if(profile!=null){
				saveProfile(profile);
			}
			break;
		case R.id.btnCancel:
			mActivity.finish();
			break;
		case R.id.birthTime:
			dtm.showTimePick(new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					String strTime = hourOfDay +":" + minute;
					tvBirthTime.setText(strTime);
				}
			});
			break;
		case R.id.birthday:
			DatePickerDialog dp = dtm.showDatePick(new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					String strDate = year +"-" + monthOfYear +"-" + dayOfMonth;
					tvBirthday.setText(strDate);
				}
			});
			break;
		default:
			break;
		}
	}
	private void init(){
		mContext = this;
		mSqLiteManager = SQLiteManager.getInstance(this);
		dtm = DateTimeManager.getInstance(ProfileActivity.this);
		btnOK = (Button)findViewById(R.id.btnOK);
		btnOK.setOnClickListener(this);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		tvName = (TextView)findViewById(R.id.name);
		tvSex = (TextView)findViewById(R.id.sex);
		tvBirthday = (TextView)findViewById(R.id.birthday);
		tvBirthday.setOnClickListener(this);
		tvBirthTime = (TextView)findViewById(R.id.birthTime);
		tvBirthTime.setOnClickListener(this);
		tvNote = (TextView)findViewById(R.id.note);
		tvWeight = (TextView)findViewById(R.id.weight);
	}
	private Profile checkBeforeSave(){
		Profile profile  = new Profile();
		String strName = tvName.getText().toString();
		if("".equals(strName)){
			ToastReminder.showToast(this, "请填写宝贝姓名", Toast.LENGTH_SHORT);
			return null;
		}
		profile.setName(strName);
		
		String strSex = tvSex.getText().toString();
		if("".equals(strSex)){
			ToastReminder.showToast(this, "请选择宝贝性别", Toast.LENGTH_SHORT);
			return null;
		}
		profile.setSex(strSex);
		
		String strBirthday = tvBirthday.getText().toString();
		if("".equals(strBirthday)){
			ToastReminder.showToast(this, "请填写宝贝出生年月", Toast.LENGTH_SHORT);
			return null;
		}
		profile.setBirthday(strBirthday);
		
		String strBirthTime = tvBirthTime.getText().toString();
		if("".equals(strBirthTime)){
			ToastReminder.showToast(this, "请填写宝贝出生时辰", Toast.LENGTH_SHORT);
			return null;
		}
		profile.setBirthTime(strBirthTime);
		
		String strWeight = tvWeight.getText().toString();
		if("".equals(strWeight)){
			ToastReminder.showToast(this, "请填写宝贝出生重量", Toast.LENGTH_SHORT);
			if(!Util.isNumber(strWeight)){
				ToastReminder.showToast(this, "请填写正确的数量", Toast.LENGTH_SHORT);
				tvWeight.requestFocus();
			}
			return null;
		}
		profile.setWeight(Float.parseFloat(strWeight));
		
		String strNote = tvNote.getText().toString();
		profile.setNote(strNote);
		
		return profile ;
	}
	private void saveProfile(Profile profile){
		long rowId = mSqLiteManager.saveProfile(profile);	
		if(rowId>0){
			ToastReminder.showToast(this, "保存成功", Toast.LENGTH_SHORT);
		}else{
			ToastReminder.showToast(this, "保存失败", Toast.LENGTH_SHORT);
			
		}
	}
}
