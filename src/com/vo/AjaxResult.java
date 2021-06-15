package com.vo;

import java.util.HashMap;

public class AjaxResult<T> extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**状态码*/
	private int code;
	
	/**返回内容*/
	private String msg;
	
	/**数据对象*/
	private T data;
	
	public AjaxResult<T> put(String key,Object obj) {
		super.put(key, obj);
		return this;
	}
	
	public AjaxResult(int code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public AjaxResult() {
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "data [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}
