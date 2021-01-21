package com.digipower.job.instance;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.digipower.elastic.job.annotation.ElasticSchedulerParam;



@ElasticSchedulerParam(cron = "0 */1 * * * ?", shardingTotalCount = 1, shardingItemParameters = "0=Core")
@Component
public class SimpleJobDemo implements SimpleJob {
	private static final Logger log = LoggerFactory.getLogger(SimpleJobDemo.class);


	@Override
	public void execute(ShardingContext shardingContext) {
		// TODO Auto-generated method stub
		System.out.println(new Date() + "SimpleJobDemo");
		try {
			Thread.sleep(10000);
			System.out.println("任务 SimpleJobDemo 开始干活。。。");
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("SimpleJobDemo 定时任务报错：" + e.getMessage());
		}
		System.out.println(new Date() + "SimpleJobDemo 任务结束------------------------------------");
	}
}
