package cn.bitlove.babylive.widget;

import cn.bitlove.babylive.util.SlideJudge;

import cn.bitlove.babylive.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
/**
 * 滑动item的Listview
 * */
public class SlideListView extends ListView {
	private ListView mCurView = this;
	private Context mContext =this.getContext();
	private int delWidth;	//删除按钮宽度
	private float mFirstX;	//滑动起始X
	private float mFirstY;	//滑动起始Y
	private int mSlideDirection;	//滑动方向
	int lastScrollX=0;				//上一次滑动X位置
	boolean isFirst= false;			//是否第一次滑动
	boolean isSliding = false;
	private View slideView = null;	//滑动的视图

	final private String tag = "SlideListView";

	public SlideListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 覆盖onTouchEvent ,实现滑动删除Item功能，主要原理是根据MotionEvent来判断滑动意图
	 * 然后通过scrollTo函数来实现item移动 露出删除区域
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mFirstX = ev.getX();
			mFirstY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float lastX = ev.getX();
			float lastY = ev.getY();
			float difX = lastX-mFirstX;
			float difY = lastY-mFirstY;
			if(!isSliding){
				mSlideDirection = SlideJudge.judgeDirection(difX,difY);
			}

			if(mSlideDirection==SlideJudge.LEFT){
				isSliding = true;
				slideView = mCurView.getChildAt(getItemByXY((int)mFirstX, (int)mFirstY));
				if(isFirst){
					lastScrollX = slideView.getScrollX();
					isFirst = false;
				}
				int moveTo = (int) (lastScrollX - difX);
				if(Math.abs(moveTo)<delWidth){
					slideView.scrollTo(moveTo, 0);
				}
				return false;
			}else if(mSlideDirection==SlideJudge.RIGHT){
				isSliding = true;
				slideView = mCurView.getChildAt(getItemByXY((int)mFirstX, (int)mFirstY));
				if(isFirst){
					lastScrollX = slideView.getScrollX();
					isFirst = false;
				}
				int moveTo = (int) (lastScrollX - difX);
				if(moveTo>0){
					slideView.scrollTo(moveTo, 0);
				}
				return false;
			}else if(mSlideDirection==SlideJudge.UP || mSlideDirection==SlideJudge.DOWN){
				clearAllDel();
			}
			return super.onTouchEvent(ev);
		case MotionEvent.ACTION_UP:
			if(mSlideDirection==SlideJudge.LEFT ){
				slideView.scrollTo((delWidth), 0);
				isSliding = false;
				mSlideDirection = SlideJudge.NONE;
			}else if(mSlideDirection==SlideJudge.RIGHT){
				slideView.scrollTo(0, 0);
				isSliding = false;
				slideView = null;
				mSlideDirection = SlideJudge.NONE;
			}else{
				return super.onTouchEvent(ev);
			}
			isFirst = true;
			break;
		default:

		}
		return true;
	}
	private void init(){
		//初始化删除区域宽度
		delWidth = mContext.getResources().getDimensionPixelOffset(R.dimen.item_del_width);
	}
	/**
	 * 根据坐标获取Listview的Item的位置
	 * */
	private int getItemByXY(int x,int y){
		int position = mCurView.pointToPosition(x, y);
		int lastVisiblePos = mCurView.getFirstVisiblePosition();
		return position - lastVisiblePos;
	}
	/**
	 * 清除所有item的删除状态
	 * */
	private void clearAllDel(){

		for(int i=0;i<mCurView.getChildCount();i++){
			View view = mCurView.getChildAt(i);
			if(view.getScrollX()!=0){
				view.scrollTo(0, 0);
			}
		}
	}
}
