package pers.xue.datasource.remote.req;


import java.time.LocalDateTime;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:10 下午
 * @Description
 * 对请求参数时间处理 ReqDTO
 */
public class CommonReqDTO {

    private Integer id;

    private String content;

    LocalDateTime localDateTime;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
