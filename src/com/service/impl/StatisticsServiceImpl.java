package com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.entity.Book;
import com.entity.Book_record;
import com.entity.Book_type;
import com.service.StatisticsService;
import com.util.Dao;

public class StatisticsServiceImpl implements StatisticsService {

	private String errorMsg = "成功";
	
	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	@Override
	public synchronized void addOnline(ServletContext application) {
		if(application.getAttribute("online")!=null) {
			int count = (int) application.getAttribute("online");
			application.setAttribute("online", count+1);
		}else {
			application.setAttribute("online", 1);
		}
	}

	@Override
	public int getOnlineNumber(ServletContext application) {
		int count = 0;
		if(application.getAttribute("online")!=null) {
			count = (int) application.getAttribute("online");
		}
		return count;
	}

	@Override
	public int getLendNumber() {
		List<Book_record> list = Dao.instance().searchAll(new Book_record());
		if(list==null) {
			errorMsg = "查询失败";
			return -1;
		}
		return list.size();
	}

	@Override
	public int getbookNumber() {
		List<Book> list = Dao.instance().searchAll(new Book());
		if(list==null) {
			errorMsg = "查询失败";
			return -1;
		}
		return list.size();
	}

	@Override
	public List<Map> getTypesAndNumber(List<Book_type> typeList, List<Book> bookList) {
		List<Map> list = new ArrayList<Map>();
		Map<Object, Object> resMap = null;
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		Map<Integer, String> nameMap = new HashMap<Integer, String>();
		
		for(int i=0;i<typeList.size();i++) {
			nameMap.put(typeList.get(i).getType_id(), typeList.get(i).getName());
		}
		
		for(int i=0;i<bookList.size();i++) {
			String name = nameMap.get(bookList.get(i).getType_id());
			if(countMap.get(name)!=null) {
				int count = countMap.get(name);
				countMap.put(name, count+1);
			}else {
				countMap.put(name, 1);
			}
		}
		for(int i=0;i<typeList.size();i++) {
			resMap = new HashMap<Object, Object>();
			resMap.put("name", typeList.get(i).getName());
			resMap.put("value", countMap.get(typeList.get(i).getName()));
			list.add(resMap);
		}
		return list;
	}

	@Override
	public List<Map> getPressNumber(List<Book> bookList) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		for(int i=0;i<bookList.size();i++) {
			String name = bookList.get(i).getPress();
			if(map.get(name)!=null) {
				int count = map.get(name);
				map.put(name, count+1);
			}else {
				map.put(name, 1);
			}
		}
		
		Iterator<String> iterator = map.keySet().iterator(); 
		List<Map> list = new ArrayList<Map>();
        while (iterator.hasNext()){
        	String name = iterator.next();
        	Map<String,Object> map1 = new HashMap<String,Object>();
        	map1.put("name", name);
        	map1.put("value", map.get(name));
        	list.add(map1);
        }
		return list;
	}
}
