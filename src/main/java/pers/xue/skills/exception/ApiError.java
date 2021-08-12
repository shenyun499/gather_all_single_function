package pers.xue.skills.exception;

/**
 * @auther huangzhixue
 * @data 2021/8/5 5:25 下午
 * @Description
 */
public class ApiError {
    private Integer code;

    private String responseDesc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }
}
