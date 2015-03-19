package cn.bitlove.babylive.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.ViewPagerHolder;
import cn.bitlove.babylive.fragment.ProfileFragment;
import cn.bitlove.babylive.fragment.RecordListFragment;
import cn.bitlove.babylive.fragment.TimeLineFragment;
import cn.bitlove.babylive.widget.PagerIndicator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragmentActivity extends BaseFragmentActivity implements OnClickListener {
	private ViewPager vp;
	private List<ViewPagerHolder> fragmentList ;
	private ViewGroup indicators ;

	/**
	 * ViewPager 指示器选择事件 
	 * */
	OnClickListener indicatorClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			TextView labelIndicator = (TextView) v.findViewById(R.id.label_indicator);
			for(int i=0;i<indicators.getChildCount();i++){
				TextView label = (TextView) indicators.getChildAt(i).findViewById(R.id.label_indicator);
				if(label == labelIndicator){
					vp.setCurrentItem(i, true);
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_man_fragment);

		init();
		initFragments();
		initAdapter();
		initIndicators();
	}
	private void init(){
		vp = (ViewPager) findViewById(R.id.main_pager);
		vp.setOnPageChangeListener(pageChangeListener);
		indicators = (ViewGroup)findViewById(R.id.indicators);
		//indicators.setOnClickListener(indicatorClick);
	}

	/**
	 * 初始化当前Viewpager的Fragment对象
	 * */
	private void initFragments(){
		fragmentList = new ArrayList<ViewPagerHolder>();
		ViewPagerHolder fh = new ViewPagerHolder();
		fh.fragment = new RecordListFragment();
		fh.indicatorFocus = R.drawable.record_focus;
		fh.indicatorUnFocus = R.drawable.record_unfocus;		
		fh.label = "记录";
		fragmentList.add(fh);

		fh = new ViewPagerHolder();
		fh.fragment = new TimeLineFragment();
		fh.indicatorFocus = R.drawable.time_line_focus;
		fh.indicatorUnFocus = R.drawable.time_line_unfocus;
		fh.label = "时间轴";
		fragmentList.add(fh);
		
		fh = new ViewPagerHolder();
		fh.fragment = new ProfileFragment();
		fh.indicatorFocus = R.drawable.profile_focus;
		fh.indicatorUnFocus = R.drawable.profile_unfocus;
		fh.label = "档案";
		fragmentList.add(fh);
		
	}
	/**
	 * 初始化Viewpager适配器
	 * */
	private void initAdapter(){
		vp.setAdapter(new FragmentPagerAdapter(mFM) {

			@Override
			public int getCount() {
				return fragmentList.size();
			}
			@Override
			public Fragment getItem(int index) {
				return fragmentList.get(index).fragment;
			}
		});
	}
	/**
	 * 初始化ViewPager指示器
	 * */
	private void initIndicators(){
		PagerIndicator pi = new PagerIndicator(mContext);
		pi.build(fragmentList,indicators);
		for(int i=0;i<indicators.getChildCount();i++){
			indicators.getChildAt(i).setOnClickListener(indicatorClick);
		}
	}
	/**
	 * ViewPager 滑动事件
	 * */
	OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			int count = indicators.getChildCount();
			for(int i=0;i<count;i++){
				View indicator = indicators.getChildAt(i);
				View imgIndicator = indicator.findViewById(R.id.img_indicator);
				TextView labelIndicator = (TextView) indicator.findViewById(R.id.label_indicator);
				ViewPagerHolder vph = fragmentList.get(i);
				if(i==position){
					imgIndicator.setBackgroundDrawable(getResources().getDrawable(vph.indicatorFocus));
					labelIndicator.setTextColor(getResources().getColor(R.color.green));
				}else{
					imgIndicator.setBackgroundDrawable(getResources().getDrawable(vph.indicatorUnFocus));
					labelIndicator.setTextColor(Color.GRAY);
				}
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

}
