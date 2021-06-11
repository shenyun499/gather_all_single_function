package org.example.datastructure.com.design.builder;

/**
 * @Description
 * 装饰整个房子
 * @Author xuexue
 * @Date 2019/9/14 9:56
 */
public class MyHourseDirector implements HourseDirector {
    //定义构造类对象
    private MyHourseBuilder myHourseBuilder = new MyHourseBuilder();

    @Override
    public Hourse director() {
        //创建实际组件
        HourseTop hourseTop = myHourseBuilder.createHourseTop();
        Rour rour = myHourseBuilder.createRour();
        Floor floor = myHourseBuilder.createFloor();

        //有了组件后，构造房子
        Hourse hourse = new Hourse();
        hourse.setHourseTop(hourseTop);
        hourse.setRour(rour);
        hourse.setFloor(floor);

        //房子构造成功，返回
        return hourse;
    }

    @Override
    public void isIn() {
        System.out.println("房子构建成功，可以入住了");
    }
}
