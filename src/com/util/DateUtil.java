package com.util;

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
}
