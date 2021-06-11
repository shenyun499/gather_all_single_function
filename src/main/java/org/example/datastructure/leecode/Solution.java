package org.example.datastructure.leecode;

import util.Arrays;
import util.Collection;
import util.HashMap;

/**
 * @Description
 * 计算字符串重复最多次数及字符
 * @Author xuexue
 * @Date 2019/12/12 16:30
 */
public class Solution {
    public static void main(String[] args) {
        String str = "";
        HashMap<Character, Integer> hashMap = maxLengthStrMap(str);
        for (Character character : hashMap.keySet()) {
            System.out.println(character + ":最大次数" + hashMap.get(character));
        }
    }

    /**
     * 计算字符串重复最多次数及字符
     * @param str 字符串
     * @return hashMap 哈希记录最大字符的次数和字符
     */
    public static HashMap<Character,Integer> maxLengthStrMap(String str) {
        if (str.isEmpty() || str == null) {
            throw new RuntimeException("没有输入字符");
        }
        //定义哈希表
        HashMap<Character, Integer> hashMap = new HashMap<>();
        HashMap<Character, Integer> hashMap2 = new HashMap<>();
        //记录最大key
        Character maxChar;
        //将字符串转为字符数组
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            //如果容器中有，则值加1
            if (hashMap.containsKey(aChar)) {
                hashMap.put(aChar,hashMap.get(aChar) + 1);
                continue;
            }
            //初始加入，值为1
            hashMap.put(aChar,1);
        }

        //获取最大的value
        hashMap.values();
        Collection<Integer> c = hashMap.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        int value = (Integer)obj[obj.length - 1];

        //根据最大值获取字符key
        for (Character key : hashMap.keySet()) {
            if (value == hashMap.get(key)) {
                hashMap2.put(key, value);
            }
        }
        return hashMap2;
    }
}
