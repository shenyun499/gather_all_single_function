package com.design.builder;

/**
 * 装饰类的规范接口
 */
public interface HourseDirector {
    /**
     * 进行组件的装饰
     */
    Hourse  director();

    /**
     * 是否可以入住
     */
    default void isIn() {}
}
