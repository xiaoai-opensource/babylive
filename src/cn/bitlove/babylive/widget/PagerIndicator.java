package cn.bitlove.babylive.widget;

import java.util.List;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.ViewPagerHolder;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ViewPager指示器
 * */
public class PagerIndicator extends LinearLayout {
	private List<ViewPagerHolder> mHolders;
	private LayoutInflater mInflater;
	
	public PagerIndicator(Context context) {
		super(context);
		mInflater =(LayoutInflater)context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public PagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInflater =(LayoutInflater)context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	/**
	 * 构架ViewPager指示器
	 * @param holder ViewPagerHolder数组
	 * */
	public void build(List<ViewPagerHolder> holders,ViewGroup parent){
		mHolders = holders;
		ViewPagerHolder holder;
		for(int i=0;i<mHolders.size();i++){
			holder = mHolders.get(i);
			View ItemIndicator = mInflater.inflate(R.layout.widget_indicators_item, null);
			TextView label = (TextView) ItemIndicator.findViewById(R.id.label_indicator);
			label.setText(holder.label);
			View img = ItemIndicator.findViewById(R.id.img_indicator);
			if(i==0){
				img.setBackgroundDrawable(getResources().getDrawable(holder.indicatorFocus));
				label.setTextColor((getResources().getColor(R.color.green)));
			}else{
				img.setBackgroundDrawable(getResources().getDrawable(holder.indicatorUnFocus));
				label.setTextColor(Color.GRAY);
			}
			
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
			ItemIndicator.setLayoutParams(params);
			parent.addView(ItemIndicator);
		}
	}

}
