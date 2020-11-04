package com.design.builder;

/**
 * @Description
 * 这是一个建造者类 房子
 * @Author xuexue
 * @Date 2019/9/149:28
 */
public class Hourse {
    //房顶
    private HourseTop hourseTop;

    //四面墙
    private Rour rour;

    //地面
    private Floor floor;

    public HourseTop getHourseTop() {
        return hourseTop;
    }

    public void setHourseTop(HourseTop hourseTop) {
        this.hourseTop = hourseTop;
        System.out.println(hourseTop.getName());
    }

    public Rour getRour() {
        return rour;
    }

    public void setRour(Rour rour) {
        this.rour = rour;
        System.out.println(rour.getName());
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
        System.out.println(floor.getName());
    }
}

class HourseTop {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Rour {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Floor {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}