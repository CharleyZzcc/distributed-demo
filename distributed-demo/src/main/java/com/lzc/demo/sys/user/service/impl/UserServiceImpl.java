
 /**
 * FileName:     UserServiceImpl.java
 * Createdate:   2018-12-19 15:14:49   
 */

package com.lzc.demo.sys.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lzc.demo.sys.user.service.UserService;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 15:14:49   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@Service
public class UserServiceImpl implements UserService {

	@Override
	public boolean login(String username, String password) {
		//FIXME 只作为简单的例子来用，不查询数据库
		Map<String, String> userMap = new HashMap<>();
		userMap.put("zc", "zc123");
		userMap.put("admin", "admin123");
		
		String pwd = userMap.get(username);
		if(!StringUtils.isEmpty(pwd) && pwd.equals(password)) {
			return true;
		}
		return false;
	}

}
