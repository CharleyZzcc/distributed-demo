
 /**
 * FileName:     UserController.java
 * Createdate:   2018-12-19 15:04:44   
 */

package com.lzc.demo.sys.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lzc.demo.common.service.RedisService;
import com.lzc.demo.common.util.CookieUtil;
import com.lzc.demo.sys.user.service.UserService;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 15:04:44   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisService redisService;
	
	@GetMapping("/loginPage")
	public String loginPage(HttpServletRequest request){
		String msg = request.getParameter("msg");
		msg = msg!=null?"："+msg:"";
		return "假装是登录页" + msg;
	}

	@GetMapping("/login")
	@ResponseBody
	public ModelAndView login(@RequestParam(name="username",required=false)String username,
			@RequestParam(name="password",required=false)String password,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		//查询用户是否存在
		if(!userService.login(username, password)) {
			//不用response.setContentType("text/html;charset=utf-8");
			redirectAttributes.addAttribute("msg", "用户名或密码错误");
			return new ModelAndView("redirect:/user/loginPage");
		}
		
		//设置token到redis
		String token = redisService.addToken(username);
		
		//设置token到cookie
		CookieUtil.addToken(response, token);
		
		return new ModelAndView("redirect:/index/index");
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes) {
		
		redisService.removeToken(CookieUtil.getToken(request));
		CookieUtil.removeToken(response);
		
		redirectAttributes.addAttribute("msg", "注销成功");
		return new ModelAndView("redirect:/user/loginPage");
	}
}
