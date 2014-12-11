package cn.bitlove.babylive.util;

/**
 * 判断滑动方向
 * */
public class SlideJudge {
	//滑动方向
	public static int UP=0;
	public static int RIGHT=1;
	public static int DOWN=2;
	public static int LEFT=3;
	public static int NONE=4;
	//认为为滑动的最小距离
	private static int minDif = 20;

	public static int judgeDirection(float dx,float dy){
		if(Math.abs(dy)>minDif && Math.abs(dx)<minDif){
			if(dy>0){
				return DOWN;
			}else{
				return UP;
			}
			
		}
		//判断左右
		if(Math.abs(dx)>minDif && Math.abs(dy)<minDif){
			if(dx>0){
				return RIGHT;
			}else{
				return LEFT;
			}
		}
		
		return NONE;
	}
}
