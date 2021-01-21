package com.digipower.elastic.job.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 * 自定义ElasticJob 定时器
 * @author zzg
 *
 */
public class MyElasticJobListener implements ElasticJobListener {
	private static final Logger logger = LoggerFactory.getLogger(MyElasticJobListener.class);

	/**
	 * 长日期格式
	 */
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private long beginTime = 0;

	@Override
	public void beforeJobExecuted(ShardingContexts shardingContexts) {
		beginTime = System.currentTimeMillis();

		logger.info("===>{} JOB BEGIN TIME: {} <===", shardingContexts.getJobName(), beginTime);
	}

	@Override
	public void afterJobExecuted(ShardingContexts shardingContexts) {
		long endTime = System.currentTimeMillis();
		logger.info("===>{} JOB END TIME: {},TOTAL CAST: {} <===", shardingContexts.getJobName(), endTime,
				endTime - beginTime);
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 *
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convert2String(long time, String format) {
		if (time > 0l) {
			if (StringUtils.isBlank(format))
				format = TIME_FORMAT;

			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);

			return sf.format(date);
		}
		return "";
	}

}
