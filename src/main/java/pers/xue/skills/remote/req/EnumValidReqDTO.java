package pers.xue.skills.remote.req;

import pers.xue.skills.config.EnumValid;
import pers.xue.skills.config.EnumValidAOP;
import pers.xue.skills.enums.NameTypeEnum;

import javax.validation.constraints.Max;

/**
 * 枚举校验Req DTO
 */
public class EnumValidReqDTO {

    @EnumValidAOP(NameTypeEnum.class)
    @EnumValid(NameTypeEnum.class)
    private String nameType;

    @Max(value = 3)
    private Integer age;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
