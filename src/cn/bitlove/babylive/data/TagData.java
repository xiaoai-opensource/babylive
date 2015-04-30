package cn.bitlove.babylive.data;

import android.content.Context;
import android.database.Cursor;

import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.database.TagTB;
import cn.bitlove.babylive.entity.Tag;


/**
 * tag 数据类
 *
 * @author luoaz
 */
public class TagData {
    /**
     * 保存Tag
     *
     * @param context
     * @param tag
     * @return
     */
    public static boolean saveTag(Context context, Tag tag) {
        SQLiteManager sqlManager = SQLiteManager.getInstance(context.getApplicationContext());
        return sqlManager.saveTag(tag);
    }
    /**
     * 删除指定id的所有tag
     * @param context
     * @param recordId
     */
    public static void removeAllTag(Context context, String recordId){
    	SQLiteManager sqlManager = SQLiteManager.getInstance(context.getApplicationContext());
        sqlManager.removeAllTags(recordId);
    	
    }

    /**
     * 根据Recordid查找所有的tag
     *
     * @param recordId
     * @return
     */
    public static String getTags(Context context, String recordId) {
        StringBuilder sb = new StringBuilder();
        SQLiteManager sqlManager = SQLiteManager.getInstance(context.getApplicationContext());
        Cursor cursor = sqlManager.getTags(recordId);
        if(cursor!=null){
            try{
                cursor.moveToFirst();
            	int colIndex = cursor.getColumnIndex(TagTB.tagName);
                
                while(cursor!=null){
                	sb.append(cursor.getString(colIndex));
                    if(!cursor.isLast()){
                        sb.append(",");
                    }
                    cursor.moveToNext();
                }

            }catch (Exception ex){
                ex.printStackTrace();;
            }finally {
                cursor.close();
                cursor = null;
            }
        }
        return sb.toString();
    }
}
