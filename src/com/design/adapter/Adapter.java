package com.design.adapter;

/**
 * @Description
 * 适配器---将笔记本和键盘的接口
 * 继承方式--类的方式实现
 * @Author xuexue
 * @Date 2019/9/15 22:32
 */
public class Adapter extends AdapteeImpl implements Target {

    @Override
    public void handle() {
        //通过适配器，可以实现使用键盘的功能
        super.printFont();
    }
}
