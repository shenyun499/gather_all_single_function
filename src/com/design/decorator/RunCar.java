package com.design.decorator;

/**
 * 需要被装饰的具体实现类
 * @author xuexue
 *
 */
public class RunCar implements Car {

	@Override
	public void run() {
		System.out.println("车会跑");
	}

	@Override
	public void show() {
		this.run();
	}

}
