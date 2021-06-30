package com.service.impl;

import java.util.List;

import com.entity.Book_type;
import com.service.TypeService;
import com.util.Dao;

public class TypeServiceImpl implements TypeService {
	private String errorMsg = "成功";
	
	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	@Override
	public List<Book_type> searchBookType(String type_id) {
		if(type_id==null) {
			errorMsg = "参数为空";
			return null;
		}
		Book_type book_type = new Book_type();
		book_type.setType_id(Integer.valueOf(type_id));
		List<Book_type> list = Dao.instance().selectOne(book_type);
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}

	@Override
	public List<Book_type> getAllType() {
		List<Book_type> list = Dao.instance().searchAll(new Book_type());
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}
}
