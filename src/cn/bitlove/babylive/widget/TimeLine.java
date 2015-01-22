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
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		for(int i=0;i<mAdapter.getGroupCount();i++){
			expandGroup(i);
		}
	}

}
