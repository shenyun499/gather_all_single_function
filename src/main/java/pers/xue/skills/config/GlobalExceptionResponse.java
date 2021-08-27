package pers.xue.skills.config;

/**
 * @author huangzhixue
 * @date 2021/8/20 2:20 下午
 * @Description
 * 全局异常响应封装信息
 */
public class GlobalExceptionResponse {
    /** 响应状态 */
    private int status;

    /** 响应信息 */
    private String message;

    /** 响应数据 */
    private Object data;

    public GlobalExceptionResponse(int status, String message, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public GlobalExceptionResponse(int status, String message) {
        this.message = message;
        this.status = status;
    }

    public GlobalExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
