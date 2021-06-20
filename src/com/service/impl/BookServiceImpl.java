package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.entity.Book;
import com.entity.Book_record;
import com.entity.Book_type;
import com.entity.User;
import com.service.BookService;
import com.util.Dao;
import com.util.StringUtil;

public class BookServiceImpl implements BookService {
	private String errorMsg = "成功";
	
	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	@Override
	public List<Book> getPageBook(Book book, int currentPage, int onePageNumber) {
		List<Book> list = Dao.instance().pageQuery(book,currentPage,onePageNumber);
		if(list==null) { //查询不到
			this.errorMsg = "查询失败";
			return null;
		}
		return list;
	}

	@Override
	public int addBook(Book book) {
		int row = Dao.instance().insert(book);
		return row;
	}

	@Override
	public List<Book_type> getBookType() {
		return Dao.instance().searchAll(new Book_type());
	}

	@Override
	public List<Book> getAllBooks() {
		return Dao.instance().searchAll(new Book());
	}

	@Override
	public List<Book> searchBook(String[] parmName,String[] value, boolean flag) {
		List<String> parmList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		for(int i=0;i<value.length;i++) {
			if(!"null".equals(value[i])) {
				parmList.add(parmName[i]);
				valueList.add(value[i]);
			}
		}
		Dao.instance();
		List<Book> list = Dao
						.setfuzzyQuery(flag) //设置是否开启模糊搜索
						.searchByConditions(new User(), parmList, valueList);
		if(list==null) { //查询不到
			this.errorMsg = "查询失败";
			return null;
		}
		return list;
	}

	@Override
	public int updateBook(Book book) {
		return Dao.instance().update(book);
	}

	@Override
	public int delBook(Book book) {
		return Dao.instance().delete(book);
	}

	@Override
	public List<Book> getOneLend(String user_id) {
		if(user_id==null) {
			errorMsg = "参数为空";
			return null;
		}
		Book_record book_record = new Book_record();
		book_record.setUser_id(Integer.valueOf(user_id));
		List<Book> list = Dao.instance().selectOne(book_record);
		if(list==null||list.isEmpty()) {
			errorMsg = "查询结果为空";
			return null;
		}
		return list;
	}

	@Override
	public List<Book> getAllLend() {
		List<Book> list = Dao.instance().searchAll(new Book_record());
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}

}
