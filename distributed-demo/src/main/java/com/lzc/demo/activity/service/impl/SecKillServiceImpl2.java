
 /**
 * FileName:     SecKillServiceImpl.java
 * Createdate:   2018-12-19 23:17:07   
 */

package com.lzc.demo.activity.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lzc.demo.activity.service.SecKillService;
import com.lzc.demo.common.exception.SaleException;

/**
 * Description: 利用synchronized关键字的秒杀服务（慢得不能叫秒杀了，尤其是越后面得到锁的）  
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
public class SecKillServiceImpl2 implements SecKillService {
	
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
	public synchronized void doOrder(String productId) {
		/*
		 * synchronized
		 * 1000请求1000并发：耗时98.369秒，整个测试耗时100.101秒
		 * 完成请求1000，失败请求991（以实测为准，不同机器不同结果），实测抢购成功的请求1000
		 * 
		 * 由于没有获得锁的请求会等待直到获得锁，所以很慢
		 * 如果直接在浏览器上访问并一直按F5，可以看到没获得锁的请求在等待响应
		 */

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
	}

}
