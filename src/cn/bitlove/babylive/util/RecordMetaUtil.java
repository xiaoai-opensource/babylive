package cn.bitlove.babylive.util;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

import cn.bitlove.babylive.R;
public class RecordMetaUtil {

	final private static String IMG_PRE="cn.bitlove.babylive.img.";
	final private static String CONTENT_IMG="\\{imgurl=[0-9a-fA-F]{32}\\}";
	/**
	 * 获取记录中 图片 资源文件的id
	 * */
	public static String getNewImgId(){
		String imgId="";
		String idToMD = IMG_PRE + System.currentTimeMillis();
		imgId = getMD5(idToMD);

		return imgId;
	}
	
	/**
	 * 解析内容中的资源信息
	 * */
	public static void parseContent(Context context,TextView etContent){
		String content = etContent.getText().toString();
		
		 SpannableStringBuilder ssb = new SpannableStringBuilder(content);
	     Pattern pattern = Pattern.compile(CONTENT_IMG);
	     Matcher matcher = pattern.matcher(content);
	     int start,end;
	     while(matcher.find()){
	    	 start = matcher.start();
	    	 end = matcher.end();
	    	 String imgDir = PropertyUtil.getProperty("sd_img_dir_thumbnail");
	    	 String imgName = content.substring(start+8,end-1);
	    	 ssb.setSpan(new ImageSpan(FileUtil.readBitmapFromSDCard(imgDir+"/"+imgName)),
	    			 start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	     }
	     
	     etContent.setText(ssb);
	}
	/**
	 * 清空内容中的资源占位符
	 * */
	public static String emptyMetaInfo(String etContent){
		 return etContent.replaceAll(CONTENT_IMG, "");
	}
	/**
	 * 编译内容中的图片地址
	 * */
	public static String compileContentImg(String imgName){
		return "{imgurl="+imgName+"}";
	}
	/**
	 * MD5 字符串
	 * */
	private static String getMD5(String strToMD5){
		String md5Str = "";
		byte[] md5Bytes;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md5Bytes = md.digest(strToMD5.getBytes("utf-8"));
			StringBuffer strBuffer = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				strBuffer.append(Integer.toHexString((0xff & md5Bytes[i])| 0xffffff00).substring(6).toUpperCase());
			}

			md5Str = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return md5Str;

	}
}
