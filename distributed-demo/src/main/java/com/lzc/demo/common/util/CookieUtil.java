
 /**
 * FileName:     CookieUtil.java
 * Createdate:   2018-12-19 15:56:21   
 */

package com.lzc.demo.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzc.demo.common.constant.RedisConstant;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 15:56:21   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

public class CookieUtil {

	/**
	* <p>描述：添加token到cookie中</p>
	* @param response
	* @param token
	* @author LZC
	* @date   2018-12-19 16:00
	 */
	public static void addToken(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie(RedisConstant.TOKEN, token);
		cookie.setMaxAge(RedisConstant.TIME_OUT_MINUTE * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	* <p>描述：移除cookie中的token</p>
	* @param response
	* @author LZC
	* @date   2018-12-19 16:15
	 */
	public static void removeToken(HttpServletResponse response) {
		Cookie cookie = new Cookie(RedisConstant.TOKEN, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	* <p>描述：获取token</p>
	* @param request
	* @return
	* @author LZC
	* @date   2018-12-19 16:46
	 */
	public static String getToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(RedisConstant.TOKEN.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
