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
		//获取方法
		String method = request.getParameter("method");
		String role_id = request.getParameter("role_id");
		String user_id = request.getParameter("user_id");
		//获取所有借阅限制
		if("getAllLimit".equals(method)) {
			List<Map> list = lendLimitService.getAllLimit();
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
		//判断借阅是否已达限制
		}else if("getIsLimit".equals(method)) {
			int falg = lendLimitService.getIsLimit(user_id);
			ajaxResult = ReturnResult.success(falg, Constant.RESCODE_SUCCESS, 1);
		//修改借阅限制
		}else if("updateLimit".equals(method)) {
			Lend_limit lend_limit = new Lend_limit(
					Integer.valueOf(request.getParameter("limit_id")),
					Integer.valueOf(request.getParameter("role_id")),
					Integer.valueOf(request.getParameter("max_number")),
					Integer.valueOf(request.getParameter("max_time"))
				);
			int res = lendLimitService.updateLimit(lend_limit);
			ajaxResult = ReturnResult.success(res, Constant.RESCODE_SUCCESS, 1);
		//搜索限制
		}else if("searchLimit".equals(method)) {
			List<Lend_limit> list = lendLimitService.searchLimit(
					Integer.valueOf(role_id)) ;
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
