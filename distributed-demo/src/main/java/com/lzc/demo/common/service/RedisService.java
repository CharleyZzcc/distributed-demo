
 /**
 * FileName:     RedisService.java
 * Createdate:   2018-12-19 15:29:31   
 */

package com.lzc.demo.common.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lzc.demo.common.constant.RedisConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:   
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 15:29:31   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         新增addToken、removeToken、getToken  
 * 2018-12-20   LZC         1.1         提供分布式锁：lock、unlock  
 * 2018-12-24   LZC         1.2         新增addLoginUser、removeLoginUser、getLoginUser；重构token方法  
 */

@Component
@Slf4j
public class RedisService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	/**
	* <p>描述：添加token</p>
	* @param userId
	* @return token
	* @author LZC
	* @date   2018-12-19 15:50
	 */
	public String addToken(String userId) {
		if(StringUtils.isEmpty(userId)) {
			return null;
		}
		String key = RedisConstant.TOKEN + RedisConstant.SEPARATOR + userId;
		String token = UUID.randomUUID().toString();
		stringRedisTemplate.opsForValue().set(key, token, RedisConstant.TIME_OUT_MINUTE, TimeUnit.MINUTES);
		
		return token;
	}
	
	/**
	* <p>描述：添加登录用户</p>
	* @param token
	* @param userId
	* @author LZC
	* @date   2018-12-24 22:14
	 */
	public void addLoginUser(String token, String userId) {
		if(StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {
			return;
		}
		String key = RedisConstant.LOGIN_USER + RedisConstant.SEPARATOR + token;
		stringRedisTemplate.opsForValue().set(key, userId, RedisConstant.TIME_OUT_MINUTE, TimeUnit.MINUTES);
	}
	
	/**
	* <p>描述：移除token</p>
	* @param userId
	* @author LZC
	* @date   2018-12-19 16:26
	 */
	public void removeToken(String userId) {
		if(StringUtils.isEmpty(userId)) {
			return;
		}
		String key = RedisConstant.TOKEN + RedisConstant.SEPARATOR + userId;
		stringRedisTemplate.opsForValue().getOperations().delete(key);
	}
	
	/**
	* <p>描述：移除登录用户</p>
	* @param token
	* @return oldLoginUser
	* @author LZC
	* @date   2018-12-24 22:14
	 */
	public String removeLoginUser(String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		String key = RedisConstant.LOGIN_USER + RedisConstant.SEPARATOR + token;
		String oldValue = getLoginUser(token);
		stringRedisTemplate.opsForValue().getOperations().delete(key);
		return oldValue;
	}
	
	/**
	* <p>描述：获取token</p>
	* @param userId
	* @return token
	* @author LZC
	* @date   2018-12-19 16:40
	 */
	public String getToken(String userId) {
		if(StringUtils.isEmpty(userId)) {
			return null;
		}
		String key = RedisConstant.TOKEN + RedisConstant.SEPARATOR + userId;
		return stringRedisTemplate.opsForValue().get(key);
	}
	
	/**
	* <p>描述：获取当前登录用户的用户名</p>
	* @param token
	* @return userId
	* @author LZC
	* @date   2018-12-24 22:15
	 */
	public String getLoginUser(String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		String key = RedisConstant.LOGIN_USER + RedisConstant.SEPARATOR + token;
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	* <p>描述：加锁</p>
	* @param key
	* @param timeValue
	* @return
	* @author LZC
	* @date   2018-12-20 00:17
	 */
	public boolean lock(String key, long timeValue) {
		String value = String.valueOf(timeValue);
		
		//如果不存在，则添加，并表示获得锁
		if(stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
			return true;
		}
		
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		//是否过期
		if(!StringUtils.isEmpty(currentValue) 
				&& Long.valueOf(currentValue) < System.currentTimeMillis()) {
			String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
			//旧值与当前值一致，仍获得锁
			if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	* <p>描述：解锁</p>
	* @param key
	* @param timeValue
	* @author LZC
	* @date   2018-12-20 00:22
	 */
	public void unlock(String key, long timeValue) {
		try {
			String currentValue = stringRedisTemplate.opsForValue().get(key);
			//当前值和value一致，说明锁未过期，为当前访问者所有，解锁
			if(!StringUtils.isEmpty(currentValue) && currentValue.equals(String.valueOf(timeValue))) {
				stringRedisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			log.error("【redis分布式锁】解锁异常，{}", e);
		}
	}
}
