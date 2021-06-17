package pers.xue.skills.remote;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xue.skills.jackson.DateJsonSerializier;

import java.time.LocalDateTime;

public class RspDTO {
    @JsonSerialize(using = DateJsonSerializier.class)
    private LocalDateTime localDateTime;

    public RspDTO() {
    }

    public RspDTO(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
