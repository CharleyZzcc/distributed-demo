
 /**
 * FileName:     SecKillServiceImpl.java
 * Createdate:   2018-12-19 23:17:07   
 */

package com.lzc.demo.activity.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzc.demo.activity.service.SecKillService;
import com.lzc.demo.common.exception.SaleException;
import com.lzc.demo.common.service.RedisService;

/**
 * Description: 利用redis实现分布式锁的秒杀服务  
 * Copyright:   Copyright (c)2018 
 * @author:     LZC  
 * @version:    1.0  
 * @date:   	2018-12-19 23:17:07   
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2018-12-19   LZC         1.0         1.0 Version  
 */

@Service
public class SecKillServiceImpl implements SecKillService {
	
	@Autowired
	private RedisService redisService;
	
	//超时时间 10s
	private static final int TIMEOUT = 10_000;
	
	private static Map<String, Integer> products;
	private static Map<String, Integer> stock;
	private static Map<String, String> orders;
	
	static {
        /*
         * 模拟多个表，商品表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("p123456", 100000);
        stock.put("p123456", 100000);
    }
	
	private String queryMap(String productId) {
        return "双十一活动，1包纸巾只要0.01元，限量"
                + products.get(productId)
                + " 还剩：" + stock.get(productId) + " 份"
                + " 该商品成功下单用户数目："
                + orders.size() + " 人";
    }

	@Override
	public String queryProduct(String productId) {
		return this.queryMap(productId);
	}

	@Override
	public void doOrder(String productId) {
		/*
		 * redis
		 * 1）1000请求1000并发：耗时2833毫秒，整个测试耗时3001毫秒
		 * 完成请求1000，失败请求992，实测抢购成功的请求10（以实测为准，不同机器不同结果）
		 * 2）2000请求2000并发：耗时874毫秒，整个测试耗时1670毫秒
		 * 完成请求2000，失败请求1989，实测抢购成功的请求11（以实测为准，不同机器不同结果）
		 * 3）3000请求3000并发：耗时1593毫秒，整个测试耗时2323毫秒
		 * 完成请求3000，失败请求2992，实测抢购成功的请求8（以实测为准，不同机器不同结果）
		 * 
		 * 如果直接在浏览器上访问并一直按F5，可以看到没获得锁的请求马上获得异常中的message提示
		 */
		
		//加锁
		long time = System.currentTimeMillis() + TIMEOUT;
		if(!redisService.lock(productId, time)) {
			throw new SaleException(101, "挤爆了！换个姿势再来~");
		}

		//1.查询该商品库存，为0则活动结束。
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SaleException(100, "活动结束");
        }else {
        	//2.下单(模拟不同用户openid不同)
        	orders.put(UUID.randomUUID().toString(), productId);
        	//3.减库存
            stockNum = stockNum - 1;
            try {
            	//模拟查询时间
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }
        
        //解锁
        redisService.unlock(productId, time);
	}

}
