package cn.bitlove.babylive.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import cn.bitlove.babylive.R;
import cn.bitlove.babylive.activity.NewRecordActivity;
import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.widget.CategoryListAdapter;
import cn.bitlove.babylive.widget.CategoryListAdapter.CateType;
/**
 * 时间线页面
 * */
public class TimeLineFragment extends Fragment {
	private Context mContext;
	private Activity mActivity;
	private LayoutInflater mInflater;
	private View mView;		//自身界面
	private ListView mTimeLine;	//时间轴
	private ArrayList<Record> itemArr;
	private CategoryListAdapter mAdapter;
	private View vMore;		//更多视图
	private PopupWindow pMoreWindow ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView =  inflater.inflate(R.layout.fragment_time_line, container,false);
		init();
		initEvt();
		return mView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		mActivity = getActivity();
		itemArr = initData();

		if(mAdapter==null){
			mAdapter = new CategoryListAdapter(mContext, itemArr);
			//恢复保存状态
			if(savedInstanceState!=null){
				HashMap<Integer, Object> mCatePositions = (HashMap<Integer, Object>) savedInstanceState.getSerializable("mCatePositions");
				mAdapter.mCatePositions = mCatePositions;
			}	
		}
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("mCatePositions", mAdapter.mCatePositions);
		super.onSaveInstanceState(outState);
	}
	/**
	 * 初始化
	 */
	public void init(){
		mInflater = LayoutInflater.from(mContext);				
		vMore = mView.findViewById(R.id.view_more);
		mTimeLine = (ListView) mView.findViewById(R.id.timeLine);
		initTimeLine();
	}
	/*
	 * 初始化事件
	 */
	public void initEvt(){
		vMore.setOnClickListener(showMoreListener);
	}
	/**
	 * 初始化数据
	 * @return
	 */
	public ArrayList<Record> initData(){
		itemArr = RecordData.getInstance(mContext).queryAllRecords();
		return itemArr;
	}
	/**
	 * 初始化时间轴
	 */
	public void initTimeLine(){
		mTimeLine.setAdapter(mAdapter);
		/**
		 * 条目点击事件
		 * */
		mTimeLine.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Record record = (Record) mTimeLine.getItemAtPosition(position);
				Intent intent = new Intent(mContext,NewRecordActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("record", record);
				intent.putExtras(bundle);
				mActivity.startActivity(intent);
			}
		});
	}
	
	/**
	 * 显示更多事件
	 */
	OnClickListener showMoreListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(pMoreWindow==null){
				View content = mInflater.inflate(R.layout.time_line_pop_more, (ViewGroup) mView,false);
				View actionMonth = content.findViewById(R.id.popActionMonth);
				View actionYear = content.findViewById(R.id.popActionYear);
				
				actionMonth.setOnClickListener(listItemListener);
				actionYear.setOnClickListener(listItemListener);
				pMoreWindow = new PopupWindow(content,200,100);
			}
			
			if(pMoreWindow.isShowing()){
				pMoreWindow.dismiss();
			}else{
				pMoreWindow.setFocusable(true);
				pMoreWindow.setOutsideTouchable(true);
				// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景  
				pMoreWindow.setBackgroundDrawable(new BitmapDrawable());  
				pMoreWindow.showAsDropDown(v,0,1);
			}
			
		}
	};
	
	OnClickListener listItemListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.popActionMonth:
				mAdapter.setCateType(CateType.MONTH);
				mAdapter.notifyDataSetChanged();
				pMoreWindow.dismiss();
				break;
			case R.id.popActionYear:
				mAdapter.setCateType(CateType.YEAR);
				mAdapter.notifyDataSetChanged();
				pMoreWindow.dismiss();
				break;
			}
		}
	};
}
