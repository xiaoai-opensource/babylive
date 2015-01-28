package cn.bitlove.babylive.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.entity.Record;

public class RecordData {
	private static RecordData mRecordData;
	private SQLiteManager mSqlManager;
	private RecordData(Context context){
		mSqlManager = SQLiteManager.getInstance(context);
	}
	public static RecordData getInstance(Context context){
		if(mRecordData==null){
			mRecordData = new RecordData(context);
		}
		return mRecordData;
	}
	/**
	 * 查询所有的记录
	 * */
	public ArrayList<Record> queryAllRecords(){
		ArrayList<Record> arrRecord = new ArrayList<Record>();
		Cursor cursor = mSqlManager.queryRecords();
		try{
			if(cursor!=null && cursor.getCount()!=0){
				cursor.moveToFirst();
				while(!cursor.isAfterLast()){
					Record record = new Record();
					record.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
					record.setActionDate(cursor.getString(cursor.getColumnIndex("actionDate")));
					record.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
					record.setContent(cursor.getString(cursor.getColumnIndex("content")));
					record.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					record.setId(cursor.getLong(cursor.getColumnIndex("id")));

					arrRecord.add(record);				
					cursor.moveToNext();
				}
			}
		}finally{
			if(cursor!=null){
				cursor.close();
				cursor =null;
			}
			mSqlManager.recyle();
		}
		return arrRecord;
	}
	/**
	 * 从指定位置读取指定条数的记录
	 * @param begin 记录起始位置
	 * @param limit 要读取的条数
	 * @return Arraylist 记录集合
	 * */
	public ArrayList<Record> queryNextNumRecords(int begin,int limit){
		ArrayList<Record> arrRecord = new ArrayList<Record>();
		Cursor cursor = mSqlManager.queryNextNumRecords(begin, limit);
		try{
			if(cursor!=null && cursor.getCount()!=0){
				cursor.moveToFirst();
				while(!cursor.isAfterLast()){
					Record record = new Record();
					record.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
					record.setActionDate(cursor.getString(cursor.getColumnIndex("actionDate")));
					record.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
					record.setContent(cursor.getString(cursor.getColumnIndex("content")));
					record.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					record.setId(cursor.getLong(cursor.getColumnIndex("id")));

					arrRecord.add(record);				
					cursor.moveToNext();
				}
			}
		}finally{
			if(cursor!=null){
				cursor.close();
				cursor =null;
			}
			mSqlManager.recyle();
		}
		
		return arrRecord;
	}
	/**
	 * 读取所有记录中的所有年份
	 * */
	public List<String> queryAllActionMonth(){
		ArrayList<String> allMonths = new ArrayList<String>();
		Cursor cursor = mSqlManager.queryAllActionMonth();
		try{
			if(cursor!=null && cursor.getCount()>0){
				cursor.moveToFirst();
				while(!cursor.isAfterLast()){
					String month = cursor.getString(0);
					allMonths.add(month);
					cursor.moveToNext();
				}
			}
		}catch(Exception ex){
			
		}finally{
			if(cursor!=null){
				cursor.close();
				cursor=null;
			}
		}
		
		return allMonths;
			
	}
	/**
	 * 读取一个月份的记录
	 * @param month 要读取的指定月份  ,格式：yyyy-mm
	 * @return  该月份所有的记录
	 * */
	public List<Record> queryMonthRecord(String month){
		ArrayList<Record> arrRecord = new ArrayList<Record>();
		Cursor cursor = mSqlManager.queryMonthRecord(month);
		try{
			if(cursor!=null){
				cursor.moveToFirst();
				while(!cursor.isAfterLast()){
					Record record = new Record();
					record.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
					record.setActionDate(cursor.getString(cursor.getColumnIndex("actionDate")));
					record.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
					record.setContent(cursor.getString(cursor.getColumnIndex("content")));
					record.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					record.setId(cursor.getLong(cursor.getColumnIndex("id")));

					arrRecord.add(record);				
					cursor.moveToNext();
				}
			}
		}catch(Exception ex){
			
		}finally{
			if(cursor!=null){
				cursor.close();
				cursor=null;
			}
		}
		
		return arrRecord;
	}
	/**
	 * 保存一条记录
	 * */
	public long saveRecord(Record record){
		return mSqlManager.saveRecord(record);
	}
	/**
	 * 更新一条记录
	 * */
	public long updateRecord(Record record){
		long rowId = -1;
		try{
			rowId = mSqlManager.updateRecord(record);
		}finally{
			mSqlManager.recyle();
		}
		return rowId;
	}
	/**
	 * 删除一条记录
	 * @param id 记录的ID
	 * */
	public int deleteRecord(long id){
		int rowNum = mSqlManager.deleteRecord(id+"");		
		return rowNum;
	}

}
