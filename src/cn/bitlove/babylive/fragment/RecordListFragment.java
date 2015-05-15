package cn.bitlove.babylive.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bitlove.babylive.R;
import cn.bitlove.babylive.activity.NewRecordActivity;
import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.util.ManageActivity;
import cn.bitlove.babylive.widget.SlideItemTouchuListener;
import cn.bitlove.babylive.widget.WaterfallListView;
import cn.bitlove.babylive.widget.WaterfallListView.IOnRefresh;
import cn.bitlove.remind.ToastReminder;

public class RecordListFragment extends Fragment{
	private Context mContext;
	private Activity mActivity;
	private View fragmentView;
	protected ManageActivity ma;
	private WaterfallListView recordList;
	private LayoutInflater mInflater;
	private RecordData mRD;
	private BaseAdapter mAdapter;
	private ArrayList<Record> arrRecord;
	final private int PAGE_NUM=10;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_record_list, null);
        mContext = getActivity();
		mActivity = getActivity();
		ma=ManageActivity.getInstance(mContext);
		mInflater = inflater;
		init();

		initListData();
		return fragmentView;
	}
	@Override
	public void onResume() {
		super.onResume();
		initListData();
	}


	private void init(){
		mRD = RecordData.getInstance(mContext);
		recordList = (WaterfallListView) fragmentView.findViewById(R.id.recordList);
		recordList.setRefreshListener(refresher);
	}
	/**
	 * 初始化列表数据
	 * */
	private void initListData(){
		arrRecord = mRD.queryNextNumRecords(0, PAGE_NUM);

		mAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder vh = null;
				final Record record = (Record) arrRecord.get(position);
				if(convertView==null){
					vh = new ViewHolder();
					convertView = mInflater.inflate(R.layout.item_record, null);
					vh.rlContent = (RelativeLayout)convertView.findViewById(R.id.rlContent);
					vh.actionTime = (TextView) convertView.findViewById(R.id.actionTime);
					vh.title = (TextView)convertView.findViewById(R.id.title);
					vh.del = (RelativeLayout)convertView.findViewById(R.id.del);

					convertView.setTag(vh);
				}else{
					vh = (ViewHolder) convertView.getTag();
					vh.rlContent.setTranslationX(0);
					vh.del.setTranslationX(0);
				}
				vh.actionTime.setText(record.getActionDate());
				vh.title.setText(record.getTitle());
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				Record record = (Record) arrRecord.get(position);
				return record.getId();
			}

			@Override
			public Object getItem(int position) {
				Record record = (Record) arrRecord.get(position);
				return record;
			}

			@Override
			public int getCount() {
				return arrRecord.size();
			}
		};

		recordList.setAdapter(mAdapter);

		/**
		 * 条目点击事件
		 * */
		recordList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Record record = (Record) recordList.getItemAtPosition(position);
				Intent intent = new Intent(mContext,NewRecordActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("record", record);
				intent.putExtras(bundle);
				mActivity.startActivity(intent);
			}
		});
		
		SlideItemTouchuListener sListener = new SlideItemTouchuListener(recordList,R.id.rlContent,R.id.del);
		recordList.setOnTouchListener(sListener);

	}
	
	IOnRefresh refresher = new IOnRefresh() {

		@Override
		public void doRefresh() {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {

					ArrayList<Record> nextRecords = mRD.queryNextNumRecords(arrRecord.size(), PAGE_NUM);
					if(nextRecords.size()==0){
						ToastReminder.showToast(mContext, "没有更多数据了", Toast.LENGTH_SHORT);
					}else{
						for(Record record : nextRecords){
							arrRecord.add(record);
						}
						mAdapter.notifyDataSetChanged();
						//执行刷新完成操作，清理刷新状态
					}
					recordList.completeRefresh();
				}
			},1000);

		}

		@Override
		public void beforeRefresh() {

		}
	};
	static final class ViewHolder{
		RelativeLayout rlContent;
		TextView actionTime;
		TextView title;
		RelativeLayout del;
	}

}
