package com.service;

import java.util.List;
import com.entity.User;

public interface UserService {
	
	/*返回错误信息*/
	public String getErrorMsg();
	
	/*登录*/
	public User login(String usr, String pwd);
	
	/*添加用户*/
	public int addUser(User user);
	
	/*查询用户*/
	public User findOneUser(User user);
	
	/*修改用户*/
	public int updateUser(User user);
	
	/*删除用户*/
	public int delete(User user);
	
	/*对用户数据进行分页*/
	public List<User> getPageUser(User user, int currentPage, int onePageNumber);
	
	/*搜索用户*/
	public List<User> searchUser(String[] parmName,String[] value, boolean flag);
	
	/*获取所有用户*/
	public List<User> getAllUsers();
}
