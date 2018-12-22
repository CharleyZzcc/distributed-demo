
 /**
 * FileName:     ProductHandler.java
 * Createdate:   2018-12-20 00:31:50   
 */

package com.lzc.demo.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzc.demo.common.exception.SaleException;

/**
 * Description: 销售处理器  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-20 00:31:50   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-20   LZC         1.0         1.0 Version  
 */

@ControllerAdvice
public class SaleHandler {

	@ExceptionHandler(SaleException.class)
	@ResponseBody
	public String handleSaleException(SaleException e) {
		return e.getMessage();
	}
}
