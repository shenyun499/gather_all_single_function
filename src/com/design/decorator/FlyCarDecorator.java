package com.design.decorator;

/**
 * 具体装饰类，实现了装饰抽象类
 * 添加功能：车会飞
 * @author xuexue
 *
 */
public class FlyCarDecorator extends CarDecorator {

	public FlyCarDecorator(Car car) {
		super(car);
	}

	@Override
	public void show() {
		this.getCar().show();
		this.fly();
	}
	
	/**
	 * 装饰后的类多加的功能
	 */
	public void fly() {
		System.out.println("车会飞");
	}
}
