package cn.bitlove.babylive.widget;

import cn.bitlove.babylive.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListView;

/**
 * 瀑布流Listview
 * */
public class WaterfallListView extends ListView {
	final private String tag="WaterfallListView";
	private Context mContext;
	private LayoutInflater mInflater;		
	final int REFRESH_IDEL=0;		//空闲中
	final int REFRESH_PREPARE=1;	//准备刷新
	final int REFRESH_ING=2;		//刷新中
	final int REFRESH_End=3;		//刷新完成
	private int mRefreshState = REFRESH_IDEL;		//刷新状态
	
	private View mListFoot;		//底部刷新区域
	private int mFirstTouchY;	//第一次触摸Listview的X位置
	private int mLastTouchY;	//上一次触摸Listview的X位置
	private int mSlideDirection;	//滑动方向
	final private int SLIDE_UP = 0;	//向上滑动
	final private int SLIDE_DOWN=1;	//向下滑动

	private OnScrollListener mScrollListener = null;	//滚动监听器
	public WaterfallListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		mContext = getContext();
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initListeners();
		setOnScrollListener(mScrollListener);
		initFooter();
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastTouchY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:

			if(ev.getY()>mLastTouchY){
				mSlideDirection = SLIDE_UP;
			}else{
				mSlideDirection = SLIDE_DOWN;
			}
			mLastTouchY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	/** 初始化滚动监听器*/
	private void initListeners(){
		mScrollListener = new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(totalItemCount==0){
					return ;
				}
				Log.i(tag,"mSlideDirection="+(mSlideDirection==SLIDE_DOWN));
				Log.i(tag,"firstVisibleItem="+firstVisibleItem
						+" visibleItemCount="+visibleItemCount
						+" totalItemCount="+totalItemCount
						);
				//抵达底部
				if(firstVisibleItem+visibleItemCount==totalItemCount){
					//if(mSlideDirection==SLIDE_DOWN){
						//准备刷新状态
						mRefreshState = REFRESH_PREPARE;
						prepareRefresh();
					//}else{
						//mRefreshState = REFRESH_IDEL;
						//cancelRefresh();
					//}

				}

			}
		};

	}
	/**
	 * 初始化底部刷新内容
	 * */
	private void initFooter(){
		if(mListFoot==null){
			mListFoot = mInflater.inflate(R.layout.foot_waterfall_listview, null);
			addFooterView(mListFoot);
		}
	}
	/**
	 * 准备刷新
	 * */
	private void prepareRefresh(){
		
	}
	/**
	 * 取消刷新
	 * */
	private void cancelRefresh(){
		if(mListFoot!=null){
			//removeFooterView(mListFoot);
			//mListFoot = null;
		}
	}


}
