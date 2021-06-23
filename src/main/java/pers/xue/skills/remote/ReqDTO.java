package pers.xue.skills.remote;

import pers.xue.skills.config.EnumValid;
import pers.xue.skills.config.EnumValidAOP;
import pers.xue.skills.enums.NameTypeEnum;

import javax.validation.constraints.Max;

public class ReqDTO {

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
}
