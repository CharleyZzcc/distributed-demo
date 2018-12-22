
 /**
 * FileName:     AuthHandler.java
 * Createdate:   2018-12-19 17:54:03   
 */

package com.lzc.demo.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lzc.demo.common.exception.AuthException;

/**
 * Description: 鉴权处理器  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 17:54:03   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@ControllerAdvice
public class AuthHandler {

	@ExceptionHandler(AuthException.class)
//	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ModelAndView handleAuthException() {
		return new ModelAndView("redirect:/user/loginPage");
	}
}
