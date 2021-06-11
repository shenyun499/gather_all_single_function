package org.example.datastructure.thread;

/**
 * 任务
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-04-01
 */
public class TestRunable implements Runnable {
    private String runNm;

    public TestRunable(String runNm) {
        this.runNm = runNm;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
