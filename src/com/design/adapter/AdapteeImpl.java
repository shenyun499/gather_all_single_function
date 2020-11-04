package com.design.adapter;

/**
 * @Description
 * 被适配对象实现类--相当于键盘的接口
 * @Author xuexue
 * @Date 2019/9/15 22:28
 */
public class AdapteeImpl implements Adaptee {

    @Override
    public void printFont() {
        System.out.println("键盘打字");
    }
}
