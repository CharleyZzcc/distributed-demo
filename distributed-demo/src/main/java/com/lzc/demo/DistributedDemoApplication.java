package com.lzc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DistributedDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedDemoApplication.class, args);
	}

}

