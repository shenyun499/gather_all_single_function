package org.example.datastructure.scheduled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-05
 */
@Component
public class BatchRecTaskHandler {
    @Value("${fpsMsg.switch.task.recBatchTime:20000L}")
    private Long recBatchTime;
    private Long processingRecBatchInfCurrentInterval = 0L;

    @Scheduled(fixedRate = 1000L)
    public void recBatchRecHandler() {
        // 不生效，直接结束
        if (processingRecBatchInfCurrentInterval < recBatchTime) {
            processingRecBatchInfCurrentInterval += 1000L;
            return;
        }
        processingRecBatchInfCurrentInterval = 0L;

        // 生效执行定时任务逻辑
    }

    @Scheduled(initialDelay = 0L, fixedRateString = "${fpsMsg.switch.task.recBatchTime}")
    public void recBatchRecHandler2() {
        // 定时任务逻辑
    }
}
