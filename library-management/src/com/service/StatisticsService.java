package com.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.entity.Book;
import com.entity.Book_type;

public interface StatisticsService {
	
	/*返回错误信息*/
	public String getErrorMsg();

	/*用户登录时动态添加在线人数*/
	public void addOnline(ServletContext application);

	/*获取在线人数*/
	public int getOnlineNumber(ServletContext application);

	/*获取借阅次数*/
	public int getLendNumber();

	/*获取书籍总数*/
	public int getbookNumber();

	/*获取图书各类别以及数量*/
	public List<Map> getTypesAndNumber(List<Book_type> typeList, List<Book> bookList);

	/*获取图书出版社名称以及数量*/
	public List<Map> getPressNumber(List<Book> bookList);

}
