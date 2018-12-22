
 /**
 * FileName:     AuthException.java
 * Createdate:   2018-12-19 17:03:47   
 */

package com.lzc.demo.common.exception;


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
 */

public class AuthException extends RuntimeException{

	public AuthException() {
		super();
	}

	public AuthException(String message) {
		super(message);
	}
}
