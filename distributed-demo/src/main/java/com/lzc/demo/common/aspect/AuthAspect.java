
 /**
 * FileName:     AuthAspect.java
 * Createdate:   2018-12-19 17:07:36   
 */

package com.lzc.demo.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lzc.demo.common.exception.AuthException;
import com.lzc.demo.common.service.RedisService;
import com.lzc.demo.common.util.CookieUtil;

/**
 * Description: 鉴权切面  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 17:07:36   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@Aspect
@Component
public class AuthAspect {
	
	@Autowired
	private RedisService redisService;

	@Pointcut("!execution(public * com.lzc.demo.sys.user.controller.UserController.*(..)) "
//			+ " && execution(public * com.lzc.demo..controller.*.*(..))")
			+ " && execution(public * com.lzc.demo.sys..controller.*.*(..))")
	public void authVerify() {}
	
	@Before("authVerify()")
	public void beforeAuth() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		String username = redisService.getTokenValue(CookieUtil.getToken(request));
		if(StringUtils.isEmpty(username)) {
			throw new AuthException("用户越权访问");
		}
	}
}
