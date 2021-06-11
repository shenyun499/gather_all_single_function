package org.example.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {
    /**
     * enum class
     */
    Class<?> value() default Class.class;

    // 注解类型变量，下面几个是必须定义的，因为使用了@Constraint
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
