package cn.bitlove.babylive.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.bitlove.babylive.R;
import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.widget.CategoryListAdapter;
/**
 * 时间线页面
 * */
public class TimeLineFragment extends Fragment {
	private Context mContext;
	private View mView;		//自身界面
	private ListView mTimeLine;	//时间轴
	private ArrayList<Record> itemArr;
	private CategoryListAdapter mAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView =  inflater.inflate(R.layout.fragment_time_line, container,false);
		init();
		return mView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity().getBaseContext();
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
		mTimeLine = (ListView) mView.findViewById(R.id.timeLine);
		initTimeLine();
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
	}
}
