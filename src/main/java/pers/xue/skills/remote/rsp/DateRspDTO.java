package pers.xue.skills.remote.rsp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xue.skills.jackson.DateJsonSerializier;

import java.time.LocalDateTime;

/**
 * jackjson 响应时间格式处理
 */
public class DateRspDTO {
    @JsonSerialize(using = DateJsonSerializier.class)
    private LocalDateTime creDateTime;

    public LocalDateTime getCreDateTime() {
        return creDateTime;
    }

    public void setCreDateTime(LocalDateTime creDateTime) {
        this.creDateTime = creDateTime;
    }
}
