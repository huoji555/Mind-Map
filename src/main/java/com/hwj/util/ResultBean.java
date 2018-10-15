package com.hwj.util;

import lombok.Data;

import java.io.Serializable;

/**
 * controller 统一返回结果规范
 * @author lierlei@xingyoucai.com
 * @create 2018-03-13 11:12
 **/
@Data
public class ResultBean<T> implements Serializable{
	private static final long serialVersionUid = 1L;
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int NO_PERMISSION = 2;
	private String msg = "success";
	private int code = SUCCESS;
	private T data;

	public ResultBean() {
		super();
	}
	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.code = FAIL;
		this.msg = e.toString();
	}
}
