package com.service;

import java.util.List;

import com.entity.Book;

public interface BookService {
	
	/*返回错误信息*/
	public String getErrorMsg();

	/*返回图书分页信息*/
	public List<Book> getPageBook(Book book, int currentPage, int onePageNumber);

	/*返回单个图书信息*/
	public Book findOneBook(Book book);
	
	/*添加图书信息*/
	public int addBook(Book book);

	/*返回所有图书信息*/
	public List<Book> getAllBooks();

	/*搜索图书信息*/
	public List<Book> searchBook(String[] parmName, String[] parmValue, boolean falg);

	/*更新图书信息*/
	public int updateBook(Book book);

	/*删除图书信息*/
	public int delBook(Book book);

	/*借阅图书*/
	public int lendBook(String user_id, String book_id);

	/*归还图书*/
	public int returnBook(String user_id, String book_id, String record_id);

}
