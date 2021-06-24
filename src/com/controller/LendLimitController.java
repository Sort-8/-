package com.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.LendLimitService;
import com.service.impl.LendLimitServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;
import com.entity.Lend_limit;

@WebServlet("/limit")
public class LendLimitController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LendLimitService lendLimitService = new LendLimitServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	public LendLimitController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> pNames =  request.getParameterNames();
		while(pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			paramMap.put(name, value);
		}
		
		String method = request.getParameter("method");
		String role_id = request.getParameter("role_id");
		String user_id = request.getParameter("user_id");
		System.out.println();
		HttpSession session = request.getSession(true);
		
		if("getIsLimit".equals(method)) {
			int falg = lendLimitService.getIsLimit(user_id);
			ajaxResult = ReturnResult.success(falg, Constant.RESCODE_SUCCESS, 1);
		}else if("updateLimit".equals(method)) {
			
			
		}else if("searchLimit".equals(method)) {
			List<Lend_limit> list = lendLimitService.searchLimit(Integer.valueOf("role_id")) ;
		}
		
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
