package com.service;

import java.util.List;

import com.entity.Lend_limit;

public interface LendLimitService {

	List<Lend_limit> searchLimit(Integer role_id);

	int getIsLimit(String user_id);

}
