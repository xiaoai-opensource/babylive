package cn.bitlove.babylive.util;

import java.io.File;

import cn.bitlove.babylive.activity.NewRecordActivity;
import cn.bitlove.babylive.activity.ProfileActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class ManageActivity {
	private Context mContext;
	private ManageActivity(Context context){	
		mContext = context;
	}
	public static ManageActivity getInstance(Context context){
		ManageActivity am = new ManageActivity(context);
		return am;
	}
	/**
	 * 启动新纪录界面
	 * */
	public void startNewRecordActivity(){
		Intent intent = new Intent(mContext,NewRecordActivity.class);
		mContext.startActivity(intent);
	}
	/**
	 * 启动新建档案界面
	 * */
	public void startNewProfile(){
		Intent intent = new Intent(mContext,ProfileActivity.class);
		mContext.startActivity(intent);
	}
	/**
	 * 调用照相机
	 * */
	public void startTakePhotoActivity(Activity activity,int requestCode,String imgName){
		String tempDir = PropertyUtil.getProperty("sd_temp");
		File file = new File(tempDir);
		if(!file.exists()){
			file.mkdirs();
		}
		
		File imgFile = new File(tempDir,imgName);
		
		Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		Uri u=Uri.fromFile(imgFile); 
		intent.putExtra(MediaStore.EXTRA_OUTPUT, u); 
        
		activity.startActivityForResult(intent,requestCode); 
	}
}
