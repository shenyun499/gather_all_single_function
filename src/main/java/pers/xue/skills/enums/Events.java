package pers.xue.skills.enums;

/**
 * @author huangzhixue
 * @date 2021/8/13 2:52 下午
 * @Description
 * 促使状态发生变化的事件
 */
public enum Events {
    /** start 启动线程 */
    START,

    /** wait无参方法 */
    WAIT_NO_PARAM,

    /** wait有参方法 */
    WAIT_PARAM,

    /** 通知唤醒 */
    NOTIFY,

    /** run方法结束 */
    RUN_END;

}
