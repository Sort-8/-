package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.entity.Book;
import com.entity.Book_record;
import com.entity.Book_type;
import com.service.BookService;
import com.util.Dao;

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
		book.setBorrow_num(book.getNumber());
		book.setCreate_time(System.currentTimeMillis());
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
						.searchByConditions(new Book(), parmList, valueList);
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

	@Override
	public int lendBook(String user_id, String book_id) {
		if(user_id==null||book_id==null) {
			errorMsg = "参数为空";
			return -1;
		}
		int bookID = Integer.valueOf(book_id);
		int userID = Integer.valueOf(user_id);
		
		//查看可借阅数
		Book book = new Book();
		book.setBook_id(bookID);
		List<Book> bookList = Dao.instance().selectOne(book);
		int bookBorrowNumber = bookList.get(0).getBorrow_num();
		if(bookBorrowNumber <=0) { //库存不足
			errorMsg = "库存不足";
			return -1;
		}
		
		//可借阅数减1
		book = bookList.get(0);
		book.setBorrow_num(bookBorrowNumber-1);
		if(bookBorrowNumber-1==0) {
			book.setLend_stu(0); //不可借状态
		}
		Dao.instance().update(book);
		
		//添加借阅记录
		Book_record book_record = new Book_record();
		book_record.setRecord_type(1);//记录类型
		book_record.setUser_id(userID);//记录人
		book_record.setBook_id(book.getBook_id());//图书id
		book_record.setBook_name(book.getName());//图书名称
		book_record.setRecord_time(System.currentTimeMillis());//记录时间
		Dao.instance().insert(book_record);
		
		return 1;
	}

	@Override
	public int returnBook(String user_id, String book_id) {
		if(user_id==null||book_id==null) {
			errorMsg = "参数为空";
			return -1;
		}
		int bookID = Integer.valueOf(book_id);
		int userID = Integer.valueOf(user_id);
		
		//图书可借阅数加1并修改图书借阅状态
		Book book = new Book();
		book.setBook_id(bookID);
		book.setBorrow_num(userID);
		
		//增加借阅记录
		return 0;
	}

}
