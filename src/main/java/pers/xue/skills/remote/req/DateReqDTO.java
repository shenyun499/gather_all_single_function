package pers.xue.skills.remote.req;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pers.xue.skills.jackson.DateJsonDeserialize;

import java.time.LocalDateTime;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:10 下午
 * @Description
 * 对请求参数时间处理 ReqDTO
 */
public class DateReqDTO {

    @JsonDeserialize(using = DateJsonDeserialize.class)
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime creDateTime;

    public LocalDateTime getCreDateTime() {
        return creDateTime;
    }

    public void setCreDateTime(LocalDateTime creDateTime) {
        this.creDateTime = creDateTime;
    }

    public DateReqDTO() {
    }

    public DateReqDTO(LocalDateTime creDateTime) {
        this.creDateTime = creDateTime;
    }
}
