package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WriteExcelFile {
	private static HSSFWorkbook wb;
	private static String msg="写入成功";
	public static String getErrorMag() {
		return msg;
	}
	public static <T> InputStream writeExcel(List<T> result) {
		InputStream in = null;
		if(result == null || result.isEmpty()) {
			msg = "数组为空";
			return null;
		}
		wb = new HSSFWorkbook();
		Class<? extends Object> clazz = result.get(0).getClass();
		String tableName = getTableName(clazz); //获取数据库表名
		Field fields[] = clazz.getDeclaredFields(); //获取class对象的成员变量
		Method getters[] = getGetMethod(clazz); //获取get方法
		HSSFSheet sheet =  wb.createSheet(tableName); //创建Excel文件表
		//设置表头
		HSSFRow rowHeader = sheet.createRow(0); 
		for(int i=0; i<fields.length; i++) {
			rowHeader.createCell(i).setCellValue(fields[i].getName()); 
		}
		//写入数据
		for(int i=0; i<result.size(); i++) { //横向写数据
			HSSFRow row = sheet.createRow(i+1);
			if(result.get(i) != null) {
				Object values[] = inokeMethod(result.get(i), getters, null);
				for(int j=0;j<values.length;j++) { //纵向写数据
					HSSFCell cell = row.createCell(j);
					try {
						cell.setCellValue(1*(double) values[j]);
					}catch(Exception e) {
						cell.setCellValue(values[j].toString());
						e.printStackTrace();
					}
				}
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
		} catch (IOException e) {
			msg="HSSFWorkbook对象写入到输出流发生异常";
			return null;
		}
		byte[] brray = bos.toByteArray();
		in = new ByteArrayInputStream(brray);
		return in;
	}
	
	public static <T> InputStream writeTemplateExcel(List<T> result) {
		return null;
		
	}
	
	/**
	 * 返回数据库对应的表名
	 * @param clazz
	 * @return
	 */
	private static String getTableName(Class<? extends Object> clazz) {
		return StringUtil.toLowerFirst(clazz.getSimpleName());
	}
	
	/**
	 * 筛选get方法
	 * @param clazz
	 * @return
	 */
	private static Method[] getGetMethod(Class<?> clazz) {
		Field fields[] = clazz.getDeclaredFields();
		Method methods[] = new Method[fields.length];
		for(int i=0;i<fields.length;i++) {
			String fieldName = fields[i].getName();
			try {
				String name = "get"+StringUtil.toUpperFirst(fieldName);
				methods[i] = clazz.getMethod(name, null);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return methods;
	}
	
	/**
	 * 执行实体方法
	 * @param entity
	 * @param methods
	 * @param args
	 * @return
	 */
	private static Object[] inokeMethod(Object entity,Method[] methods,Object... args) {
		Object objs[] = new Object[methods.length];
		try {
			for(int i=0;i<methods.length;i++){
				objs[i] = methods[i].invoke(entity, args);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return objs;
	}
}
