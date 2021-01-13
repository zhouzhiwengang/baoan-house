package com.digipower.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化文件上传线程池配置
 * @author zzg
 *
 */
@Configuration
public class ThreadPoolConfig {
	// 文件上传初始化线程池大小
	@Bean(value = "uploadExecutor")
	public ExecutorService getExecutor() {
		return Executors.newFixedThreadPool(20);
	}

}
