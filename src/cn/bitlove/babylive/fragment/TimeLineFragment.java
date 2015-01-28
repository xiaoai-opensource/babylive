package cn.bitlove.babylive.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.data.RecordData;
import cn.bitlove.babylive.entity.Record;
import cn.bitlove.babylive.util.RecordMetaUtil;
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
	private RecordData recordData;
	private View mView;		//自身界面
	private TimeLine mTimeLine;	//时间轴
	private ArrayList<String> groupArr;
	private ArrayList<String> itemArr;
	private HashMap<String, Object> children;
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
		children = new HashMap<String, Object>();
		itemArr = new ArrayList<String>();
		for(int i=0;i<50;i++){
			itemArr.add("item 几点几分类似稍等几分类似的风景江苏大丰路快速减肥斯蒂芬家里舒服亟待立法精神分裂发送到立刻就翻了三番是的风景绿色几分类似分是就翻了三番江苏大丰 : " + i);
		}
		
		initTimeLine();
	}
	/**
	 * 获取指定组的子对象
	 * @param groupKey  要获取的组的id
	 * */
	public ArrayList<Record> getChildren(String groupKey){
		ArrayList<Record> items = (ArrayList<Record>) children.get(groupKey);
		if(items == null){
			items = (ArrayList<Record>) recordData.queryMonthRecord(groupKey);
			children.put(groupKey, items);
		}
		
		return items;
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
				String groupKey = getGroup(groupPosition).toString();
				ArrayList<Record> items = getChildren(groupKey);
				return items.size();
			}
			
			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
				String groupKey = getGroup(groupPosition).toString();
				ArrayList<Record> items = getChildren(groupKey);
				
				Record record = items.get(childPosition);
				View item = mInflater.inflate(R.layout.time_line_item, null);
				TextView tvTitle = (TextView) item.findViewById(R.id.timeLineItemTitle);
				tvTitle.setText(record.getTitle());
				
				TextView tvTime = (TextView) item.findViewById(R.id.timeLineItemTime);
				String date = record.getActionDate();
				date = date.substring(date.lastIndexOf("-")+1);
				tvTime.setText(date+"日");
				
				TextView tvContent = (TextView) item.findViewById(R.id.timeLineItemContent);
				tvContent.setText(record.getContent());
 				RecordMetaUtil.parseContent(mContext,tvContent,300*300);
				
				return item;
			}
			
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return groupPosition*childPosition;
			}
			
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				String groupKey = getGroup(groupPosition).toString();
				ArrayList<Record> items = getChildren(groupKey);
				return items.get(childPosition);
			}
			
			@Override
			public boolean areAllItemsEnabled() {
				return true;
			}
		});
	}
}
