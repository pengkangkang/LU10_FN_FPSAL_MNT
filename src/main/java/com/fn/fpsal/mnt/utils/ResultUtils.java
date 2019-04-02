package com.fn.fpsal.mnt.utils;

import com.fn.fpsal.mnt.bean.Result;

public class ResultUtils {
	
	public ResultUtils() {}
	
	/**
	 * 返回成功，传入返回体具体参数
	 * @param data
	 * @param msg
	 * @return
	 */
	public static Result<Object> success(Object data,String msg){
		Result<Object> result = new Result<Object>();
		result.setCode("S");
		result.setMessage(msg);
		result.setData(data);
		return result;
	}
	
	/**
	 * 返回成功，传入返回体具体参数
	 * @param data
	 * @return
	 */
	public static Result<Object> success(Object data){
		return success(data,"");
	}
	
	/**
	 * 提供给不需要出参的接口
	 * @param data
	 * @return
	 */
	public static Result<Object> success(){
		return success(null,"");
	}
	
	/**
	 * 返回错误信息
	 * @param msg
	 * @return
	 */
	public static Result<Object> error(String msg){
		Result<Object> result = new Result<Object>();
		result.setCode("F");
		result.setMessage(msg);
		return result;
	}
}
