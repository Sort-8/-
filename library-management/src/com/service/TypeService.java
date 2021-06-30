package com.service;

import java.util.List;

import com.entity.Book_type;

public interface TypeService {
	
	/*返回错误信息*/
	public String getErrorMsg();
	
	/*搜索图书类型*/
	public List<Book_type> searchBookType(String type_id);

	/*返回所有图书类型*/
	public List<Book_type> getAllType();
}
