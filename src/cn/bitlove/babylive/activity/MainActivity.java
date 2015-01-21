package cn.bitlove.babylive.activity;

import java.util.ArrayList;

import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.remind.ToastReminder;

import cn.bitlove.babylive.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 应用主界面，记录列表界面
 * */
public class MainActivity extends BaseActivity {
	private ListView recordList;
	private LayoutInflater mInflater;
	private RecordData mRD;
	private int delWidth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		init();
	}
	@Override
	protected void onResume() {
		super.onResume();
		initListData();
	}

	private void init(){
		mInflater = LayoutInflater.from(mContext);
		mRD = RecordData.getInstance(mContext);
		recordList = (ListView) findViewById(R.id.recordList);
		delWidth = mContext.getResources().getDimensionPixelOffset(R.dimen.item_del_width);
	}
	/**
	 * 初始化列表数据
	 * */
	private void initListData(){
		final ArrayList<Record> arrRecord = mRD.queryAllRecords();
		recordList.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder vh = null;
				final Record record = arrRecord.get(position);
				if(convertView==null){
					vh = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_record, null);
					vh.rlContent = (RelativeLayout)convertView.findViewById(R.id.rlContent);
					vh.actionTime = (TextView) convertView.findViewById(R.id.actionTime);
					vh.title = (TextView)convertView.findViewById(R.id.title);
					vh.del = (RelativeLayout)convertView.findViewById(R.id.del);

					vh.del.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							int rowNum = mRD.deleteRecord(record.getId());
							if(rowNum>-1){
								ToastReminder.showToast(mContext, "成功删除记录", Toast.LENGTH_SHORT);
								initListData();
							}
						}
					});
					convertView.setTag(vh);
				}else{
					vh = (ViewHolder) convertView.getTag();
				}
				vh.actionTime.setText(record.getActionDate());
				vh.title.setText(record.getTitle());
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				Record record = arrRecord.get(position);
				return record.getId();
			}

			@Override
			public Object getItem(int position) {
				Record record = arrRecord.get(position);
				return record;
			}

			@Override
			public int getCount() {
				return arrRecord.size();
			}
		});

		/**
		 * 条目点击事件
		 * */
		recordList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Record record = (Record) recordList.getItemAtPosition(recordList.getFirstVisiblePosition()+position);
				Intent intent = new Intent(mContext,NewRecordActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("record", record);
				intent.putExtras(bundle);
				mActivity.startActivity(intent);
			}
		});

	}
	static final class ViewHolder{
		RelativeLayout rlContent;
		TextView actionTime;
		TextView title;
		RelativeLayout del;
		int delScrollX;
	}
}
