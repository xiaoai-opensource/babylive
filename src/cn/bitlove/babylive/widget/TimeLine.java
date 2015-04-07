package cn.bitlove.babylive.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * 时间线控件，以时间为轴显示数据
 * */
public class TimeLine  extends ExpandableListView{
	private ExpandableListAdapter mAdapter;
	private boolean isExpandOnLoad = true;
	public TimeLine(Context context) {
		super(context);
	}
	public TimeLine(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		super.setAdapter(adapter);
		mAdapter = adapter;
		if(isExpandOnLoad){
			for(int i=0;i<mAdapter.getGroupCount();i++){
				expandGroup(i);				
			}
		}
	}
	/**
	 * 是否默认展开
	 * */
	public void setGroupExpandOnLoad(boolean expand){
		isExpandOnLoad = expand;
	}
	
	
}
