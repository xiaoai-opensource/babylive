package cn.bitlove.babylive.widget;

import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ListView;

/**
 * ListView 侧滑
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
	//上一次事件的Y值
	private float lastY=0;
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

	public SlideItemTouchuListener(ListView lv,int leftId,int rightId){
		mListView = lv;
		itemLeftId = leftId;
		itemRightId = rightId;
	}
	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = ev.getX();
			lastY = ev.getY();
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
				slidingItem = itemView;
				
				itemRight.setTranslationX(lastActionTranslationX+getDif(ev));
				itemLeft.setTranslationX(lastActionTranslationX+getDif(ev));
			}

			return false;
		case MotionEvent.ACTION_UP:
			if(isDoSlide){
				MotionEvent me = MotionEvent.obtain(ev);
				me.setAction(MotionEvent.ACTION_CANCEL);
				mListView.onTouchEvent(me);

				doLastSlide(ev,true);
			}
			System.out.println("isDoslide 2 : " + isDoSlide);
		default:
			break;
		}


		return false;
	}
	//根据事件位置，查找item
	private View findViewByAction(MotionEvent ev){
		float y = ev.getY();
		for(int i=0;i<mListView.getCount();i++){
			View child = mListView.getChildAt(i);
			if(child.getTop()<y && child.getBottom()>y){
				return child;
			}

		}
		return null;
	}
	//List 是否上下滑动
	private boolean isSlideVertical(MotionEvent ev){

		return Math.abs(ev.getY()-lastY) > Math.abs(ev.getX()-lastX);
	}
	//Item是否向左滑动
	private boolean isSlideLeft(MotionEvent ev){
		return	ev.getX() - lastX <0;
	}
	//计算当前偏移量
	private float getDif(MotionEvent ev){
		return ev.getX() - lastX;
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
	private void doLastSlide(MotionEvent ev,boolean isNormal){
		Interpolator interpolator = AnimationUtils.loadInterpolator(mListView.getContext()
				, android.R.anim.accelerate_interpolator);
		
		if(slidingItem == null){
			slidingItem = itemView;
		}
		View right = slidingItem.findViewById(itemRightId);
		View left = slidingItem.findViewById(itemLeftId);
				
		if(Math.abs(right.getTranslationX()*2) >= itemRightWidth){
			slideLeft(right,left,interpolator);
		}else{
			slideRight(right, left, interpolator);
		}
	}
	
	private void slideRight(View right,View left,Interpolator interpolator ){
		ObjectAnimator animator = ObjectAnimator.ofFloat(left, "translationX", 0).setDuration(animDuration);
		animator.setInterpolator(interpolator);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(right, "translationX",0).setDuration(animDuration);
		animator2.setInterpolator(interpolator);

		animator.start();
		animator2.start();
		isDoSlide = false;
	}
	private void slideLeft(View right,View left,Interpolator interpolator ){
		ObjectAnimator animator = ObjectAnimator.ofFloat(left, "translationX", -itemRightWidth).setDuration(animDuration);
		animator.setInterpolator(interpolator);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(right, "translationX", -itemRightWidth).setDuration(animDuration);
		animator2.setInterpolator(interpolator);

		animator.start();
		animator2.start();
	}

}
