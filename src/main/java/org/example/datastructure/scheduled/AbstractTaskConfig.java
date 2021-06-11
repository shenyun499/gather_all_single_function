package org.example.datastructure.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public abstract class AbstractTaskConfig implements SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(AbstractTaskConfig.class);
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(//执行定时任务
                () -> {
                    processTask();
                }, //设置触发器
                triggerContext -> {
                    // 初始化定时任务周期
                    CronTrigger trigger = new CronTrigger(getCron());
                    //CronTrigger trigger = new CronTrigger("* * 1 * * ?");
                    return trigger.nextExecutionTime(triggerContext);
                });
    }
    /**
     * @brief 任务的处理函数
     * 本函数需要由派生类根据业务逻辑来实现
     */
    protected abstract void processTask();


    /**
     * @return String
     * @brief 获取定时任务周期表达式
     * 本函数由派生类实现，从配置文件，数据库等方式获取参数值
     */
     abstract String getCron();
}