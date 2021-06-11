package org.example.datastructure.com.design.builder;

/**
 * @Description
 * 创建我的房子
 * @Author xuexue
 * @Date 2019/9/149:48
 */
public class MyHourseBuilder implements HourseBuilder {

    @Override
    public HourseTop createHourseTop() {
        HourseTop hourseTop = new HourseTop();
        hourseTop.setName("房顶组件创建");
        return hourseTop;
    }

    @Override
    public Rour createRour() {
        Rour rour = new Rour();
        rour.setName("四面墙的创建");
        return rour;
    }

    @Override
    public Floor createFloor() {
        Floor floor = new Floor();
        floor.setName("地面的创建");
        return floor;
    }
}
