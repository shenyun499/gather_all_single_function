package org.example.datastructure.com.design.prototype;

import util.HashSet;

public class Test2 {
	public static void main(String[] args) {
		//创建两个Key对象，性质相同
		Key key1 = new Key("张三22");
		Key key2 = new Key("张三2");
		HashSet<Key> hashSet = new HashSet<>();
		boolean isA = hashSet.add(key1);
		boolean isA2 = hashSet.add(key2);
		System.out.println(isA + " " + isA2);
		for (Key key : hashSet) {
			System.out.println(key);
		}
		
	}

}
