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

import com.entity.Book_type;
import com.service.TypeService;
import com.service.impl.TypeServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;

@WebServlet("/type")
public class TypeController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TypeService typeService = new TypeServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取方法
		String method = request.getParameter("method");
		
		//获取全部图书类型
		if("getAllType".equals(method)) {  
			List<Book_type> typeList = typeService.getAllType();
			ajaxResult = ReturnResult.success(typeList, Constant.RESCODE_SUCCESS, typeList.size());
		
		//搜索图书类别
		}else if("searchBookType".equals(method)) {  
			String type_id = request.getParameter("type_id");
			List<Book_type> typeList = typeService.searchBookType(type_id);
			ajaxResult = ReturnResult.success(typeList, Constant.RESCODE_SUCCESS, typeList.size());
		
		}else {
			ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, "未匹配任何方法");
		}
		
		//返回消息
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
