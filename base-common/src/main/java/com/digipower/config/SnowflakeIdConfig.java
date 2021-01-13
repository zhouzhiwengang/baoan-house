package com.digipower.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.digipower.common.uuid.SnowflakeIdGenerator;

/**
 * 分布式ID生成器
 * @author zzg
 *
 */
@Configuration
public class SnowflakeIdConfig {
	
	private Long workerId = 0L;
	
	private Long datacenterId = 1L;
	
	/**
     * 分布式Id生成器
     */
    @Bean
    public SnowflakeIdGenerator getSnowflakeIdGenerator() {
    	SnowflakeIdGenerator generator = new SnowflakeIdGenerator(workerId,datacenterId);
        return generator;
    }
}
