package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.entity.Book;
import com.entity.Book_type;
import com.entity.User;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.util.Constant;
import com.util.Dao;
import com.util.ReturnResult;
import com.util.StringUtil;
import com.util.WriteExcelFile;
import com.vo.AjaxResult;


@WebServlet("/book")
@MultipartConfig
public class BookController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookService bookService = new BookServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取方法
		String method = request.getParameter("method");
		String book_id = request.getParameter("book_id");
		String user_id = request.getParameter("user_id");
		if("getAllBook".equals(method)) {  //获取全部数据
			List<Book> list = bookService.getAllBooks();
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
		}else if("selectBook".equals(method)) {  //分页获取数据
			int currentPage = Integer.valueOf(request.getParameter("currentPage")); //当前页数
			int onePageNumber =Integer.valueOf(request.getParameter("onePageNumber"));   //每一页的数据量
			List<Book> list = bookService.getPageBook(new Book(),currentPage,onePageNumber);
			if(list==null) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
			
		}else if("lendBook".equals(method)) {  //借书
			int res = bookService.lendBook(user_id,book_id);
			if(res==-1) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(res, Constant.RESCODE_SUCCESS, 1);
			}
		}else if("returnBook".equals(method)) {  //借书
			int res = bookService.returnBook(user_id,book_id);
			if(res==-1) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(res, Constant.RESCODE_SUCCESS, 1);
			}
		}else if("importBook".equals(method)) {  //导入图书信息
			Part part = request.getPart("filePath");
		}else if("getTemplate".equals(method)) {  //下载图书信息模板
			
		}else if("exportBook".equals(method)) {  //导出图书信息
			String fileName = StringUtil.getFileName();
			response.setHeader("Content-Type", "application/x-xls"); //发送一次请求
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			List<Book> bookList = bookService.getAllBooks();
			InputStream in = WriteExcelFile.writeExcel(bookList);
			ServletOutputStream out = response.getOutputStream();
			int aRead=0;out.flush();
			byte[] b= new byte[1024];
			while ((aRead = in.read(b))!=-1 && in!=null) {
				out.write(b,0,aRead);
			}
			out.flush();
			in.close();
			out.close();
		}else if("addBook".equals(method)) {  //添加图书信息
			User user = new User();
			user.setUser_id(Integer.valueOf(request.getParameter("user_id")));
			List<User> list = Dao.instance().selectOne(user);
			Book book = new Book(0,
								request.getParameter("code"),
								request.getParameter("name"),
								request.getParameter("author"),
								request.getParameter("press"),
								Integer.valueOf(request.getParameter("lend_num")),
								Integer.valueOf(request.getParameter("type_id")),
								Integer.valueOf(request.getParameter("number")),
								1,
								request.getParameter("url"),
								list.get(0).getName()
							);
			book.setCreate_time(System.currentTimeMillis());
			int result = bookService.addBook(book);
			if(result==0) {
				String errorMsg = bookService.getErrorMsg(); //添加失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_INSERTERROR, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
			}
			
		}else if("updateBook".equals(method)) {  //修改图书信息
			Book book = new Book();
			book.setBook_id(Integer.valueOf(book_id));
			book.setCode(request.getParameter("code"));
			book.setName(request.getParameter("name"));
			book.setAuthor(request.getParameter("author"));
			book.setPress(request.getParameter("press"));
			book.setLend_num(Integer.valueOf(request.getParameter("lend_num")));
			book.setType_id(Integer.valueOf(request.getParameter("type_id")));
			book.setNumber(Integer.valueOf(request.getParameter("number")));
			book.setLend_stu(Integer.valueOf(request.getParameter("lend_stu")));
			book.setUrl(request.getParameter("url"));
			int result = bookService.updateBook(book);
			if(result==0) {
				String errorMsg = bookService.getErrorMsg(); //添加失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_INSERTERROR, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
			}
			
		}else if("searchBook".equals(method)) {  //搜索图书信息
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
			
		}else if("delBook".equals(method)) {  //删除图书信息
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
		
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
