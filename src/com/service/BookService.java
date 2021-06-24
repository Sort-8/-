package com.service;

import java.util.List;

import com.entity.Book;
import com.entity.Book_type;

public interface BookService {
	
	/*返回错误信息*/
	public String getErrorMsg();

	public List<Book> getPageBook(Book book, int currentPage, int onePageNumber);

	public int addBook(Book book);

	public List<Book_type> getBookType();

	public List<Book> getAllBooks();

	public List<Book> searchBook(String[] parmName, String[] parmValue, boolean falg);

	public int updateBook(Book book);

	public int delBook(Book book);

	public List<Book> getOneLend(String user_id);

	public List<Book> getAllLend();

	public int lendBook(String user_id, String book_id);

	public int returnBook(String user_id, String book_id);

}
