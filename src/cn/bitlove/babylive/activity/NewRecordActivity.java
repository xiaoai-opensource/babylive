package cn.bitlove.babylive.activity;

import java.util.Calendar;

import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.data.RecordMetaData;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.fragment.TagFragment;
import cn.bitlove.babylive.util.DateTimeManager;
import cn.bitlove.babylive.util.FileUtil;
import cn.bitlove.babylive.util.RecordMetaUtil;
import cn.bitlove.babylive.util.Util;
import cn.bitlove.babylive.widget.CategoryListAdapter.CateType;
import cn.bitlove.remind.ToastReminder;
import cn.bitlove.babylive.R;
import cn.bitlove.babylive.SeflConstants;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class NewRecordActivity extends BaseFragmentActivity implements OnClickListener {
	private Button btnSaveRecord;	//保存记录
	private View menuTakePhoto;		//插入照相机内容
	private LayoutInflater mInflater;
	private TextView etDate;
	private EditText etContent;
	private EditText etTitle;
	private Record mRecord;
	private String _imgName;
	
	private View backUp;	
	private PopupWindow mPopWindow;
	private View vMore;
	TagFragment mTagFragment;
	
	private long recordId=-1;
	private final int BACK_TAKE_PHOTO =1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_record);
		initActionBar();
		init();
		initEvt();
	}
	@Override
	protected void initActionBar() {

		backUp = findViewById(R.id.backUp);
		vMore = findViewById(R.id.view_more);

		btnSaveRecord = (Button)findViewById(R.id.menu_save_Record);
	}
	private void initEvt(){
		backUp.setOnClickListener(this);
		btnSaveRecord.setOnClickListener(this);
		//menuTakePhoto = findViewById(R.id.menu_take_photo);
		//menuTakePhoto.setOnClickListener(this);
		vMore.setOnClickListener(showMoreListener);
	}
	private void init(){
		mContext=this;
		mInflater = LayoutInflater.from(mContext);
		initField();
	}
	/**
	 * 初始化界面域
	 * */
	private void initField(){
		//判断是否传递了记录对象
		Intent intent = getIntent();
		mRecord = (Record) intent.getSerializableExtra("record");
		etDate = (TextView)findViewById(R.id.date);
		etDate.setOnClickListener(this);
		etContent = (EditText)findViewById(R.id.content);
		etTitle = (EditText)findViewById(R.id.title);
		if(mRecord!=null){
			etDate.setText(mRecord.getActionDate());
			etContent.setText(mRecord.getContent());
			etTitle.setText(mRecord.getTitle());

			RecordMetaUtil.parseContent(mContext,etContent,RecordMetaUtil.DEFAULT_SIZE);			
			recordId=mRecord.getId();
		}else{
			Calendar calendar = Calendar.getInstance();
			String createDate = DateFormat.format(SeflConstants.DB_DATE_FORMAT, calendar).toString();
			etDate.setText(createDate);
		}
	}
	/**
	 * 保存记录
	 * */
	private void saveRecord(){
		if(mRecord==null){
			mRecord = new Record();
		}
		mRecord.setActionDate(etDate.getText().toString());
		mRecord.setContent(etContent.getText().toString());
		String strTitle = "".equals(etTitle.getText().toString())?"无标题":etTitle.getText().toString();
		mRecord.setTitle(strTitle);
		Calendar calendar = Calendar.getInstance();
		String createDate = DateFormat.format(SeflConstants.DB_DATE_FORMAT, calendar).toString();
		mRecord.setCreateDate(createDate);
		mRecord.setUserName("");
		RecordData rd = RecordData.getInstance(mContext);
		if(recordId>0){
			mRecord.setId(recordId);
			long rowId = rd.updateRecord(mRecord);
			if(rowId>0){
				ToastReminder.showToast(mContext, "更新成功", Toast.LENGTH_LONG);
			}else{
				ToastReminder.showToast(mContext, "更新失败", Toast.LENGTH_LONG);
			}
		}else{
			recordId = rd.saveRecord(mRecord);
			if(recordId>0){
				ToastReminder.showToast(mContext, "保存成功", Toast.LENGTH_LONG);
			}else{
				ToastReminder.showToast(mContext, "保存失败", Toast.LENGTH_LONG);
			}
		}
	}
	/**
	 * 设置日期
	 * */
	private void setDate(){
		DateTimeManager dtm = DateTimeManager.getInstance(mContext);
		dtm.showDatePick(new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, monthOfYear+1, dayOfMonth);
				String date = (String) DateFormat.format(SeflConstants.DB_DATE_FORMAT, calendar);
				etDate.setText(date);
			}
		});
	}
	/**
	 * 获取照相机内容
	 * */
	private void takePhoto(){
		Util.hideKeyboard(mContext, etTitle);
		Util.hideKeyboard(mContext, etContent);
		_imgName = RecordMetaUtil.getNewImgId();
		ma.startTakePhotoActivity(this, BACK_TAKE_PHOTO, _imgName);
	}
	/**
	 * 设置照相机返回内容
	 * */
	private void setPhoto(Intent data){

		Uri uri = data.getData(); 
		String sdPath = FileUtil.saveFileBitMapToSDcard(uri.getPath(), _imgName);

		int etWidthDp = etContent.getWidth() - etContent.getPaddingLeft() - etContent.getPaddingRight();
		Bitmap thumbnailBM = FileUtil.saveFileThumbnailImgToSDcard(mContext,sdPath,etWidthDp, _imgName);
		ImageSpan is = new ImageSpan(mContext,thumbnailBM,ImageSpan.ALIGN_BASELINE);

		RecordMetaData rmd = RecordMetaData.getInstance(mContext);
		if(mRecord==null){
			mRecord = new Record();
		}
		mRecord.setId(-1);
		rmd.insertImg(_imgName, mRecord);

		String photoStr = RecordMetaUtil.compileContentImg(_imgName);
		SpannableStringBuilder ssb = new SpannableStringBuilder(photoStr);
		ssb.setSpan(is, 0, photoStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

		etContent.append(ssb);
	}
	/**
	 * 修改Tag
	 */
	private void modifyTag(){
		mTagFragment = new TagFragment(null,okListener);
		mTagFragment.show(getSupportFragmentManager(), "tag");
	}
	DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			String etTags = mTagFragment.getTags();
			ToastReminder.showToast(mContext, etTags, Toast.LENGTH_SHORT);
		}
	};
	/**
	 * 本界面点击事件
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backUp:
			mActivity.finish();
			break;
		case R.id.menu_save_Record:
			saveRecord();
			break;
		case R.id.date:
			setDate();
			break;
		/*case R.id.menu_take_photo:
			takePhoto();
			break;*/
		default:
			break;
		}
	}
	/**
	 * 处理Activity返回值
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_CANCELED){
			return;
		}
		
		switch (requestCode) {
		case BACK_TAKE_PHOTO:			
			setPhoto(data);
			break;

		default:
			break;
		}

	}

	/**
	 * 显示更多事件
	 */
	OnClickListener showMoreListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(mPopWindow==null){
				View content = mInflater.inflate(R.layout.new_record_pop_more,null);
				View actionTakePhoto = content.findViewById(R.id.popactionTakePhoto);
				View actionTag = content.findViewById(R.id.popActionTag);
				
				actionTakePhoto.setOnClickListener(listItemListener);
				actionTag.setOnClickListener(listItemListener);
				mPopWindow = new PopupWindow(content,200,100);
			}
			
			if(mPopWindow.isShowing()){
				mPopWindow.dismiss();
			}else{
				mPopWindow.setFocusable(true);
				mPopWindow.setOutsideTouchable(true);
				// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景  
				mPopWindow.setBackgroundDrawable(new BitmapDrawable());  
				mPopWindow.showAsDropDown(v,0,1);
			}
			
		}
	};
	
	OnClickListener listItemListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.popactionTakePhoto:
				takePhoto();
				mPopWindow.dismiss();
				break;
			case R.id.popActionTag:
				modifyTag();
				mPopWindow.dismiss();
				break;
			}
		}
	};
}
