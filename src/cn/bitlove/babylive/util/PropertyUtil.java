package cn.bitlove.babylive.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置工具类
 * */
public class PropertyUtil {
	private static String configFile = "/assets/global.properties";
	private static Properties ps = null;

	static{
		InputStream is = PropertyUtil.class.getResourceAsStream(configFile);
		try {
			ps = new Properties();
			ps.load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取配置属性
	 * */
	public static String getProperty(String property){
		
		return ps.getProperty(property);
	}
}
