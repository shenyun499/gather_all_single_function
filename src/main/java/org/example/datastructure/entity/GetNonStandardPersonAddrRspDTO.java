package org.example.datastructure.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author huangzhixue
 * @date 2020/9/4
 */
public class GetNonStandardPersonAddrRspDTO {

    /** 客户号 */
    @ApiModelProperty(name = "ciNo", value = "客户号")
    private String ciNo;

    /** 地址编号 */
    @ApiModelProperty(name = "addrNo", value = "地址编号")
    private Integer addrNo;

    /** 地址类型 */
    @ApiModelProperty(name = "type", value = "地址类型")
    private String type;

    /** 英文地址标志 */
    @ApiModelProperty(name = "engFlg", value = "英文地址标志")
    private String engFlg;

    /** 邮寄国家 */
    @ApiModelProperty(name = "postCty", value = "邮寄国家")
    private String postCty;

    /** 地区邮政编码 */
    @ApiModelProperty(name = "postArea", value = "地区邮政编码")
    private String postArea;

    /** 客户地址默认标识 */
    @ApiModelProperty(name = "defaultFlag", value = "客户地址默认标识")
    private String defaultFlag;

    /** 英文地址非标准 */
    @ApiModelProperty(name = "engAddr", value = "英文地址非标准")
    private String engAddr;

    /** 本地语言地址非标准 */
    @ApiModelProperty(name = "localAddr", value = "本地语言地址非标准")
    private String localAddr;

    public String getCiNo() {
        return ciNo;
    }

    public void setCiNo(String ciNo) {
        this.ciNo = ciNo;
    }

    public Integer getAddrNo() {
        return addrNo;
    }

    public void setAddrNo(Integer addrNo) {
        this.addrNo = addrNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEngFlg() {
        return engFlg;
    }

    public void setEngFlg(String engFlg) {
        this.engFlg = engFlg;
    }

    public String getPostCty() {
        return postCty;
    }

    public void setPostCty(String postCty) {
        this.postCty = postCty;
    }

    public String getPostArea() {
        return postArea;
    }

    public void setPostArea(String postArea) {
        this.postArea = postArea;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getEngAddr() {
        return engAddr;
    }

    public void setEngAddr(String engAddr) {
        this.engAddr = engAddr;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    @Override
    public String toString() {
        return "GetNonStandardPersonAddrRspDTO{" +
                "ciNo='" + ciNo + '\'' +
                ", addrNo=" + addrNo +
                ", type='" + type + '\'' +
                ", engFlg='" + engFlg + '\'' +
                ", postCty='" + postCty + '\'' +
                ", postArea='" + postArea + '\'' +
                ", defaultFlag='" + defaultFlag + '\'' +
                ", engAddr='" + engAddr + '\'' +
                ", localAddr='" + localAddr + '\'' +
                '}';
    }
}
