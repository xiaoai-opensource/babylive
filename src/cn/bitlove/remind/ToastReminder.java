package cn.bitlove.remind;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Toast提示
 * */
public class ToastReminder {
	private static Toast mToast;
	private static Handler mHandler;
	private static void init(Context context){
		if(mToast==null){
			mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
			mHandler = new Handler(context.getMainLooper());
		}
	}
	/**
	 * toast
	 * */
	public static void showToast(final Context context,final String text,final int duration){
		init(context);
		mToast.cancel();
		mHandler.post(new Runnable() { 
			@Override 
			public void run() { 
				mToast.setText(text);
				mToast.setDuration(duration);
				mToast.show();
			} 
		}); 

	}
}
