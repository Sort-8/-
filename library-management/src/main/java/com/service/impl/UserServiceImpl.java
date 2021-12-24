package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.entity.User;
import com.service.UserService;
import com.util.Dao;

public class UserServiceImpl implements UserService {

	private String errorMsg = "成功";
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	@Override
	public User login(String usr, String pwd) {
		User user = new User();
		user.setUsr(usr);
		user.setPwd(pwd);
		//判断用户名密码
		Object[] paraName = new Object[2];
		Object[] values = new Object[2];
		paraName[0]="usr";paraName[1]="pwd";
		values[0]=usr;values[1]=pwd;
		List<User> list = Dao.instance().selectOne(user);
		if(list==null||list.isEmpty()) { //查询不到
			this.errorMsg = "帐号或密码错误";
			return null;
		}
		list.get(0).setPwd("******");
		return list.get(0);
	}
	
	@Override
	public int setLoginOut(ServletContext application) {
		if(application.getAttribute("online")!=null) {
			int count = (int) application.getAttribute("online");
			if(count!=0) {
				application.setAttribute("online", count-1);
				System.out.println("用户登出");
			}else {
				return 0;
			}
		}else {
			return -1;
		}
		return 1;
	}
	
	@Override
	public int addUser(User user) {
		int row = Dao.instance().insert(user);
		return row;
	}
	
	@Override
	public User findOneUser(User user) {
		List<User> list = Dao.instance().selectOne(user);
		if(list==null||list.isEmpty()) { //查询不到
			this.errorMsg = "帐号不存在";
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public int updateUser(User user) {
		int row = Dao.instance().update(user);
		return row;
	}
	
	@Override
	public int delete(User user) {
		int row = Dao.instance().delete(user);
		return row;
	}
	
	@Override
	public List<User> getPageUser(User user, int currentPage, int onePageNumber) {
		List<User> list = Dao.instance().pageQuery(user,currentPage,onePageNumber);
		if(list==null) { //查询不到
			this.errorMsg = "查询失败";
			return null;
		}
		return list;
	}
	
	@Override
	public List<User> searchUser(String[] parmName,String[] value, boolean flag) {
		List<String> parmList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		for(int i=0;i<value.length;i++) {
			if(!"null".equals(value[i])) {
				parmList.add(parmName[i]);
				valueList.add(value[i]);
			}
		}
		Dao.instance();
		List<User> list = Dao
						.setfuzzyQuery(flag,false) //设置是否开启模糊搜索
						.searchByConditions(new User(), parmList, valueList);
		if(list==null) { //查询不到
			this.errorMsg = "查询失败";
			return null;
		}
		return list;
	}

	@Override
	public List<User> getAllUsers() {
		return Dao.instance().setfuzzyQuery(false, true).searchAll(new User());
	}
}
