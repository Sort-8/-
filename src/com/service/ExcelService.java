package com.service;

import java.io.InputStream;
import java.util.List;

import com.entity.Book;
import com.entity.User;

public interface ExcelService {

	/*返回错误信息*/
	public String getErrorMsg();
	
	public int importEntity(InputStream in, String fileName, Class<?> clazz);

	public List<Book> getAllBook();

	public List<User> getAllUser();

	public List<String> getEntityTemplate(String entity);

}
