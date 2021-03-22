package thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * 线程池的使用
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-20
 */
@Component
public class MsgTaskUtils {

    @Autowired
    @Qualifier("task_threadpool_test")
    private Executor asyncExecutor;

    public void dealTask() {
        // 模拟任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        // 通过线程池方式执行
        asyncExecutor.execute(runnable);
    }
}
