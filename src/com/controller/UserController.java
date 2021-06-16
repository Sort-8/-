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

import com.entity.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;

@WebServlet("/user")
public class UserController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> pNames =  request.getParameterNames();
		while(pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			paramMap.put(name, value);
		}
		
		String usr = request.getParameter("usr");
		String pwd = request.getParameter("pwd");
		String method = request.getParameter("method");
		HttpSession session = request.getSession(true);
		if("login".equals(method)) {
			String value = request.getParameter("imgCode");
			String realValue = (String) session.getAttribute(Constant.ValidateCode+session.getId());
			if(!value.equals(realValue)) { //验证码不匹配
				String errorMsg = "验证码不匹配";
				ajaxResult = ReturnResult.error(Constant.RESCODE_EXIST, errorMsg);
			}else {
				User user = userService.login(usr,pwd);
				if(user==null) {       //帐号密码错误
					String errorMsg = userService.getErrorMsg();
					ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
				}else {
					objMap = new HashMap<String,Object>();
					String sessionID = Constant.IdentityCode+session.getId();
					session.setAttribute(sessionID, user.getUser_id());
					objMap.put("obj", user);
					objMap.put("sessionID", sessionID);
					ajaxResult = ReturnResult.success(objMap, Constant.RESCODE_SUCCESS_MSG, 1);
				}
			}
			
		}else if("register".equals(method) || "addUser".equals(method)) {
			User user = new User();
			user.setUsr(usr);
			user = userService.findOneUser(user);
			if(user!=null) {  //帐号已存在
				ajaxResult = ReturnResult.error(Constant.RESCODE_EXIST, "帐号已存在");
			}else {
				user = new User(Integer.valueOf(request.getParameter("role_id")),
								request.getParameter("usr"),
								request.getParameter("pwd"),
								request.getParameter("name"),
								request.getParameter("email"),
								request.getParameter("sex"),
								(int) System.currentTimeMillis()
						);
				int result = userService.addUser(user);
				if(result==0) {
					String errorMsg = userService.getErrorMsg(); //添加失败
					ajaxResult = ReturnResult.error(Constant.RESCODE_INSERTERROR, errorMsg);
				}else {
					ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
				}
			}
			
		}else if("getAllUser".equals(method)) {
			List<User> list = userService.getAllUser();
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
		}
		else if("updateUser".equals(method)) {
			User user = new User();
			user.setUsr(usr);
			user = userService.findOneUser(user);
			if(!pwd.equals(user.getPwd())) {  //原密码错误
				ajaxResult = ReturnResult.error(Constant.RESCODE_EXIST, "密码错误");
			}else {
				user = new User(Integer.valueOf(request.getParameter("role_id")),
								request.getParameter("usr"),
								request.getParameter("re_pwd"),
								request.getParameter("name"),
								request.getParameter("email"),
								request.getParameter("sex"),
								user.getCreate_time()
							);
				user.setUser_id(Integer.valueOf(request.getParameter("user_id")));
				int result = userService.updateUser(user);
				if(result==0) {
					String errorMsg = userService.getErrorMsg(); //修改失败
					ajaxResult = ReturnResult.error(Constant.RESCODE_MODIFYERROR, errorMsg);
				}else {
					ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
				}
			}
			
		}else if("delUser".equals(method)) {
			User user = new User();
			user.setUser_id(Integer.valueOf(request.getParameter("del_user_id")));
			int result = userService.delete(user);
			if(result==0) {
				String errorMsg = userService.getErrorMsg(); //删除失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_DELETEERROR, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(null, Constant.RESCODE_SUCCESS, result);
			}
			
		}else if("selectUser".equals(method)) {  //分页获取用户数据
			int currentPage = Integer.valueOf(request.getParameter("currentPage")); //当前页数
			int onePageNumber =Integer.valueOf(request.getParameter("onePageNumber"));   //每一页的数据量
			List<User> list = userService.getPageUser(new User(),currentPage,onePageNumber);
			if(list==null) {
				String errorMsg = userService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
			
		}else if("searchUser".equals(method)) {  //搜索用户
			String isfuzzyQuery = request.getParameter("isfuzzyQuery");
			boolean flag = "1".equals(isfuzzyQuery);
			String[] parmName = request.getParameter("parmName").split(";");
			String[] parmValue = request.getParameter("parmValue").split(";");
			List<User> list = userService.searchUser(parmName,parmValue,flag);
			if(list==null) {
				String errorMsg = userService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
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
