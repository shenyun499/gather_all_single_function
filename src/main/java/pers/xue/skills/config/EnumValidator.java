package pers.xue.skills.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {
    private EnumValid enumValid;
    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.enumValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Class<?> value = enumValid.value();
        // 是枚举则进入校验
        if (value.isEnum()) {
            Object[] enumConstants = value.getEnumConstants();
            for (Object object : enumConstants) {
                // 只要有一个匹配，就返回true
                if (object.toString().equals(s)) {
                    return true;
                }
            }
            //todo: 不匹配，可以做点其他，这里暂时打印一下
            System.out.println(s + "是一个错误值");
        }
        return false;
    }
}
