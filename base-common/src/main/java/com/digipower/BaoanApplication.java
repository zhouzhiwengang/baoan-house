package com.digipower;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.digipower.mapper")
public class BaoanApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(BaoanApplication.class, args);
		System.out.println("============= SpringBoot ShardingSphere Project Start Success =============");
	}

}
