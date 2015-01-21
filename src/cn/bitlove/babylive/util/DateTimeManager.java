package cn.bitlove.babylive.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
/**
 * 时间控件
 * */
public class DateTimeManager {
	private Context mContext;
	private static DateTimeManager mDateTimeManager;
	private static DatePickerDialog mDatePicker;;
	private static TimePickerDialog mTimePicker;
	private DateTimeManager(){};
	public static DateTimeManager getInstance(Context context){
		if(mDateTimeManager==null){
			mDateTimeManager = new DateTimeManager();
		}
		mDateTimeManager.mContext = context;
		return mDateTimeManager;
	}
	public DatePickerDialog showDatePick(OnDateSetListener dsl){
		if(mDatePicker==null){
			Calendar calendar = Calendar.getInstance();
			mDatePicker = new DatePickerDialog(mContext, dsl, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		}
		mDatePicker.show();
		
		return mDatePicker;
	}
	public TimePickerDialog showTimePick(OnTimeSetListener tsl){
		if(mTimePicker==null){
			Calendar calendar = Calendar.getInstance();
			mTimePicker = new TimePickerDialog(mContext, tsl, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
		}
		mTimePicker.show();
		return mTimePicker;
	}
}
