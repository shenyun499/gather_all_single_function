package entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author huxiaofei
 * @date 2019/7/316:09
 */
public class GetPersonAddrReqDTO {

    /** 客户号 */
    @ApiModelProperty(name = "ciNo", value = "客户号")
    private String ciNo;

    /** 地址编号 */
    @ApiModelProperty(name = "addrNo", value = "地址编号")
    private Integer addrNo;


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



    @Override
    public String toString() {
        return "GetPersonAddrReqDTO{" +
                "ciNo='" + ciNo + '\'' +
                ", addrNo=" + addrNo +
                '}';
    }
}
