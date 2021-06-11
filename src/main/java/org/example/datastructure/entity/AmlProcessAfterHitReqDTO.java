package org.example.datastructure.entity;

/**
 * @Author: Tc
 * @Date: 2020/9/1 10:54
 * @Description:
 * 反洗钱系统人工柜员放行拒绝dto
 **/
public class AmlProcessAfterHitReqDTO {

    private String msgId;

    private String amlResult;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAmlResult() {
        return amlResult;
    }

    public void setAmlResult(String amlResult) {
        this.amlResult = amlResult;
    }

    @Override
    public String toString() {
        return "AmlProcessAfterHitReqDTO{" +
                "msgId='" + msgId + '\'' +
                ", amlResult='" + amlResult + '\'' +
                '}';
    }
}
