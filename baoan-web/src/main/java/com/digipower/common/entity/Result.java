package com.digipower.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Result implements Serializable {
	// 成功返回
	public static String RESULT_CODE_SUCCESS = "0";
	// 异常返回
	public static String RESULT_CODE_ERROR = "500";

	// 返回码
	protected String code;
	// 返回提示信息
	protected String message;
	// 返回消息类型
	protected Integer type;

	// 返回数据
	protected Map<String, Object> datas = new HashMap<String, Object>();

	public Result() {
		this.code = RESULT_CODE_SUCCESS;
		this.message = "success";
	}

	public static Result ok() {
		return new Result();
	}

	public static Result ok(String msg) {
		return ok(RESULT_CODE_SUCCESS, msg);
	}

	public static Result ok(String code, String msg) {
		Result r = new Result();
		r.code = code;
		r.message = msg;
		return r;
	}

	public static Result error(String msg) {
		return error(RESULT_CODE_ERROR, msg);
	}

	public static Result error(String code, String msg) {
		Result r = new Result();
		r.code = code;
		r.message = msg;
		return r;
	}

	public static Result error(String code, String msg, Integer type) {
		Result r = new Result();
		r.code = code;
		r.message = msg;
		r.type = type;
		return r;
	}

	/**
	 * 
	 * @Title: setData @Description: 添加返回数据到默认 data 节点 @param: @param
	 *         obj @param: @return @return: Result @throws
	 */
	public Result setDatas(Object obj) {
		datas.put("data", obj);
		return this;
	}

	public Result setDatas(Map rs) {
		datas.putAll(rs);
		return this;
	}

	public Result setDatas(List list) {
		datas.put("list", list);
		return this;
	}

	public Result setDatas(String name, Object value) {
		datas.put(name, value);
		return this;
	}

	public Result setSid(Long sid) {
		setDatas("sid", sid);
		return this;
	}

	public Result setSid(String sid) {
		setDatas("sid", sid);
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDatas() {
		return datas;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
