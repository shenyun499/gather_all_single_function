package pers.xue.skills.config;

/**
 * @author huangzhixue
 * @date 2021/8/20 3:27 下午
 * @Description
 */
public enum CaseConstant {

    /** 记录不存在 */
    RECORD_IS_NOT_EXITST("record is not exists", 1000),

    /** 事件转化错误 */
    CONVERT_STATES_ERROR("convert states error,please check the event is right", 1001);

    /** 消息描述 */
    private final String message;
    /** 状态码 */
    private final int code;

    CaseConstant(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
