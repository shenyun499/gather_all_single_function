package org.example.config;

import lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumValidAOP {
    /**
     * enum class
     */
    Class<?> value() default Class.class;
    // 注解类型变量...
}
