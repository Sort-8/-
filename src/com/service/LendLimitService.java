package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Lend_limit;

public interface LendLimitService {

	/*返回所有借阅限制*/
	List<Lend_limit> searchLimit(Integer role_id);

	/*查询用户是否达到借阅限制*/
	int getIsLimit(String user_id);

	/*返回所有借阅限制*/
	List<Map> getAllLimit();

	/*更新呢借阅限制*/
	int updateLimit(Lend_limit lend_limit);

}
