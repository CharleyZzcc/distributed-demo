
 /**
 * FileName:     User.java
 * Createdate:   2018-12-24 22:35:49   
 */

package com.lzc.demo.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 简单的用户实体类  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-24 22:35:49   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-24   LZC         1.0         1.0 Version  
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private String userId;
	
	private String username;
	
	private String password;
}
