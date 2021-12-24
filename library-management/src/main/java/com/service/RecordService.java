package com.service;

import java.util.List;

import com.entity.Book_record;

public interface RecordService {
	
	/*返回错误信息*/
	public String getErrorMsg();
	
	/*返回该用户的借阅记录*/
	public List<Book_record> getOneLend(String user_id);

	/*返回所有的借阅记录*/
	public List<Book_record> getAllLend();
	
	/*搜索借阅限制*/
	public List<Book_record> searchBookRecord(String name, boolean flag);

}
