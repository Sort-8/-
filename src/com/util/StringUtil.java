package com.util;

import javax.servlet.http.Part;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 2021/6/8
 * @author 庞海
 *
 */
public class StringUtil {
	/**
	 * 从part对象中获取文件名
	 * @param part
	 * @return
	 */
	public static String getFileName(Part part) {
		String fileName = part.getHeader("content-disposition");
		//文件名判空
		if(fileName.lastIndexOf("=") + 2 == fileName.length()-1 ) {
			return null;
		}
		//筛选出文件名
		fileName = fileName.substring(fileName.lastIndexOf("=")+2,fileName.length()-1);
		//去掉绝对路径
		int index = fileName.lastIndexOf("\\");
		if(index==-1) {
			return fileName;
		}
		return fileName.substring(index+1);
	}
	
	/**
	 * 返回6位随机数
	 * @return
	 */
	public static String getRandNum() {
		Random random = new Random();
		int num = random.nextInt(999999);
		if(num<100000) {
			num+=100000;
		}
		return num+"";
	}
	
	/**
	 * 首字母转大写
	 * @param name
	 * @return
	 */
	public static String toUpperFirst(String name) {
		if(name!=null) {
			char first = Character.toUpperCase(name.charAt(0));
			return first+name.substring(1);
		}
		return null;
	}
	
	/**
	 * 首字母转小写
	 * @param name
	 * @return
	 */
	public static String toLowerFirst(String name) {
		if(name!=null) {
			char first = Character.toLowerCase(name.charAt(0));
			return first+name.substring(1);
		}
		return null;
	}
	
	public static boolean isNum(String str) {
		Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}
}
