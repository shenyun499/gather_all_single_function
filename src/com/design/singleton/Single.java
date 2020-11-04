package com.design.singleton;

/**
 * 单例模式---饿汉式
 * @author xuexue
 *
 */
/*public class Single {
	//1、直接创建一个静态的Single
	private static Single single = new Single();
	
	//2、私有构造方法
	private Single() {}
	
	//3、静态返回单例方法
	public static Single getInstance() {
		return single;
	}
}*/

/**
 * 单例模式---懒汉式，非安全
 * @author xuexue
 *
 */
/*
public class Single {
	//1、先声明一个静态的Single
	private static Single single = null;
	
	//2、私有构造方法
	private Single() {}
	
	//3、静态返回单例方法
	public static Single getInstance() {
		if (single == null) {
			single = new Single();
		}
		return single;
	}
}
*/
/**
 * 单例模式---懒汉式安全--同步方法
 * @author xuexue
 *
 */
/*public class Single {
	//1、先声明一个静态的Single
	private static Single single = null;
	
	//2、私有构造方法
	private Single() {}
	
	//3、静态返回单例方法
	public static synchronized Single getInstance() {
		if (single == null) {
			single = new Single();
		}
		return single;
	}
}*/

/**
 * 单例模式---懒汉式安全--双重检查
 * @author xuexue
 *
 */
public class Single {
	//1、先声明一个静态的Single
	private static volatile Single single = null;
	
	//2、私有构造方法
	private Single() {}
	
	//3、静态返回单例方法
	public static Single getInstance() {
		if (single == null) {
			synchronized (Single.class) {
				if (single == null) {//双重检查
					single = new Single();
				}
			}
		}
		return single;
	}
}
