
 /**
 * FileName:     UserService.java
 * Createdate:   2018-12-19 15:10:36   
 */

package com.lzc.demo.sys.user.service;

import com.lzc.demo.common.entity.User;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 15:10:36   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

public interface UserService {

	User get(String username, String password);
	
	User get(String userId);
}
