package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Book;
import com.entity.User;
import com.service.BookService;
import com.service.UserService;
import com.service.impl.BookServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.Constant;
import com.util.Dao;
import com.util.ReturnResult;
import com.vo.AjaxResult;


@WebServlet("/book")
@MultipartConfig
public class BookController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookService bookService = new BookServiceImpl();
	UserService userService = new UserServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取方法
		String method = request.getParameter("method");
		String book_id = request.getParameter("book_id");
		String user_id = request.getParameter("user_id");
		String record_id = request.getParameter("record_id");
		
		//获取所有图书信息
		if("getAllBook".equals(method)) {  //获取全部数据
			List<Book> list = bookService.getAllBooks();
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
		//分页获取数据
		}else if("selectBook".equals(method)) {  
			int currentPage = Integer.valueOf(request.getParameter("currentPage")); //当前页数
			int onePageNumber =Integer.valueOf(request.getParameter("onePageNumber"));   //每一页的数据量
			List<Book> list = bookService.getPageBook(new Book(),currentPage,onePageNumber);
			if(list==null) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
		//借书
		}else if("lendBook".equals(method)) {  
			int res = bookService.lendBook(user_id,book_id);
			if(res==-1) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(res, Constant.RESCODE_SUCCESS, 1);
			}
		//还书
		}else if("returnBook".equals(method)) {  
			int res = bookService.returnBook(user_id,book_id,record_id);
			if(res==-1) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(res, Constant.RESCODE_SUCCESS, 1);
			}
		//添加图书信息
		}else if("addBook".equals(method)) {  
			User user = new User();
			user.setUser_id(Integer.valueOf(request.getParameter("user_id")));
			user = userService.findOneUser(user);
			Book book = new Book(0,
								request.getParameter("code"),
								request.getParameter("name"),
								request.getParameter("author"),
								request.getParameter("press"),
								0,
								Integer.valueOf(request.getParameter("type_id")),
								Integer.valueOf(request.getParameter("number")),
								1,
								request.getParameter("url"),
								user.getName()
							);
			book.setCreate_time(System.currentTimeMillis());
			int result = bookService.addBook(book);
			if(result==0) {
				String errorMsg = bookService.getErrorMsg(); //添加失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_INSERTERROR, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
			}
		//修改图书信息
		}else if("updateBook".equals(method)) {  
			Book book = new Book();
			book.setBook_id(Integer.valueOf(book_id));
			book = bookService.findOneBook(book);
			Dao.instance().selectOne(book);
			List<Book> bookList = Dao.instance().selectOne(book);
			book = bookList.get(0);
			book.setCode(request.getParameter("code"));
			book.setName(request.getParameter("name"));
			book.setAuthor(request.getParameter("author"));
			book.setPress(request.getParameter("press"));
			book.setType_id(Integer.valueOf(request.getParameter("type_id")));
			book.setNumber(Integer.valueOf(request.getParameter("number")));
			if(request.getParameter("url")!="")
				book.setUrl(request.getParameter("url"));
			int result = bookService.updateBook(book);
			if(result==0) {
				String errorMsg = bookService.getErrorMsg(); //添加失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_INSERTERROR, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
			}
		//搜索图书信息
		}else if("searchBook".equals(method)) {  
			String isFuzzyQuery = request.getParameter("isFuzzyQuery");
			boolean flag = "1".equals(isFuzzyQuery);
			String[] parmName = request.getParameter("parmName").split(";");
			String[] parmValue = request.getParameter("parmValue").split(";");
			List<Book> list = bookService.searchBook(parmName,parmValue,flag);
			if(list==null) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
		//删除图书信息
		}else if("delBook".equals(method)) {  
			Book book = new Book();
			book.setBook_id(Integer.valueOf(book_id));
			int result = bookService.delBook(book);
			if(result==0) {
				String errorMsg = bookService.getErrorMsg(); //删除失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_DELETEERROR, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
			}
		}else {
			ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, "未匹配任何方法");
		}
		//返回数据
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
