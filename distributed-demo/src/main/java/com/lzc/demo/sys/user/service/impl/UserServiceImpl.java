
 /**
 * FileName:     UserServiceImpl.java
 * Createdate:   2018-12-19 15:14:49   
 */

package com.lzc.demo.sys.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lzc.demo.common.entity.User;
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
 * 2018-12-19   LZC         1.0         实现login  
 * 2018-12-24   LZC         1.1         根据接口将改为get，修改实现；重载get  
 */

@Service
public class UserServiceImpl implements UserService {

	@Override
	public User get(String username, String password) {
		//FIXME 只作为简单的例子来用，不查询数据库
		Map<String, User> userMap = new HashMap<>();
		userMap.put("zc", new User("uid_0000000001", "zc", "zc123"));
		userMap.put("admin", new User("uid_0000000002", "admin", "admin123"));
		
		User user = userMap.get(username);
		if(user!=null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String userId) {
		if(StringUtils.isEmpty(userId)) {
			return null;
		}
		//FIXME 只作为简单的例子来用，不查询数据库
		Map<String, User> userMap = new HashMap<>();
		userMap.put("uid_0000000001", new User("uid_0000000001", "zc", "zc123"));
		userMap.put("uid_0000000002", new User("uid_0000000002", "admin", "admin123"));
		return userMap.get(userId);
	}
	
}
