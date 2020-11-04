package com.design.decorator;

/**
 * 具体装饰类，实现了装饰抽象类
 * 添加功能：车会游
 * @author xuexue
 *
 */
public class SwimCarDecorator extends CarDecorator {

	public SwimCarDecorator(Car car) {
		super(car);
	}

	@Override
	public void show() {
		this.getCar().show();
		this.swim();
	}
	
	/**
	 * 装饰后的类多加的功能
	 */
	public void swim() {
		System.out.println("车会游");
	}

}
