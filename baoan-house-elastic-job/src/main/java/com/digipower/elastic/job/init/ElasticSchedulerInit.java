package com.digipower.elastic.job.init;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.digipower.elastic.job.annotation.ElasticSchedulerParam;
import com.digipower.elastic.job.listener.MyElasticJobListener;

@Configuration
public class ElasticSchedulerInit {
	// 日志记录
		public static final Logger log = LoggerFactory.getLogger(ElasticSchedulerInit.class);

		@Autowired
		private ZookeeperRegistryCenter regCenter;

		@Autowired
		private ApplicationContext applicationContext;

		@PostConstruct
		public void startSimpleJob() {
			applicationContext.getBeansWithAnnotation(ElasticSchedulerParam.class).forEach((className, obj) -> {
				ElasticSchedulerParam config = obj.getClass().getAnnotation(ElasticSchedulerParam.class);
				String cron = StringUtils.defaultIfBlank(config.cron(), config.value());
				int shardingTotalCount = config.shardingTotalCount();
				String shardingItemParameters = config.shardingItemParameters();
				MyElasticJobListener elasticJobListener = new MyElasticJobListener();
				SimpleJob simpleJob = (SimpleJob) obj;
				new SpringJobScheduler(simpleJob, regCenter,
						getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
						elasticJobListener).init();
			});
		}

		// 私有方法
		private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
				final int shardingTotalCount, final String shardingItemParameters) {
			return LiteJobConfiguration
					.newBuilder(
							new SimpleJobConfiguration(
									JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
											.shardingItemParameters(shardingItemParameters).build(),
									jobClass.getCanonicalName()))
					.overwrite(true).build();
		}
}
