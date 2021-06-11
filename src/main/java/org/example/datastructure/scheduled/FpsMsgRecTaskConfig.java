package org.example.datastructure.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import text.SimpleDateFormat;
import util.Date;

@Configuration
public class FpsMsgRecTaskConfig extends AbstractTaskConfig{
    private static final Logger logger = LoggerFactory.getLogger(FpsMsgRecTaskConfig.class);

    @Override
    protected void processTask() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info( " 开始任务"+ ",开始时间:" + sd.format(new Date()));
        // TODO: 定时任务的逻辑
        logger.info( " 结束任务" + ",结束时间:" + sd.format(new Date()));
    }

    @Override
    String getCron() {
        return null;
        // 从数据库取合适的定时任务 如取得0 0/1 * * * ?
        // return fileTaskInfoDao.get("x").getTaskTimeCrontab();
    }
}
