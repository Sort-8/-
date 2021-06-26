package com.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel工具类
 * 日期：2021/6/8
 * @author 庞海
 * @version 1.0
 * @param <T>
 * 
 */
public class ReadExcelFile<T> {
	private static String errorMsg = "读取成功";
	
	/**
	 * 获取错误信息
	 * @return
	 */
	public static String getErrorInfo() {
		return errorMsg;
	}
	
	/**
	 * 通过io输入流获取Excel文件信息
	 * @param io
	 * @param fileName
	 * @return
	 */
	public static List<Map> getExcelInfo(InputStream io,String fileName ,Map<String,String> parmMap){
		if(!validateExcel(fileName)) { //验证Excel文件
			return null;
		}
		int totalRows = 0; //总行数
		int totalCols = 0;
		Workbook wb = null;
		List<Map> list = new ArrayList<Map>();
		try {
			if(excelVersion(fileName)==2003) {
				wb = new HSSFWorkbook(io);            //创建工作簿
			}else if(excelVersion(fileName)==2007) {
				wb = new XSSFWorkbook(io);
			}else {
				errorMsg = "文件不支持";
				return null;
			}
			Sheet sheet = wb.getSheetAt(0);    //获取第一个工作表
			totalRows = sheet.getLastRowNum(); //获取总行数
			Row row = sheet.getRow(0);         //获取第一行
			totalCols = row.getLastCellNum();  //获取总列数
			List<String> arrayList = new ArrayList<>();
			
	        // 把需要封装的实体类的属性名存入arrayList
	        for (int i = 0; i < totalCols; i++) {
	            String name = (String)getCellFormatValue(row.getCell(i));
	            arrayList.add(name);
	        }
			
	        // 循环遍历excel表格，把每条数据封装成 map集合，再放入list集合中
	        for (int i = 1; i <= totalRows; i++) {
	            Map<String, Object> map = new HashMap<>();
	            row = sheet.getRow(i);
	            if (row != null){
	                for (int j = 0; j < totalCols; j++) {
	                	Object cellData = null;
	                	if(StringUtil.isNum(row.getCell(j).toString()))
	                		cellData = Math.round(Double.valueOf(row.getCell(j).toString()));
	                	else
	                		cellData = row.getCell(j).toString();
	                    map.put(parmMap.get(arrayList.get(j)), cellData); // map 封装
	                }
	                map.put("create_time", System.currentTimeMillis());
	                list.add(map); // map存入list
	            }
	        }
		}catch(Exception e) {
			errorMsg = "读入出错";
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	/**
	 * 用于获取表格中的数据方法
	 * @param cell
	 * @return
	 */
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = Integer.valueOf((int) cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
	
	/**
	 * 验证Excel文件
	 * @param filePath
	 * @return
	 */
	public static boolean validateExcel(String filePath) {
		if(filePath == null) {
			errorMsg = "文件不能为空";
			return false;
		}else if(excelVersion(filePath)==0) {
			errorMsg = "文件不支持";
			return false;
		}
		return true;
	}
	
	/**
	 * 判断Excel版本
	 * @param name
	 * @return
	 */
	public static int excelVersion(String fileName) {
		int version = 0;
		String type = fileName.substring(fileName.lastIndexOf(".")+1);
		if("xls".equals(type)) {
			version = 2003;
		}else if("xlsx".equals(type)) {
			version = 2007;
		}
		return version;
	}
}
