
 /**
 * FileName:     UserServiceImpl.java
 * Createdate:   2018-12-19 15:14:49   
 */

package com.lzc.demo.sys.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lzc.demo.common.entity.User;
import com.lzc.demo.sys.user.repository.UserRepository;
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
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User get(String username, String password) {
		//只作为简单的例子来用，数据库中的用户密码都没有加密
		User user = userRepository.findByUsername(username);
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
		return userRepository.findByUserId(userId);
	}
	
}
