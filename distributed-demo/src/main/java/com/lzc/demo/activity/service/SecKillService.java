
 /**
 * FileName:     SecKillService.java
 * Createdate:   2018-12-19 23:12:43   
 */

package com.lzc.demo.activity.service;

/**
 * Description: 秒杀服务  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 23:12:43   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

public interface SecKillService {

	/**
	* <p>描述：查询商品</p>
	* @param productId
	* @return
	* @author LZC
	* @date   2018-12-19 23:15
	 */
	String queryProduct(String productId);
	
	/**
	* <p>描述：下单</p>
	* @param productId
	* @return
	* @author LZC
	* @date   2018-12-19 23:16
	 */
	void doOrder(String productId);
	
}
