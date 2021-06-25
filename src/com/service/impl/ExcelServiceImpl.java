package com.service.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.entity.Book;
import com.entity.User;
import com.service.ExcelService;
import com.util.Dao;
import com.util.ReadExcelFile;

public class ExcelServiceImpl implements ExcelService {

	private String errorMsg = "成功";
	
	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	@Override
	public int importEntity(InputStream in, String fileName, Class clazz) {
		List<Map> list = ReadExcelFile.getExcelInfo(in,fileName);
		if(list==null) {
			errorMsg = ReadExcelFile.getErrorInfo();
			return 0;
		}
		int res = Dao.instance().mapInsert(list, clazz);
		if(res==0) {
			errorMsg = Dao.instance().getErrorMsg();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Book> getAllBook() {
		List<Book> list = Dao.instance().searchAll(new Book());
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}

	@Override
	public List<User> getAllUser() {
		List<User> list = Dao.instance().searchAll(new User());
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}

	@Override
	public List<String> getEntityTemplate(String entity) {
		List<String> list = new ArrayList<String>();
		if("book".equals(entity)) {
			list.add("IBSN");
			list.add("图书名称");
			list.add("作者");
			list.add("出版社");
			list.add("馆藏");
			list.add("图片地址");
		}else if("user".equals(entity)) {
			list.add("帐号");
			list.add("密码");
			list.add("用户名");
			list.add("邮箱");		
			list.add("性别");		
		}
		return list;
	}


}
