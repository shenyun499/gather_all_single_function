package org.example.datastructure.com.design.proxy;

public class Test {
	public static void main(String[] args) {
		//创建需要被代理的对象
		RealSubject realSubject = new RealSubject();
		
		//创建代理对象
		MyInvocation proxyHandler = new MyInvocation();
		//反射创建代理类
		Subject proxyInstance = (Subject) proxyHandler.newProxyInstance(realSubject);
		proxyInstance.sail();
		
	}
	private static void test1() {
		//创建需要被代理的对象
		Subject realSubject = new RealSubject();
		//卖书
		realSubject.sail();
		System.out.println("--------------------------------------");
		
		//创建代理对象，传入被代理对象realSubject
		ProxySubject proxySubject = new ProxySubject(realSubject);
		//代理之后卖书
		proxySubject.sail();
	}

}
