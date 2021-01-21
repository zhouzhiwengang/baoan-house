package com.digipower.elastic.job.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
public class ElasticRegCenterConfig {

	// zookeeper地址信息
	@Value("${zookeeper.serverLists}")
	private String serverList;
	// 命名空间
	@Value("${zookeeper.namespace}")
	private String namespace;

	@Bean(initMethod = "init")
	public ZookeeperRegistryCenter regCenter() {
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverList, namespace);
		return new ZookeeperRegistryCenter(zookeeperConfiguration);
	}

}
