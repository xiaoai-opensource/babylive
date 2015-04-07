package cn.bitlove.babylive.data;

import android.content.Context;
import android.database.Cursor;
import cn.bitlove.babylive.database.ProfileTB;
import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.entity.Profile;

/**
 * 档案信息
 * */
public class ProfileData {
	/**
	 * 查询宝贝档案
	 * */
	public static  Profile queryProfile(Context context){
		Profile profile = null;
		SQLiteManager manager = SQLiteManager.getInstance(context);
		Cursor cursor = manager.queryProfiles();
		try{
			if(cursor!=null){
				profile = new Profile();
				cursor.moveToFirst();
				profile.setId(cursor.getLong(cursor.getColumnIndex(ProfileTB.id)));
				profile.setName(cursor.getString(cursor.getColumnIndex(ProfileTB.name)));
				profile.setBirthday(cursor.getString(cursor.getColumnIndex(ProfileTB.birthday)));
				profile.setBirthTime(cursor.getString(cursor.getColumnIndex(ProfileTB.birthTime)));
				profile.setWeight(cursor.getFloat(cursor.getColumnIndex(ProfileTB.weight)));
				profile.setNote(cursor.getString(cursor.getColumnIndex(ProfileTB.note)));
				profile.setSex(cursor.getString(cursor.getColumnIndex(ProfileTB.sex)));
			}
		}finally{
			if(cursor!=null){
				cursor.close();
				cursor = null;
				
			}
		}
		
		return profile;				
	}
}
