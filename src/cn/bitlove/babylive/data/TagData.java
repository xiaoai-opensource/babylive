package cn.bitlove.babylive.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.database.TagTB;
import cn.bitlove.babylive.entity.Tag;
import cn.bitlove.babylive.entity.TagCate;


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
    
    /**
     * 统计tag使用情况
     * @return
     */
    public static List<TagCate> totalTagCate(Context context){
    	List<TagCate> tags = new ArrayList<TagCate>();
    	SQLiteManager sqlManager = SQLiteManager.getInstance(context.getApplicationContext());
    	Cursor cursor = null;
    	cursor = sqlManager.totalTagCate();
    	if(cursor!=null){
    		try{
    			cursor.moveToFirst();
    			while(cursor!=null){
    				TagCate tc = new TagCate();
    				tc.tagName = cursor.getString(0);
    				tc.tagCount = cursor.getInt(1);
    				
    				tags.add(tc);
    				cursor.moveToNext();
    			}
    			
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}finally{
    			if(cursor!=null){
    				cursor.close();
    				cursor = null;
    			}
    		}
    	}
    	return tags;
    	
    }
}
