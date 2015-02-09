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
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragmentActivity extends BaseFragmentActivity implements OnClickListener {
	private List<ViewPagerHolder> fragmentList ;
	private ViewGroup indicators ;
	private FragmentManager mFManager;

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
					changeTab(i);
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlideRole(R.layout.fragment_main_menu);
		setSlideRole(R.layout.activity_man_fragment);

		initActionBar();
		init();
		initFragments();
		initIndicators();

		//chage Tab to 0
		changeTab(0);
	}
	private void init(){
		indicators = (ViewGroup)findViewById(R.id.indicators);
		indicators.setOnClickListener(indicatorClick);
		mFManager = getSupportFragmentManager();
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
	 * 切换Tab页
	 * @param position	要切换到的Tab下标
	 * @author luoaz
	 * */
	private void changeTab(int position){
		if(position<0){
			throw new IllegalArgumentException("position 不能小于0");
		}
		try{
			//change Tab
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

			//change fragment
			Fragment replace = fragmentList.get(position).fragment;
			mFManager.beginTransaction().replace(R.id.mainFragment, replace)
			.commit();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IllegalAccessError("Tab 页内容初始化有问题，请检查");

		}
	}

}
