package cn.bitlove.babylive.activity;

import java.util.ArrayList;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.entity.Record;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 指定标签的Record列表
 */
public class TagRecordsActivity extends BaseActivity{

	LayoutInflater mInflater;
	ListView mListView;
	ArrayList<Record> mListData;
	String mTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_records);
		init();
		initActionBar();
		getListData();
		initListView();
	}

	private void init(){
		mListView = (ListView) findViewById(R.id.lvRecords);
		mInflater = LayoutInflater.from(mContext);
				
		Intent intent = getIntent();
		mTag = intent.getStringExtra("tag");
		if(mTag ==null || "".equals(mTag)){
			throw new IllegalArgumentException("tag 为空 ");
		}
	}
	/**
	 * 初始化ActionBar
	 */
	protected void initActionBar(){
		View back = findViewById(R.id.backUp);
		back.setOnClickListener(this);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(mTag);
	}
	/**
	 * 获取数据
	 */
	private void getListData(){
		mListData = RecordData.getInstance(mContext).queryRecordsByTag(mTag);
	}
	/**
	 * 初始化列表
	 */
	private void initListView(){
		mListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Record record = (Record) getItem(position);
				
				View view = convertView;
				ViewHolder viewHolder = new ViewHolder();
				if(view!=null){
					viewHolder = (ViewHolder) view.getTag();
				}else{
					view = mInflater.inflate(R.layout.item_record,parent,false);
					viewHolder.title = (TextView) view.findViewById(R.id.title);
					viewHolder.actionTime = (TextView) view.findViewById(R.id.actionTime);
				}
				
				viewHolder.title.setText(record.getTitle());
				viewHolder.actionTime.setText(record.getActionDate());
				return view;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return mListData.get(position);
			}
			
			@Override
			public int getCount() {
				return mListData.size();
			}
		});
		
		/**
		 * 条目点击事件
		 * */
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Record record = (Record) mListView.getItemAtPosition(position);
				Intent intent = new Intent(mContext,NewRecordActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("record", record);
				intent.putExtras(bundle);
				mActivity.startActivity(intent);
			}
		});
	}
	
	class ViewHolder{
		TextView title;
		TextView actionTime;
	}
}
