
 /**
 * FileName:     AuthException.java
 * Createdate:   2018-12-19 17:03:47   
 */

package com.lzc.demo.common.exception;

import lombok.Getter;

/**
 * Description: 鉴权异常  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 17:03:47   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 * 2018-12-24   LZC         1.1         新增code成员变量  
 */
@Getter
public class AuthException extends RuntimeException{
	
	private static final long serialVersionUID = -4492950504876332995L;
	
	private int code;

	public AuthException(int code) {
		super();
		this.code = code;
	}

	public AuthException(AuthEnum authEnum) {
		super(authEnum.getMessage());
		this.code = authEnum.getCode();
	}
	
	
}
