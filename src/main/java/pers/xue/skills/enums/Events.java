package pers.xue.skills.enums;

/**
 * @author huangzhixue
 * @date 2021/8/13 2:52 下午
 * @Description
 * 促使状态发生变化的事件
 */
public enum Events {
    /** start 启动线程 */
    START("start"),

    /** wait无参方法 */
    WAIT_NO_PARAM("wait_no_param"),

    /** wait有参方法 */
    WAIT_PARAM("wait_param"),

    /** 进入sync同步方法/代码 */
    ENTER_SYNC("enter_sync"),

    /** 通知唤醒 */
    NOTIFY("notify"),

    /** run方法结束 */
    RUN_END("run_end");

    private String event;
    Events(String event) {this.event = event;}
}
