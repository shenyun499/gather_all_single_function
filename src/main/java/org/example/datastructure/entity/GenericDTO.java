package org.example.datastructure.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import io.Serializable;
import time.LocalDate;
import time.LocalDateTime;
import util.Locale;

/**
 * 请求通用头
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-09-02
 */
@ApiModel(value = "GenericDTO", description = "通用数据传输对象")
public class GenericDTO<T> implements Serializable {
    /**
     * 请求流水号
     */
    @ApiModelProperty(hidden = true)
    private String requestId;
    /**
     * 交易流水号
     */
    @ApiModelProperty(hidden = true)
    private String msgId;
    /**
     * 调用链唯一标识
     */
    @ApiModelProperty(hidden = true)
    private String traceId;

    /** 调用链Span唯一标识 */
    @ApiModelProperty(hidden = true)
    private String spanId;

    /** 当前Span的父ID */
    @ApiModelProperty(hidden = true)
    private String parentSpanId;
    /**
     * 记账日期
     */
    @ApiModelProperty(hidden = true)
    private LocalDate accDate;
    /**
     * 交易发起时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime startDateTime;

    /**
     * 交易结束时间
     */
    @ApiModelProperty(value = "交易结束时间", hidden = true)
    private LocalDateTime endDateTime;
    /**
     * 区域
     */
    @ApiModelProperty(hidden = true)
    private Locale locale;
    /**
     * 路径信息，用|隔开
     */
    @ApiModelProperty(hidden = true)
    private String routeInfo;
    /**
     * 登录用户ID
     */
    @ApiModelProperty(hidden = true)
    private String userId;


    @ApiModelProperty(hidden = true)
    private String clientIp;


    @ApiModelProperty(hidden = true)
    private String source;


    @ApiModelProperty(hidden = true)
    private String channel;


    @ApiModelProperty(hidden = true)
    private String business;


    @ApiModelProperty(hidden = true)
    private String uri;


    @ApiModelProperty(hidden = true)
    private String token;


    @ApiModelProperty(hidden = true)
    private String loginName;

    /**
     * 交易核算单元
     */
    @ApiModelProperty(hidden = true)
    private Long trBr;

    /**
     *  顺序号 - 用于表示当前交易在整个服务调用链中的顺序
     */
    @ApiModelProperty(hidden = true)
    private Integer seq;

    @Valid
    @ApiModelProperty(value = "业务传输对象; 非嵌套业务传输对象，忽略该属性")
    private T body;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public LocalDate getAccDate() {
        return accDate;
    }

    public void setAccDate(LocalDate accDate) {
        this.accDate = accDate;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getTrBr() {
        return trBr;
    }

    public void setTrBr(Long trBr) {
        this.trBr = trBr;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "GenericDTO{" +
                "requestId='" + requestId + '\'' +
                ", msgId='" + msgId + '\'' +
                ", traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                ", parentSpanId='" + parentSpanId + '\'' +
                ", accDate=" + accDate +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", locale=" + locale +
                ", routeInfo='" + routeInfo + '\'' +
                ", userId='" + userId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", source='" + source + '\'' +
                ", channel='" + channel + '\'' +
                ", business='" + business + '\'' +
                ", uri='" + uri + '\'' +
                ", org.example.datastructure.token='" + token + '\'' +
                ", loginName='" + loginName + '\'' +
                ", trBr=" + trBr +
                ", seq=" + seq +
                ", body=" + body +
                '}';
    }
}
