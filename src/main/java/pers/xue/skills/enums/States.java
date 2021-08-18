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
    NEW("new"),

    /** 运行状态 */
    RUNNABLE("runnable"),

    /** 等待状态 */
    WAITING("waiting"),

    /** 计时等待 */
    TIMED_WAITING("timed_waiting"),

    /** 阻塞状态 */
    BLOCKED("blocked"),

    /** 终止状态 */
    TERMINATED("terminated");

    private String state;
    States(String state) {
        this.state = state;
    }
}
