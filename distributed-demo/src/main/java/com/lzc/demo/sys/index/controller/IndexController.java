
 /**
 * FileName:     IndexController.java
 * Createdate:   2018-12-19 16:32:21   
 */

package com.lzc.demo.sys.index.controller;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lzc.demo.common.service.RedisService;
import com.lzc.demo.common.util.CookieUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 16:32:21   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

	@Autowired
	private RedisService redisService;
	
	@GetMapping("/index")
	public String index(HttpServletRequest request) {
		String username = redisService.getTokenValue(CookieUtil.getToken(request));
		//主要区分请求哪台服务器
		log.info(username + "：访问首页");
		return "欢迎访问首页，当前登录用户：" + username;
	}
}
