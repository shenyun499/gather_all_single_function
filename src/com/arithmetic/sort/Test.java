package com.arithmetic.sort;

import com.arithmetic.hash.HashTable;
import com.arithmetic.hash.StudentInfo;

/**
 * @Description
 * 测试
 * @Author xuexue
 * @Date 2019/5/3016:53
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.arch"));

    }

    public void test() {
        StudentInfo studentInfo1 = new StudentInfo("zhangsan",10);
        StudentInfo studentInfo2 = new StudentInfo("lisi",20);
        StudentInfo studentInfo3 = new StudentInfo("wangwu",5);
        StudentInfo studentInfo4 = new StudentInfo("zhaoliu",40);

        HashTable hashTable = new HashTable();
        hashTable.put(studentInfo1);
        hashTable.put(studentInfo2);
        hashTable.put(studentInfo3);
        hashTable.put(studentInfo4);

        System.out.println(hashTable);
    }
}
