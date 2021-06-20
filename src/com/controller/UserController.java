package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Book;
import com.entity.User;
import com.service.BookService;
import com.service.UserService;
import com.service.impl.BookServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.util.StringUtil;
import com.util.WriteExcelFile;
import com.vo.AjaxResult;

@WebServlet("/user")
public class UserController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserServiceImpl();
	BookService bookService = new BookServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	Map<String,String> paramMap = new HashMap<String,String>();
	Map<String, Object> objMap = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> pNames =  request.getParameterNames();
		while(pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			paramMap.put(name, value);
//			System.out.println(name+" "+value);
		}
		
		String usr = request.getParameter("usr");
		String pwd = request.getParameter("pwd");
		String method = request.getParameter("method");
		System.out.println(request.getParameter("data"));
		HttpSession session = request.getSession(true);
		//登录
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
		//注册或添加用户
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
		//获取所有用户
		}else if("getAllUser".equals(method)) {
			List<User> list = userService.getAllUsers();
			ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
		}
		//修改用户信息
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
		//删除用户
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
		//获取分页用户数据
		}else if("selectUser".equals(method)) {  
			int currentPage = Integer.valueOf(request.getParameter("currentPage")); //当前页数
			int onePageNumber =Integer.valueOf(request.getParameter("onePageNumber"));   //每一页的数据量
			List<User> list = userService.getPageUser(new User(),currentPage,onePageNumber);
			if(list==null) {
				String errorMsg = userService.getErrorMsg(); //查询失败
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOEXIST, errorMsg);
			}else {
				ajaxResult = ReturnResult.success(list, Constant.RESCODE_SUCCESS, list.size());
			}
		//搜索用户
		}else if("searchUser".equals(method)) { 
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
		//导出用户
		}else if("exportUser".equals(method)) { 
			String fileName = StringUtil.getFileName();
			response.setHeader("Content-Type", "application/x-xls"); //发送一次请求
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			List<User> userList = userService.getAllUsers();
			InputStream in = WriteExcelFile.writeExcel(userList);
			ServletOutputStream out = response.getOutputStream();
			int aRead=0;out.flush();
			byte[] b= new byte[1024];
			while ((aRead = in.read(b))!=-1 && in!=null) {
				out.write(b,0,aRead);
			}
			out.flush();
			in.close();
			out.close();
		
		}else if("exportUser".equals(method)) {
			
		}else {
			ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, "未匹配任何方法");
		}
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
