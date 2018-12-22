
 /**
 * FileName:     SecKillController.java
 * Createdate:   2018-12-19 23:09:20   
 */

package com.lzc.demo.activity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lzc.demo.activity.service.SecKillService;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 23:09:20   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@RestController
@RequestMapping("/kill")
@Slf4j
public class SecKillController {
	
	@Autowired
    private SecKillService secKillService;
	
	/**
	* <p>描述：查询描述商品</p>
	* @param productId
	* @return
	* @author LZC
	* @date   2018-12-20 00:25
	 */
	@GetMapping("/query/{productId}")
	public String query(@PathVariable String productId) {
		return secKillService.queryProduct(productId);
	}
	
	/**
	* <p>描述：秒杀商品</p>
	* @param productId
	* @return
	* @author LZC
	* @date   2018-12-20 00:28
	 */
	@GetMapping("/shopping/{productId}")
	public String shopping(@PathVariable String productId) {
		log.info("秒杀商品，productId：{}", productId);
		secKillService.doOrder(productId);
		return secKillService.queryProduct(productId);
	}

}
