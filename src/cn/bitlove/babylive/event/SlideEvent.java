package cn.bitlove.babylive.event;

import android.view.MotionEvent;
import android.view.View;


public class SlideEvent {
	private static SlideEvent mSlideEvent=null;
	private MotionEvent mEvent;	//事件
	private View mView;			//事件发生对象
	private float mStartX=0,mEndX=0,mStartY=0,mEndY=0;	//事件触发是的开始和结束位置
	private int mSlideMax=20;	//认为为滑动的最大值
	private int mDirection=-1;

	int mTargetWidth=0;
	float mRightPoint=0;
	float mLeftPoint=0;


	private SlideEvent(){}
	public static SlideEvent getInstance(MotionEvent event,View view){
		if(mSlideEvent==null){
			mSlideEvent = new SlideEvent();
		}
		mSlideEvent.mView = view;
		mSlideEvent.mEvent = event;
		mSlideEvent.init();
		return mSlideEvent;
	}
	public void init(){
		//计算事件源的大小，将其左右分为三份，分别对应左、中、右

		mTargetWidth = mView.getMeasuredWidth();
		mRightPoint = (float) ((mTargetWidth * 3.0) / 5);
		mLeftPoint = (float) ((mTargetWidth * 2.0) / 5);
	}
	/**
	 * 分发屏幕点击事件
	 * */
	public void dispatchClickEvent(ClickEventListener evts){
		int action = mEvent.getAction();
		//事件源的X位置
		float evtX;

		if(action==MotionEvent.ACTION_DOWN){
			mStartX = mEvent.getRawX();
		}else if(action == MotionEvent.ACTION_UP){
			mEndX = mEvent.getRawX();
			//认为为滑动
			if(Math.abs(mEndX - mStartX)>mSlideMax){
				return;
			}
			evtX = mEvent.getRawX();			
			if(evtX>mRightPoint){
				evts.onRightClick();
			}else if(evtX<mLeftPoint){
				evts.onLeftClick();
			}else{
				evts.onCenterClick();
			}
		}
	}

	/**分发屏幕滑动事件
	 * 
	 * */
	public void dispatchSlideEvent(SlideEventLister evts){
		int action = mEvent.getAction();
		float xLen=0;
		float yLen=0;
		//事件源的X位置
		if(action==MotionEvent.ACTION_DOWN){
			mStartX = mEvent.getRawX();
			mStartY = mEvent.getRawY();
			evts.onSlideBegin();
		}else if(action == MotionEvent.ACTION_MOVE){
			mEndX = mEvent.getRawX();
			mEndY = mEvent.getRawY();
			xLen = mEndX - mStartX;
			yLen = mEndY - mStartY;

			//处理左右滑动
			if(xLen>mSlideMax && Math.abs(yLen)<mSlideMax){
				mDirection = SlideEventLister.RIGHT;
				evts.onSlideRight( xLen);
				return;
			}else if(xLen<-mSlideMax && Math.abs(yLen)<mSlideMax){
				mDirection = SlideEventLister.LEFT;
				evts.onSlideLeft(-xLen);
				return;
			}
			//处理上下滑动
			if(yLen>0 && Math.abs(xLen)<mSlideMax){
				mDirection = SlideEventLister.DOWN;
				evts.onSlideDown(yLen);
				return;
			}else if(yLen<0 && Math.abs(xLen)<mSlideMax){
				mDirection = SlideEventLister.UP;
				evts.onSlideUp(-yLen);
				return;
			}

		}else if (action == MotionEvent.ACTION_UP || action==MotionEvent.ACTION_CANCEL) {
			mEndX = mEvent.getRawX();
			mEndY = mEvent.getRawY();
			xLen = mEndX - mStartX;
			yLen = mEndY - mStartY;
			//认为为 点击
			if(Math.abs(xLen)<mSlideMax && Math.abs(yLen)<mSlideMax){
				evts.onclick();
			}else{
				evts.onSlideEnd(mDirection);
			}
			return;
		}

	}
	/**
	 * 屏幕点击事件
	 * */
	public interface ClickEventListener {
		public void onLeftClick();
		public void onRightClick();
		public void onCenterClick();
	}
	/**
	 * 滑动事件
	 * */
	public interface SlideEventLister{
		public static int UP=0;
		public static int RIGHT=1;
		public static int DOWN=2;
		public static int LEFT=3;

		public void onSlideBegin();
		public void onSlideLeft(float dif);
		public void onSlideRight(float dif);
		public void onSlideUp(float dif);
		public void onSlideDown(float dif);
		public void onSlideEnd(int direction);
		public void onclick();
	}
}
