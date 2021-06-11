package org.example.remote;

import org.example.config.EnumValid;
import org.example.config.EnumValidAOP;
import org.example.enums.NameTypeEnum;

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
