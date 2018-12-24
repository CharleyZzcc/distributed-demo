
 /**
 * FileName:     AuthEnum.java
 * Createdate:   2018-12-24 22:56:52   
 */

package com.lzc.demo.common.exception;

import lombok.Getter;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-24 22:56:52   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-24   LZC         1.0         提供LOGOUT、OFFLINE 
 */

@Getter
public enum AuthEnum {
	
	/**	未登录状态 */
	LOGOUT(11, "未登录，越权访问"),
	/** 离线状态 */
	OFFLINE(12, "异地登录，强制下线");

	private int code;
	
	private String message;
	
	AuthEnum(int code, String message){
		this.code = code;
		this.message = message;
	}
}
