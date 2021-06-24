package com.service.impl;

import java.util.List;

import com.entity.Book_record;
import com.entity.Lend_limit;
import com.entity.User;
import com.service.LendLimitService;
import com.util.Dao;

public class LendLimitServiceImpl implements LendLimitService {

private String errorMsg = "成功";
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	@Override
	public List<Lend_limit> searchLimit(Integer role_id) {
		Lend_limit lend_limit = new Lend_limit();
		lend_limit.setRole_id(role_id);
		List<Lend_limit> list = Dao.instance().selectOne(lend_limit);
		if(list==null) {
			errorMsg = "查询失败";
			return null;
		}
		return list;
	}
	@Override
	public int getIsLimit(String user_id) {
		if(user_id==null) {
			errorMsg = "参数为空";
			return -1;
		}
		int id = Integer.valueOf(user_id);
		//找到该用户
		User user = new User();
		user.setUser_id(id);
		List<User> userList = Dao.instance().selectOne(user);
		
		//找到该用户的借阅记录
		Book_record book_record = new Book_record();
		book_record.setUser_id(id);
		book_record.setRecord_type(1); //规定是借阅的类型
		List<Book_record> recordList = Dao.instance().selectOne(book_record);
		
		//找到该用户当前的借书数
		int currentLendNum = recordList.size();
		
		//找到该用户的角色id
		int role_id = userList.get(0).getRole_id();
		
		//找到该用户的限制数
		Lend_limit lend_limit = new Lend_limit();
		lend_limit.setRole_id(role_id);
		List<Lend_limit> limitList = Dao.instance().selectOne(lend_limit);
		int limitNum = limitList.get(0).getMax_number();
		
		//比较是否借阅已满
		return currentLendNum>=limitNum?1:0;
	}

}
