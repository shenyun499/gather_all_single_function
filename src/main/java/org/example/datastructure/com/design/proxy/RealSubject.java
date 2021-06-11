package org.example.datastructure.com.design.proxy;

/**
 * 需要被代理的实现类
 * @author xuexue
 *
 */
public class RealSubject implements Subject {

	@Override
	public void sail() {
		System.out.println("卖书");
	}

}
