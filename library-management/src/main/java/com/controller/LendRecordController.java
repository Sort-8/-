package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Book_record;
import com.service.RecordService;
import com.service.impl.RecordServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;

@WebServlet("/record")
public class LendRecordController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RecordService recordService = new RecordServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取方法
		String method = request.getParameter("method");
		
		//获取用户借阅记录
		if("getMyLend".equals(method)) {  
			List<Book_record> list = recordService.getOneLend(request.getParameter("user_id"));
			if(list==null) {
				String errorMsg = recordService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
		//获取所有借阅记录
		}else if("getAllLend".equals(method)) {  //获取所有用户借阅记录
			List<Book_record> list = recordService.getAllLend();
			if(list==null||list.isEmpty()) {
				String errorMsg = recordService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
			
		//搜索借阅记录
		}else if("searchLend".equals(method)) {  
			String isFuzzyQuery = request.getParameter("isFuzzyQuery");
			boolean flag = "1".equals(isFuzzyQuery);
			String name = request.getParameter("book_name");
			List<Book_record> list = recordService.searchBookRecord(name,flag);
			if(list==null) {
				String errorMsg = recordService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
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
