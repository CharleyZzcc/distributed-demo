
 /**
 * FileName:     SecKillServiceImpl.java
 * Createdate:   2018-12-19 23:17:07   
 */

package com.lzc.demo.activity.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.lzc.demo.activity.service.SecKillService;
import com.lzc.demo.common.exception.SaleException;

/**
 * Description: 利用Lock实现的秒杀服务（2）  
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
//要用时再去掉注释，同时其它三个注解要注释掉
//@Service
public class SecKillServiceImpl4 implements SecKillService {
	
	private static Lock lock = new ReentrantLock();
	
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
		 * lock.tryLock(long time, TimeUnit unit)
		 * 1）1000请求1000并发：耗时10119毫秒，整个测试耗时10325毫秒
		 * 完成请求1000，失败请求897（未获得锁的），实测抢购成功的请求103（以实测为准，不同机器不同结果）
		 * 2）2000请求2000并发：耗时19434毫秒，整个测试耗时20722毫秒
		 * 完成请求2000，失败请求1976（未获得锁的），但加了等待锁的时间，实际上抢购成功的请求200左右（以实测为准，不同机器不同结果）
		 * 
		 * 如果直接在浏览器上访问并一直按F5，可以看到没获得锁的请求在等待响应，等待时间长短根据设置得来
		 */
		
		//加锁
		try {
			//加锁，如果没获得锁，将等待指定时间，等待时间一过，放弃获得锁
			if(lock.tryLock(2L, TimeUnit.SECONDS)) {
				try {
					//1.查询该商品库存，为0则活动结束。
					int stockNum = stock.get(productId);
					if (stockNum == 0) {
						throw new SaleException(100, "活动结束");
					} else {
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
				} finally {
					//解锁
					lock.unlock();
				}
			}else {
				throw new SaleException(101, "挤爆了！换个姿势再来~");
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
