package cn.bitlove.babylive.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;

/**
 * 文件存储、读取操作类
 * */
public class FileUtil {
	final private static int IMAGE_LIMIT_SIZE = 500*300;

	/**
	 * 将bitmap保存至sd卡的指定位置
	 * */
	public static boolean saveBitmaptoSDCard(Bitmap bitmap,String newName){
		boolean bSuccess = true;
		String imgDir = PropertyUtil.getProperty("sd_img_dir");
		File file = new File(imgDir);
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(imgDir+"/"+newName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);// 把数据写入文件

		} catch (FileNotFoundException e) {
			bSuccess = false;
			e.printStackTrace();
		}finally{
			if(fo!=null){
				try {
					fo.flush();
					fo.close();
				} catch (IOException e) {
					bSuccess = false;
					e.printStackTrace();
				}
			}
		}
		return bSuccess;
	}
	/**
	 * 将一个由uri指定的bitmap对象保存至SD卡中
	 * */
	public static Uri saveUriBitMapToSDcard(Uri uri,String newName){
		Uri newUri = null;
		String newPath = saveFileBitMapToSDcard(uri.getPath(), newName);
		if(!"".equals(newPath)){
			newUri = Uri.parse(newPath);
		}
		return newUri;
	}
	/**
	 * 将一个由路径地址指定的bitmap保存至SD卡中
	 * */
	public static String saveFileBitMapToSDcard(String filePath,String newName){
		String newPath = "";
		File file = new File(filePath);
		if(file.isFile()){
			FileInputStream fis = null;
			File outFile = null;
			OutputStream os = null;
			try {
				fis = new FileInputStream(file);
				String path = PropertyUtil.getProperty("sd_img_dir");
				outFile = new File(path+"/"+newName);
				os = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				while(fis.read(buf)>-1){
					os.write(buf);
				}
				os.flush();
				newPath = path+"/"+newName;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{

				try {
					if(fis!=null){
						fis.close();
					}
					if(os!=null){
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return newPath;
	}
	/**
	 * 根据图片路径创建缩略图
	 * */
	public static Bitmap saveFileThumbnailImgToSDcard(Context context,String filePath,int width,String newName){
		String imgDir = PropertyUtil.getProperty("sd_img_dir_thumbnail");
		
		BitMapMetric bmm = getBitmapMetric(filePath);
		float imgWidth = bmm.width;		//图片真实宽度
		float imgHeight = bmm.height;	//图片真实高度

		Bitmap bitmapThumbnail = getZoomBitmap(filePath,IMAGE_LIMIT_SIZE);
		
		float scale = width/imgWidth;			//压缩比例
		int height = (int)(imgHeight * scale);
		
		bitmapThumbnail = ThumbnailUtils.extractThumbnail(bitmapThumbnail, width, height);
		File file = new File(imgDir);
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream fo = null;
		Bitmap _tempBM = null;
		try {
			fo = new FileOutputStream(imgDir+File.separator+newName);
			_tempBM = ThumbnailUtils.extractThumbnail(bitmapThumbnail, UnitSwitch.dp2px(context, width)
					, UnitSwitch.dp2px(context, height));
			_tempBM.compress(Bitmap.CompressFormat.JPEG, 100, fo);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(_tempBM!=null){
				_tempBM.recycle();
			}
			if(fo!=null){
				try {
					fo.flush();
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmapThumbnail;
	}
	/**
	 * 创建图片缩略图
	 * */
	public static Bitmap saveThumbnailImgToSDcard(Context context,Bitmap bitmap,int width,String newName){
		float imgWidth = bitmap.getWidth();		//图片真实宽度
		float imgHeight = bitmap.getHeight();	//图片真实高度
		float scale = width/imgWidth;			//压缩比例

		int height = (int)(imgHeight * scale);
		Bitmap bitmapThumbnail = ThumbnailUtils.extractThumbnail(bitmap, width, height);
		String imgDir = PropertyUtil.getProperty("sd_img_dir_thumbnail");
		File file = new File(imgDir);
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream fo = null;
		Bitmap _tempBM = null;
		try {
			fo = new FileOutputStream(imgDir+"/"+newName);
			_tempBM = ThumbnailUtils.extractThumbnail(bitmap, UnitSwitch.dp2px(context, width)
					, UnitSwitch.dp2px(context, height));
			_tempBM.compress(Bitmap.CompressFormat.JPEG, 100, fo);// 把数据写入文件

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(_tempBM!=null){
				_tempBM.recycle();
			}
			if(fo!=null){
				try {
					fo.flush();
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmapThumbnail;
	}
	/**
	 * 压缩图片
	 * */
	public static Bitmap getZoomBitmap(String filePath,int size){
		if(size==0){
			throw new IllegalArgumentException("size 不能为 零");
		}
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, newOpts);
		
		float imgWidth = newOpts.outWidth;		//图片真实宽度
		float imgHeight = newOpts.outHeight;	//图片真实高度
		
		int zoom = 1;
		while((imgWidth/zoom)*(imgHeight/zoom)>size){
			zoom *=2;
		}
		newOpts.inJustDecodeBounds = false;
		newOpts.inSampleSize = zoom;
		
		Bitmap bitmapZoom = BitmapFactory.decodeFile(filePath, newOpts);
		
		return bitmapZoom;
	}
	/**
	 * 从sdcard读取bitmap
	 * */
	public static Bitmap readBitmapFromSDCard(String pathName){
		Bitmap bitmap = BitmapFactory.decodeFile(pathName);
		return bitmap;
	}
	/**
	 * 计算指定路径下的Bitmap的尺寸信息
	 * */
	public static BitMapMetric getBitmapMetric(String filePath){
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, newOpts);
		
		BitMapMetric bmc = BitMapMetric.getInstance();
		bmc.height = newOpts.outHeight;
		bmc.width = newOpts.outWidth;
		
		return bmc;
	}
	/**
	 * Bitmap尺寸信息
	 * */
	static class BitMapMetric {
		static BitMapMetric bmc = null;
		
		public int height;
		public int width;
		private BitMapMetric(){};
		public static BitMapMetric getInstance(){
			if(bmc==null){
				bmc = new BitMapMetric();
			}
			
			return bmc;
		}
	}
	

	/**
	 * 检测是否有sd卡
	 * */
	public static boolean hasSDCard(){
		String sdStatus = Environment.getExternalStorageState();
		// 检测sd是否可用
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { 
			return true;
		}else{
			return false;
		}
	}
}
