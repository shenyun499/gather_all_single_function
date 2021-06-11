package org.example.datastructure.com.design.prototype;

import util.ArrayList;
import util.List;

public class Test {
	
	public static void main(String[] args) throws CloneNotSupportedException {
		//创建对象并赋值
		Prototype p1 = new Prototype();
		p1.setName("张三");
		p1.setAge(22);
		List<String> list = new ArrayList<>();
		list.add("test");
		p1.setList(list);
		
		//需求：需要一个李四，只有姓名不同     完成：进行克隆
		Prototype p2 = p1.clone();
		p2.setName("李四");
		//重新设置p1的list的值
		list.add("test2");
		p1.setList(list);
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p1 == p2);//false
		System.out.println(p1.getList() == p2.getList());//false，深度克隆，实现引用备份
		
	}

	/**
	 * 浅克隆测试
	 * @throws CloneNotSupportedException
	 */
	private static void test1() throws CloneNotSupportedException {
		//创建对象并赋值
		Prototype p1 = new Prototype();
		p1.setName("张三");
		p1.setAge(22);
		List<String> list = new ArrayList<>();
		list.add("test");
		p1.setList(list);
		
		//需求：需要一个李四，只有姓名不同     完成：进行克隆
		Prototype p2 = p1.clone();
		p2.setName("李四");
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p1 == p2);//false
		System.out.println(p1.getList() == p2.getList());//true，浅克隆，没有实现引用备份
	}

}
