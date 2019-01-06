
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

import com.lzc.demo.common.exception.AuthEnum;
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
 * 2018-12-19   LZC         1.0         定义切点authVerify和前置通知beforeAuth  
 * 2018-12-24   LZC         1.1         前置通知beforeAuth补充“异地登录，强制下线”功能  
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
		
		String token = CookieUtil.getToken(request);
		String userId = redisService.getLoginUser(token);
		if(StringUtils.isEmpty(userId)) {
			throw new AuthException(AuthEnum.LOGOUT);
		}
		
		String redisToken = redisService.getToken(userId);
		if(StringUtils.isEmpty(redisToken)) {
			//说明后登录的用户已注销，移除旧token对应的登录用户
			redisService.removeLoginUser(token);
			throw new AuthException(AuthEnum.LOGOUT);
		}else if(!redisToken.equals(token)) {
			//说明当前用户已被下线，移除旧token对应的登录用户
			redisService.removeLoginUser(token);
			throw new AuthException(AuthEnum.OFFLINE);
		}
	}
}
