package cn.bitlove.babylive.database;


import cn.bitlove.babylive.entity.Profile;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.entity.RecordMeta;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SqLite管理
 * */
public class SQLiteManager {
	private Context mContext;
	private SQLiteDBHelper dbHelper;
	private SQLiteDatabase db;

	private final String DBNAME="babylive";
	private final String TABLE_PROFILE="profile";		//当前表
	private final String TABLE_RECORD="record";			//记录表
	private final String TABLE_RECORD_META="record_meta";	//记录表

	private SQLiteManager(){};
	public static SQLiteManager mSqLiteManager;
	public static SQLiteManager getInstance(Context context) {
		if(mSqLiteManager==null){
			mSqLiteManager = new SQLiteManager();
		}
		mSqLiteManager.mContext = context;
		return mSqLiteManager;
	}
	/**
	 * 查询档案
	 * */
	public Cursor queryProfiles(){

		Cursor cursor = null;
		try{
			db = getSqLiteDatabase(false);
			cursor =  db.query(TABLE_PROFILE, null, null, null, null, null, "birthday");
		}catch(Exception ex){

		}finally{
			if(db!=null){
				db.close();
				db = null;
			}
		}

		return cursor;
	}	
	/**
	 * 保存档案
	 * */
	public long saveProfile(Profile profile){
		long rowId=-1;
		try{
			ContentValues cv = new ContentValues();
			cv.put("name", profile.getName());
			cv.put("sex", profile.getSex());
			cv.put("birthday", profile.getBirthday());
			cv.put("birthTime", profile.getBirthTime());
			cv.put("weight", profile.getWeight());
			cv.put("note", profile.getNote());

			db = getSqLiteDatabase(true);
			rowId = db.insert(TABLE_PROFILE, null, cv);

		}catch(Exception ex){
		}finally{
			if(db!=null){
				db.close();
				db = null;
			}

		}
		return rowId;
	}

	/**
	 * 保存一条新纪录
	 * */	
	public long saveRecord(Record record){
		long rowId = -1;

		try{
			ContentValues cv = new ContentValues();
			cv.put(RecordTB.userName, record.getUserName());
			cv.put(RecordTB.actionDate,record.getActionDate());
			cv.put(RecordTB.createDate, record.getCreateDate());
			cv.put(RecordTB.content,record.getContent());
			cv.put(RecordTB.title,record.getTitle());

			db = getSqLiteDatabase(true);
			rowId = db.insert(TABLE_RECORD, null, cv);

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db = null;
			}
		}

		return rowId;
	}
	/**
	 * 更新一条记录
	 * */
	public long updateRecord(Record record){
		long rowId=-1;
		try{
			ContentValues cv = new ContentValues();
			cv.put("actionDate",record.getActionDate());
			cv.put("content",record.getContent());
			String whereArgs[] = {record.getId()+""}; 
			db = getSqLiteDatabase(true);
			rowId = db.update(TABLE_RECORD, cv, "id=?", whereArgs);
		}finally{
			if(db!=null){
				db.close();
				db = null;
			}
		}
		return rowId;
	}
	/**
	 * 删除一条记录
	 * */
	public int deleteRecord(String id){
		String whereArgs[] = {id}; 
		int rowNum=-1;
		try{
			db = getSqLiteDatabase(true);
			rowNum = db.delete(TABLE_RECORD, "id=?", whereArgs);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db = null;
			}
		}

		return rowNum;
	}
	/**
	 * 查询所有的记录
	 * */
	public Cursor queryRecords(){

		Cursor  cursor =null;
		try{
			db = getSqLiteDatabase(false);
			cursor = db.query(TABLE_RECORD, null, null, null, null, null, RecordTB.actionDate +" desc");
		}catch(Exception ex){

		}
		return cursor;
	}
	/**
	 * 从指定位置读取指定条数的记录
	 * @param begin 记录起始位置
	 * @param limit 要读取的条数
	 * @return Cursor
	 * */
	public Cursor queryNextNumRecords(int begin,int limit){
		Cursor  cursor =null;
		try{
			db = getSqLiteDatabase(false);
			String sql = String.format("select * from record order by actionDate desc limit %d offset %d", limit,begin);
			cursor = db.rawQuery(sql,null);
		}catch(Exception ex){

		}
		return cursor;
	}
	/**
	 * 插入一条资源图片
	 * */
	public long insertMetaImg(RecordMeta rm,Record record){
		long rowId = -1;

		try{
			ContentValues cv = new ContentValues();
			cv.put(RecordMetaTB.type,"IMG");
			cv.put(RecordMetaTB.recordId,record.getId());
			cv.put(RecordMetaTB.fileName,rm.getFileName());

			db = getSqLiteDatabase(true);
			rowId = db.insert(TABLE_RECORD_META, null, cv);

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db = null;
			}
		}

		return rowId;
	}

	/**
	 * 回收数据库
	 * */
	public boolean recyle(){
		boolean result = true;
		try{
			if(db!=null){
				db.close();
				db = null;
			}
		}catch(Exception ex){
			result = false;
		}

		return result;
	}

	/**
	 * 获取数据库
	 * @param editable 是否可编辑
	 * */
	private SQLiteDatabase getSqLiteDatabase(boolean editable){
		if(dbHelper==null){
			dbHelper = new SQLiteDBHelper(mContext, DBNAME, null, 1);
		}
		if(editable){
			db = dbHelper.getWritableDatabase();
		}else{
			db = dbHelper.getReadableDatabase();
		}
		return db;
	}
	class SQLiteDBHelper extends SQLiteOpenHelper{

		public SQLiteDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		/**
		 * 创建Profile表
		 * */
		private void createProfileTable(SQLiteDatabase db){
			StringBuilder sb = new StringBuilder();
			sb.append("create table [" + TABLE_PROFILE + "](");
			sb.append("[id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("[name] TEXT,");
			sb.append("[sex] TEXT,");
			sb.append("[birthday] TEXT,");
			sb.append("[birthTime] TEXT,");
			sb.append("[weight] float,");
			sb.append("[note] TEXT)");
			db.execSQL(sb.toString());
		}
		/**
		 * 创建Record表
		 * */
		private void createRecordTable(SQLiteDatabase db){

			StringBuilder sb = new StringBuilder();

			sb.append("create table [" + TABLE_RECORD + "](");
			sb.append("["+RecordTB.id+"] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("["+RecordTB.title+"] TEXT,");
			sb.append("["+RecordTB.userName+"] TEXT,");
			sb.append("["+RecordTB.createDate+"] TEXT,");
			sb.append("["+RecordTB.actionDate+"] TEXT,");
			sb.append("["+RecordTB.content+"] TEXT)");
			db.execSQL(sb.toString());
		}
		/**
		 * 创建记录中的资源表
		 * */
		private void createRecordMetaTable(SQLiteDatabase db){
			StringBuilder sb = new StringBuilder();

			sb.append("create table [" + TABLE_RECORD_META + "](");
			sb.append("["+RecordMetaTB.id+"] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("["+RecordMetaTB.recordId+"] TEXT,");
			sb.append("["+RecordMetaTB.type+"] TEXT,");
			sb.append("["+RecordMetaTB.fileName+"] TEXT)");
			db.execSQL(sb.toString());
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			createProfileTable(db);
			createRecordTable(db);
			createRecordMetaTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	}
}
