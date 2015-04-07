package cn.bitlove.babylive.activity;

import cn.bitlove.babylive.data.ProfileData;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * 宝贝档案表
 * */
public class ProfileActivity extends BaseActivity implements OnClickListener {
	SQLiteManager mSqLiteManager=null;
	private Profile mProfile = null;
	private Button btnOK =null;
	private Button btnCancel = null;
	private TextView tvName = null;
	private TextView tvBirthday = null;
	private TextView tvBirthTime = null;
	private RadioGroup sex = null;
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
        switch (v.getId()){
            case R.id.tvBirthday:
                DateTimeManager.getInstance(mContext).showDatePick(new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate = String.format("%d-%s-%s",year,Util.format(monthOfYear+1,2),Util.format(dayOfMonth,2));
                        tvBirthday.setText(strDate);
                    }
                });
                break;
            case R.id.tvBirthTime:
                DateTimeManager.getInstance(mContext).showTimePick(new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String strTime = String.format("%s:%s",Util.format(hourOfDay,2),Util.format(minute,2));
                        tvBirthTime.setText(strTime);
                    }
                });
                break;
            case R.id.btnSave:
                Profile profile = checkBeforeSave();
                if(profile!=null){
                    saveProfile(profile);
                }
                break;
        }
	}
	private void init(){
		mContext = this;
		mSqLiteManager = SQLiteManager.getInstance(this);
        btnOK = (Button) findViewById(R.id.btnSave);
        btnOK.setOnClickListener(this);
		dtm = DateTimeManager.getInstance(ProfileActivity.this);
        sex = (RadioGroup) findViewById(R.id.sex);
        tvName = (TextView)findViewById(R.id.name);
		tvNote = (TextView)findViewById(R.id.note);
		tvWeight = (TextView)findViewById(R.id.weight);
        tvBirthday=(TextView)findViewById(R.id.tvBirthday);
        tvBirthday.setOnClickListener(this);

        tvBirthTime=(TextView)findViewById(R.id.tvBirthTime);
        tvBirthTime.setOnClickListener(this);
        
        Profile profile = getProfile();
        if(profile!=null){
        	tvName.setText(mProfile.getName());
        	tvBirthday.setText(mProfile.getBirthday());
        	tvBirthTime.setText(mProfile.getBirthTime());
        	tvWeight.setText(String.valueOf(mProfile.getWeight()));
        	tvNote.setText(mProfile.getNote());
        	
        	RadioButton rb1 = (RadioButton) sex.getChildAt(0);
        	RadioButton rb2 = (RadioButton) sex.getChildAt(1);
        	if(rb1.getText().equals(mProfile.getSex())){
        		rb1.setChecked(true);
        	}
        	if(rb2.getText().equals(mProfile.getSex())){
        		rb2.setChecked(true);
        	}
        	
        }
	}
	private Profile checkBeforeSave(){
		String strName = tvName.getText().toString();
		if("".equals(strName)){
			ToastReminder.showToast(this, getString(R.string.remind_write_name), Toast.LENGTH_SHORT);
			return null;
		}
		mProfile.setName(strName);
		
        int radioId = sex.getCheckedRadioButtonId();
		if(radioId==-1){
			ToastReminder.showToast(this, getString(R.string.remind_write_sex), Toast.LENGTH_SHORT);
			return null;
		}
        RadioButton rb = (RadioButton) sex.findViewById(radioId);
		mProfile.setSex(rb.getText().toString());
		
		String strBirthday = tvBirthday.getText().toString();
		if("".equals(strBirthday)){
			ToastReminder.showToast(this, getString(R.string.remind_write_birthday), Toast.LENGTH_SHORT);
			return null;
		}
		mProfile.setBirthday(strBirthday);
		
		String strBirthTime = tvBirthTime.getText().toString();
		if("".equals(strBirthTime)){
			ToastReminder.showToast(this, getString(R.string.remind_write_birthTime), Toast.LENGTH_SHORT);
			return null;
		}
		mProfile.setBirthTime(strBirthTime);
		
		String strWeight = tvWeight.getText().toString();
		if("".equals(strWeight)){
			ToastReminder.showToast(this, getString(R.string.remind_write_birthweight), Toast.LENGTH_SHORT);
			if(!Util.isNumber(strWeight)){
				ToastReminder.showToast(this, getString(R.string.remind_correct_weight), Toast.LENGTH_SHORT);
				tvWeight.requestFocus();
			}
			return null;
		}
		mProfile.setWeight(Float.parseFloat(strWeight));
		
		String strNote = tvNote.getText().toString();
		mProfile.setNote(strNote);
		
		return mProfile ;
	}
	/**
     * 获取当前用户信息
     * */
    private Profile getProfile(){
    	mProfile = ProfileData.queryProfile(this);
    	return mProfile;
    }
    /**
     * 保存档案
     * */
	private void saveProfile(Profile profile){
		long rowId = mSqLiteManager.saveProfile(profile);	
		if(rowId>0){
			ToastReminder.showToast(this, getString(R.string.save_success), Toast.LENGTH_SHORT);
		}else{
			ToastReminder.showToast(this, getString(R.string.save_fail), Toast.LENGTH_SHORT);
			
		}
	}
}
