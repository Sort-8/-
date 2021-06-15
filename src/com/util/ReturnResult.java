package com.util;

import java.io.PrintWriter;

import com.alibaba.fastjson.JSON;
import com.vo.AjaxResult;

public class ReturnResult<T> extends AjaxResult<T>{
	private static final long serialVersionUID = 1L;

	/**
	 * 返回前端方法
	 * @param ajaxResult
	 * @param printWriter
	 */
	public static void returnResult(AjaxResult<?> ajaxResult,PrintWriter printWriter) {
		String json = JSON.toJSONString(ajaxResult);
		printWriter.write(json); 
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 返回成功消息
	 * @param object
	 * @param code
	 * @param count
	 * @return
	 */
	public static AjaxResult success(Object object, Integer code, Integer count){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.put("code",code);
		ajaxResult.put("msg","成功");
		ajaxResult.put("data",object);
        return ajaxResult;
    }
	
	/**
	 * 返回失败消息
	 * @param code
	 * @param msg
	 * @return
	 */
    public static AjaxResult error(Integer code, String msg){
    	AjaxResult ajaxResult = new AjaxResult();
    	ajaxResult.put("code",code);
		ajaxResult.put("msg",msg);
        return ajaxResult;
    }
}
