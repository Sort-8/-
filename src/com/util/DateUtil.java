package com.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;

public class DateUtil {

	public static boolean isCellDateFormatted(Cell cell) {
		String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(cell.getStringCellValue());
		return m.matches();
	}
	
	public static Date getNow() {
		Date birthday1 = new java.sql.Date(new Date(System.currentTimeMillis()).getTime());//2021-06-15
        String LgTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));  
        System.out.println(LgTime);  
        System.out.println(System.currentTimeMillis());
		return birthday1;
	}
}
