package pers.xue.skills.remote.req;


import java.time.LocalDateTime;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:10 下午
 * @Description
 * 对请求参数时间处理 ReqDTO
 */
public class UnitTestReqDTO {

    private LocalDateTime content;

    public LocalDateTime getContent() {
        return content;
    }

    public void setContent(LocalDateTime content) {
        this.content = content;
    }
}
