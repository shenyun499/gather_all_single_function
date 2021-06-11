package org.example.datastructure.com.design.prototype;

import util.ArrayList;
import util.List;

/**
 * 原型对象
 * 实现作用，可以由此对象克隆出相同的对象
 * @author xuexue
 *
 */
public class Prototype implements Cloneable {
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 年龄
	 */
	private int age;
	
	/**
	 * 集合存放字符串
	 */
	private List<String> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	

	@Override
	public String toString() {
		return "Prototype [name=" + name + ", age=" + age + ", list=" + list + "]";
	}

	/* 
	 * 功能：浅克隆
	 * @return 返回克隆对象
	 */
	/*public Prototype clone() throws CloneNotSupportedException {
		return (Prototype)super.clone();
	}*/
	
	/* 
	 * 功能：深度克隆
	 * @return 返回克隆对象
	 */	
	public Prototype clone() throws CloneNotSupportedException {
		//进行浅克隆对象
		Prototype prototype = (Prototype)super.clone();
		
		//实现需要克隆的引用属性List
		List<String> list = new ArrayList<>();
		list.addAll(prototype.getList());
		prototype.setList(list);
		
		return prototype;
	}
}
