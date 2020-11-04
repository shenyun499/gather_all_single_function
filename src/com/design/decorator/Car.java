package com.design.decorator;

/**
 * 需要装饰的接口
 * @author xuexue
 *
 */
public interface Car {
	/**
	 * 会跑
	 */
	default void run() {}


	/**
	 * 展示功能
	 */
	default void show() {}

}
