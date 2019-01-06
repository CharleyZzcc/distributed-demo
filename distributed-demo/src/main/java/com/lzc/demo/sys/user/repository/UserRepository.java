
 /**
 * FileName:     UserRepository.java
 * Createdate:   2019-01-06 17:57:12   
 */

package com.lzc.demo.sys.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lzc.demo.common.entity.User;

/**
 * Description: 用户持久层  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2019-01-06 17:57:12   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2019-01-06   LZC         1.0         1.0 Version  
 */

public interface UserRepository extends JpaRepository<User, String>{

	User findByUsername(String username);
	
	User findByUserId(String userId);
}
