package com.design.adapter;

/**
 * @Description
 * 相当于笔记本
 * @Author xuexue
 * @Date 2019/9/1522:30
 */
public class Client {

    public void test(Target t) {
        t.handle();
    }

    public static void main(String[] args) {
        //通过组合方式实现
        //创建被适配对象--键盘
        AdapteeImpl adaptee = new AdapteeImpl();

        //创建适配器
        Adapter2 adapter = new Adapter2(adaptee);

        //创建笔记本客户端
        Client client = new Client();

        //通过适配器，将笔记本电脑和键盘适配
        client.test(adapter);
    }

    public void test1() {
        //通过继承方式实现
        //创建被适配对象--键盘
        AdapteeImpl adaptee = new AdapteeImpl();

        //创建适配器
        Adapter adapter = new Adapter();

        //创建笔记本客户端
        Client client = new Client();

        //通过适配器，将笔记本电脑和键盘适配
        client.test(adapter);
    }

}
