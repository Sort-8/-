package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.entity.Book_record;
import com.service.RecordService;
import com.util.Dao;

public class RecordServiceImpl implements RecordService {
	
	private String errorMsg = "成功";
	
	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	@Override
	public List<Book_record> getOneLend(String user_id) {
		if(user_id==null) {
			errorMsg = "参数为空";
			return null;
		}
		Book_record book_record = new Book_record();
		book_record.setUser_id(Integer.valueOf(user_id));
		book_record.setRecord_type(1);
		List<Book_record> list = Dao.instance().selectOne(book_record);
		if(list==null||list.isEmpty()) {
			errorMsg = "查询结果为空";
			return null;
		}
		return list;
	}

	@Override
	public List<Book_record> getAllLend() {
		Dao.instance().setfuzzyQuery(false, true);
		List<Book_record> list = Dao.instance().searchAll(new Book_record());
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}
	
	@Override
	public List<Book_record> searchBookRecord(String name,boolean flag) {
		List<String> pname = new ArrayList<String>();
		List<String> pvalue = new ArrayList<String>();
		pname.add("book_name");
		pvalue.add(name);
		Dao.instance().setfuzzyQuery(flag,true);
		List<Book_record> list = Dao.instance().searchByConditions(new Book_record(), pname, pvalue);
		return list;
	}
}
