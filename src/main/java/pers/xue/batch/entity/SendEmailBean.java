package pers.xue.batch.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-06-12
 */
@Builder
@Getter
public class SendEmailBean {
    private LocalDateTime date;
    private String event;
    private String number;
    private Integer count;
    private String status;
}
