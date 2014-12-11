package cn.bitlove.babylive.data;

import android.content.Context;
import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.entity.RecordMeta;

/**
 * 操作内容中的资源
 * */
public class RecordMetaData {
	private static RecordMetaData rmd=null;
	private SQLiteManager mSqlManager;
	private Context mContext;
	
	private RecordMetaData(Context context){
		mContext = context;
		mSqlManager = SQLiteManager.getInstance(context);
	}
	public static RecordMetaData getInstance(Context context){
		if(rmd==null){
			rmd = new RecordMetaData(context);
		}
		
		return rmd;
	}
	/**
	 * 插入图片
	 * */
	public void insertImg(String imgName,Record record){
		RecordMeta rm = new RecordMeta();
		rm.setFileName(imgName);
		mSqlManager.insertMetaImg(rm, record);
		mSqlManager.recyle();
	}
}
