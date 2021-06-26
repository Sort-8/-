package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Book;
import com.entity.Book_type;
import com.service.BookService;
import com.service.StatisticsService;
import com.service.TypeService;
import com.service.impl.BookServiceImpl;
import com.service.impl.StatisticsServiceImpl;
import com.service.impl.TypeServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;

@WebServlet("/statistics")
public class StatisticsController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StatisticsService statisticsService = new StatisticsServiceImpl();
	TypeService typeService = new TypeServiceImpl();
	BookService bookService = new BookServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String, Object> objMap = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取方法
		String method = request.getParameter("method");
		
		//获取累计数
		if("getStatistics".equals(method)) {
			objMap = new HashMap<String, Object>();
			int onlineNum = statisticsService.getOnlineNumber(getServletContext());
			int LendNum = statisticsService.getLendNumber();
			int bookNum = statisticsService.getbookNumber();
			objMap.put("onlineNum", onlineNum);//获取在线人数
			objMap.put("LendNum", LendNum);//获取借阅次数
			objMap.put("bookNum", bookNum);//获取书籍数量
			ajaxResult = ReturnResult.success(objMap, Constant.RESCODE_SUCCESS, objMap.size());
		//获取图书分类分布
		}else if("getTypesNumber".equals(method)) {
			List<Map> list = new ArrayList<Map>();
			List<Book_type> typeList = typeService.getAllType();
			List<Book> bookList = bookService.getAllBooks();
			list = statisticsService.getTypesAndNumber(typeList, bookList);
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
		//获取出版社分类分布	
		}else if("getPressNumber".equals(method)) {
			List<Book> bookList = bookService.getAllBooks();
			List<Map> list = new ArrayList<Map>();
			list = statisticsService.getPressNumber(bookList);
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			
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
