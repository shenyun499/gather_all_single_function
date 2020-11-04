package com.design.adapter;

/**
 * @Description
 * 适配器---将笔记本和键盘的接口
 * 组合方式--比较灵活
 * @Author xuexue
 * @Date 2019/9/15 22:32
 */
public class Adapter2 implements Target {

    private Adaptee adaptee;

    public Adapter2(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void handle() {
        //通过适配器，可以实现使用键盘的功能
        adaptee.printFont();
    }
}
