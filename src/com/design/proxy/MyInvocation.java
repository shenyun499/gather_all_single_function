package com.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类
 * @author xuexue
 *
 */
public class MyInvocation implements InvocationHandler {
	
	//需要代理的对象
	private Object object;
	
	public Object newProxyInstance(Object object) {
		this.object = object;
		return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces()	, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result;
		System.out.println("卖书之前检查书包完整性");
		result = method.invoke(object, args);
		System.out.println("卖书之后确保售后服务");
		return result;
	}

}
