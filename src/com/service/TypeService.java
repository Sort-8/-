package com.service;

import java.util.List;

import com.entity.Book_type;

public interface TypeService {
	
	public String getErrorMsg();
	
	public List<Book_type> searchBookType(String type_id);

	public List<Book_type> getAllType();
}
