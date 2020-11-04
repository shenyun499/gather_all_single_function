package com.design.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		/*for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
						Single single = Single.getInstance();
						System.out.println(single.toString());
				}
			}.start();
		}*/
		
		HashMap<Integer,Boolean> hashMap = new HashMap<>();
		hashMap.put(88, true);
		hashMap.put(44, true);
		hashMap.put(66, true);
		hashMap.put(55, true);
		hashMap.put(22, true);
		hashMap.put(99, true);
		hashMap.put(77, true);
		System.out.println(hashMap.size());
		System.out.println(hashMap.containsKey(77));
		
		List<Integer> list = new ArrayList<>();
		list.add(33);
		list.add(55);
		list.add(77);
		for (Integer i : list) {
			System.out.println(i);
		}
		System.out.println(Collections.binarySearch(list, 55));

	}
}
