package com.design.builder;

/**
 * 接口，定义创建组件的规范
 */
public interface HourseBuilder {
    /**
     * 创建房顶
     * @return HourseTop房顶的组件
     */
    HourseTop createHourseTop();

    /**
     * 创建四面墙
     * @return Rour四面墙的组件
     */
    Rour createRour();

    /**
     * 创建地面
     * @return Floor地面的组件
     */
    Floor createFloor();
}
