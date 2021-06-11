package org.example.datastructure.com.design.proxy;

/**
 * 代理类
 * @author xuexue
 *
 */
public class ProxySubject implements Subject {
	
	private Subject subject;
	
	public ProxySubject(Subject subject) {
		this.subject = subject;
	}

	/* 
	 * 代理RealSubject类
	 * 增加功能，检查和售后
	 */
	@Override
	public void sail() {
		System.out.println("卖书之前检查书包完整性");
		subject.sail();
		System.out.println("卖书之后确保售后服务");
	}

}
