package cn.bitlove.babylive.data;


import android.content.Context;
import cn.bitlove.babylive.database.SQLiteManager;

/**
 * 数据库到java数据源的工具栏
 * */
public class DataUtil {
	private DataUtil(){}
	private static Context mContext;
	private static DataUtil mDataUtil;
	private static SQLiteManager sqlManager;

	public static DataUtil getInstance(Context context){
		if(mDataUtil==null){
			mDataUtil = new DataUtil();
		}
		mContext = context;
		if(sqlManager == null){
			sqlManager = SQLiteManager.getInstance(mContext);
		}
		return mDataUtil;
	}
	
}
