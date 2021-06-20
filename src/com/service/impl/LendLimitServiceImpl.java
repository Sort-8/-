package com.service.impl;

import java.util.List;

import com.entity.Lend_limit;
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

}
