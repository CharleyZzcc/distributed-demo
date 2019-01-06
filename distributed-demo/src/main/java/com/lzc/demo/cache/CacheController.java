
 /**
 * FileName:     CacheController.java
 * Createdate:   2019-01-06 19:27:51   
 */

package com.lzc.demo.cache;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 基于redis的缓存测试用例
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2019-01-06 19:27:51   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2019-01-06   LZC         1.0         1.0 Version  
 */

@RestController
@RequestMapping("/cache")
public class CacheController {

	/**
	* <p>描述：第一次访问后将结果缓存到redis，第二次访问直接获取redis的缓存数据</p>
	* @return
	* @author LZC
	* @date   2019-01-06 19:42
	 */
	@GetMapping("/get")
	@Cacheable(cacheNames="cache_test",key="123")
	public String get() {
		return getMsg();
	}
	
	/**
	* <p>描述：更新缓存，返回结果要和原有缓存一致</p>
	* @return
	* @author LZC
	* @date   2019-01-06 19:42
	 */
	@GetMapping("/update")
	@CachePut(cacheNames="cache_test",key="123")
	public String update() {
		return getMsg();
	}
	
	/**
	* <p>描述：清除缓存</p>
	* @return
	* @author LZC
	* @date   2019-01-06 19:42
	 */
	@GetMapping("/clear")
	@CacheEvict(cacheNames="cache_test",key="123")
	public String clear() {
		return "成功清除缓存";
	}
	
	/**
	* <p>描述：简单地输出字符串</p>
	* @return
	* @author LZC
	* @date   2019-01-06 19:43
	 */
	public String getMsg() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String msg = format.format(new Date()) + "：" + "缓存测试用例";
		System.out.println(msg);
		return msg;
	}
}
