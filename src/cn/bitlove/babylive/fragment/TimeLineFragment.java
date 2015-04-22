package cn.bitlove.babylive.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.bitlove.babylive.R;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView =  inflater.inflate(R.layout.fragment_time_line, container,false);
		init();
		return mView;
	}
	/**
	 * 初始化
	 */
	public void init(){
		mContext = getActivity().getBaseContext();
		mTimeLine = (ListView) mView.findViewById(R.id.timeLine);
		itemArr = new ArrayList<Record>();
		for(int i=0;i<10;i++){
			Record record = new Record();
			record.setTitle("title : " + i);
			record.setCreateDate("2015-03");
			record.setContent("距离首都基辅首都基辅款手机第三方克里斯蒂减肥三级地方立刻就受到法律就受到咖啡就是浪费首都基辅卢卡斯解放螺丝钉首都基辅款三等奖发牢骚分就受到了罚款就受到福克斯分居两地分居");
			itemArr.add(record);
		}
		for(int i=10;i<20;i++){
			Record record = new Record();
			record.setTitle("title : " + i);
			record.setCreateDate("2015-04");
			itemArr.add(record);
		}
		for(int i=20;i<30;i++){
			Record record = new Record();
			record.setTitle("title : " + i);
			record.setCreateDate("2015-05");
			itemArr.add(record);
		}
		
		initTimeLine();
	}
	/**
	 * 初始化时间轴
	 */
	public void initTimeLine(){
		mTimeLine.setAdapter(new CategoryListAdapter(mContext, itemArr));
	}
}
