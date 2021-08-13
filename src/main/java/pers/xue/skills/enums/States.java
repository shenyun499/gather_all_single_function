package pers.xue.skills.enums;

/**
 * @author huangzhixue
 * @date 2021/8/13 2:38 下午
 * @Description
 * 状态机 拥有的状态
 * new、runnable、waiting、timed_wait、blocked、terminated
 */
public enum States {
    /** 新建状态 */
    NEW,

    /** 运行状态 */
    RUNNABLE,

    /** 等待状态 */
    WAITING,

    /** 计时等待 */
    TIMEDWAIT,

    /** 阻塞状态 */
    BLOCKED,

    /** 终止状态 */
    TERMINATED;
}
