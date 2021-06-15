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
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
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
		if("selectBook".equals(method)) {  //分页获取数据
			int currentPage = Integer.valueOf(request.getParameter("currentPage")); //当前页数
			int onePageNumber =Integer.valueOf(request.getParameter("onePageNumber"));   //每一页的数据量
			List<Book> list = bookService.getPageBook(new Book(),currentPage,onePageNumber);
			if(list==null) {
				String errorMsg = bookService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
		}else if("importBook".equals(method)) {  //导入图书信息
			
		}else if("exportBook".equals(method)) {  //导出图书信息
			
		}else if("addBook".equals(method)) {  //添加图书信息
			
		}else if("updateBook".equals(method)) {  //修改图书信息
			
		}else if("searchBook".equals(method)) {  //搜索图书信息
			
		}else if("uploadImg".equals(method)) {  //上传图书图片
			
		}else {
			ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, "未匹配任何方法");
		}
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
