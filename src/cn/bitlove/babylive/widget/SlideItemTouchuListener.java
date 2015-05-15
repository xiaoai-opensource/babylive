package cn.bitlove.babylive.widget;

import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ListView;

/**
 * ListView 侧滑事件
 */
public class SlideItemTouchuListener implements View.OnTouchListener {

	//最小滑动距离
	private static final int MIN_SLIDE_PX = 10;
	//操作对象
	private ListView mListView;
	//item左侧内容ID
	private int itemLeftId;
	//Item右侧内容Id
	private int itemRightId;
	//上一次事件的X值
	private float lastX=0;
	//Item右侧隐藏内容宽度
	private int itemRightWidth;
	//上次滑动后的TranslationX值
	private float lastActionTranslationX;
	//操作的item
	private View itemView;
	//正在滑动的item
	private View slidingItem;
	//Item右侧隐藏区域
	private View itemRight;
	//Item 正常显示区域
	private View itemLeft;
	//是否做了滑动
	private boolean isDoSlide = false;
	//自动完成动画时间
	private int animDuration = 50;
	
	//自动完成动画Interpolator
	private Interpolator mInterpolator;

	public SlideItemTouchuListener(ListView lv,int leftId,int rightId){
		mListView = lv;
		itemLeftId = leftId;
		itemRightId = rightId;
		mInterpolator = AnimationUtils.loadInterpolator(mListView.getContext()
				, android.R.anim.accelerate_interpolator);
	}
	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = ev.getX();
			itemView = findViewByAction(ev);
			if(itemView==null){
				return false;
			}
			itemRight = itemView.findViewById(itemRightId);
			itemRightWidth = itemRight.getWidth();
			itemLeft = itemView.findViewById(itemLeftId);
			lastActionTranslationX = itemRight.getTranslationX();
			break;
		case MotionEvent.ACTION_MOVE:

			if(isRightItemCanSlide(ev)){
				isDoSlide = true;
				if(slidingItem != itemView && slidingItem!=null){
					View right  = slidingItem.findViewById(itemRightId);
					View left  = slidingItem.findViewById(itemLeftId);
					slideRight(right, left);
				}
				slidingItem = itemView;

				for(int i=0;i<ev.getHistorySize();i++){
					itemRight.setTranslationX(lastActionTranslationX+getDif(ev.getHistoricalX(i)));
					itemLeft.setTranslationX(lastActionTranslationX+getDif(ev.getHistoricalX(i)));
				}
			}else{
				/*
				 * 由于通过onTouch获得的x,并不是一个连续的值，有时候在向右滑动时，将要到临界点时跳过了，导致没有滑动最后，然后
				 * 在松开的时候又把剩下的一段距离给移动完，这样效果不是很好，下面用来解决这个问题
				 */
				if(itemRight.getTranslationX()<0 ){
					itemRight.setTranslationX(-itemRightWidth);
					itemLeft.setTranslationX(-itemRightWidth);
				}
			}
			

			return false;
		case MotionEvent.ACTION_UP:
			if(isDoSlide){
				MotionEvent me = MotionEvent.obtain(ev);
				try{
					//取消点击事件
					me.setAction(MotionEvent.ACTION_CANCEL);
					mListView.onTouchEvent(me);
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					me.recycle();
				}

				doLastSlide(ev);
			}
		default:
			break;
		}


		return false;
	}
	//根据事件位置，查找item
	private View findViewByAction(MotionEvent ev){
		float y = ev.getY();
		for(int i=mListView.getFirstVisiblePosition();i<mListView.getChildCount();i++){
			View child = mListView.getChildAt(i);
			if(child.getTop()<y && child.getBottom()>y){
				return child;
			}

		}
		return null;
	}
	//Item是否向左滑动
	private boolean isSlideLeft(MotionEvent ev){
		return	ev.getX() - lastX <0;
	}
	//计算当前偏移量
	private float getDif(MotionEvent ev){
		return getDif(ev.getX());
	}
	//计算当前偏移量
	private float getDif(float x){
		return x - lastX;
	}
	
	//是否可以滑动
	private boolean isRightItemCanSlide( MotionEvent ev){
		float curDif = getDif(ev);
		if(Math.abs(curDif)<MIN_SLIDE_PX){
			return false;
		}
		boolean isCanSlideLeft = isSlideLeft(ev) && itemRightWidth > Math.abs(curDif) && itemRightWidth>Math.abs(lastActionTranslationX);
		boolean isCanSlideRight = !isSlideLeft(ev) && itemRight.getTranslationX()<0;
		return isCanSlideLeft || isCanSlideRight;
	}
	//做剩下的滑动内容
	private void doLastSlide(MotionEvent ev){
		if(slidingItem == null){
			slidingItem = itemView;
		}
		if(slidingItem==null){
			return;
		}
		View right = slidingItem.findViewById(itemRightId);
		View left = slidingItem.findViewById(itemLeftId);

		if(Math.abs(right.getTranslationX()*2) >= itemRightWidth){
			slideLeft(right,left);
		}else{
			slideRight(right, left);
		}
	}

	//右滑
	private void slideRight(View right,View left){
		ObjectAnimator animator = ObjectAnimator.ofFloat(left, "translationX", 0).setDuration(animDuration);
		animator.setInterpolator(mInterpolator);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(right, "translationX",0).setDuration(animDuration);
		animator2.setInterpolator(mInterpolator);

		animator.start();
		animator2.start();
		isDoSlide = false;
	}
	//左滑
	private void slideLeft(View right,View left){
		ObjectAnimator animator = ObjectAnimator.ofFloat(left, "translationX", -itemRightWidth).setDuration(animDuration);
		animator.setInterpolator(mInterpolator);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(right, "translationX", -itemRightWidth).setDuration(animDuration);
		animator2.setInterpolator(mInterpolator);

		animator.start();
		animator2.start();
	}

}
