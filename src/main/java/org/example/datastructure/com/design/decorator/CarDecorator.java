package org.example.datastructure.com.design.decorator;

/**
 * 抽象装饰类
 * @author xuexue
 *
 */
public abstract class CarDecorator implements Car {
	//声明一个被装饰的类
	private Car car;
	
	Car getCar() {
		return car;
	}

	CarDecorator(Car car) {
		this.car = car;
	}
	
	/**
	 * 展示功能
	 */
	public abstract void show();

}
