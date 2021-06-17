package pers.xue.skills.remote;

import pers.xue.skills.config.EnumValid;
import pers.xue.skills.config.EnumValidAOP;
import pers.xue.skills.enums.NameTypeEnum;

public class ReqDTO {

    @EnumValidAOP(NameTypeEnum.class)
    @EnumValid(NameTypeEnum.class)
    private String nameType;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
