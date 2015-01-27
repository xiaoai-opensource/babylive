package cn.bitlove.babylive.fragment;

import java.util.ArrayList;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.widget.TimeLine;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
/**
 * 时间线页面
 * */
public class TimeLineFragment extends Fragment {
	private LayoutInflater mInflater;
	private Context mContext;
	RecordData recordData;
	private View mView;		//自身界面
	private TimeLine mTimeLine;	//时间轴
	private ArrayList<String> groupArr;
	private ArrayList<String> itemArr;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		mView =  inflater.inflate(R.layout.fragment_time_line, null);
		init();
		return mView;
	}
	/**
	 * 初始化
	 */
	public void init(){
		mContext = getActivity().getBaseContext();
		recordData = RecordData.getInstance(mContext);
		mTimeLine = (TimeLine) mView.findViewById(R.id.timeLine);
		groupArr = (ArrayList<String>) recordData.queryAllActionMonth();
		
		itemArr = new ArrayList<String>();
		for(int i=0;i<50;i++){
			itemArr.add("item 几点几分类似稍等几分类似的风景江苏大丰路快速减肥斯蒂芬家里舒服亟待立法精神分裂发送到立刻就翻了三番是的风景绿色几分类似分是就翻了三番江苏大丰 : " + i);
		}
		
		initTimeLine();
	}
	/**
	 * 初始化时间轴
	 */
	public void initTimeLine(){
		mTimeLine.setAdapter(new ExpandableListAdapter() {
			
			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {
			}
			
			@Override
			public void registerDataSetObserver(DataSetObserver observer) {
			}
			
			@Override
			public void onGroupExpanded(int groupPosition) {
			}
			
			@Override
			public void onGroupCollapsed(int groupPosition) {
			}
			
			@Override
			public boolean isEmpty() {
				return false;
			}
			
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				return false;
			}
			
			@Override
			public boolean hasStableIds() {
				return false;
			}
			
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
				View group = mInflater.inflate(R.layout.time_line_group, null);
				TextView tv = (TextView) group.findViewById(R.id.groupView);
				tv.setText(groupArr.get(groupPosition));
				return group;
			}
			
			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}
			
			@Override
			public int getGroupCount() {
				return groupArr.size();
			}
			
			@Override
			public Object getGroup(int groupPosition) {
				return groupArr.get(groupPosition);
			}
			
			@Override
			public long getCombinedGroupId(long groupId) {
				return groupId;
			}
			
			@Override
			public long getCombinedChildId(long groupId, long childId) {
				return groupId*childId;
			}
			
			@Override
			public int getChildrenCount(int groupPosition) {
				return 10;
			}
			
			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
				View item = mInflater.inflate(R.layout.time_line_item, null);
				TextView tv = (TextView) item.findViewById(R.id.timeLineItem);
				tv.setText(itemArr.get(childPosition));
				return item;
			}
			
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return groupPosition*childPosition;
			}
			
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return itemArr.get(childPosition);
			}
			
			@Override
			public boolean areAllItemsEnabled() {
				return true;
			}
		});
	}
}
