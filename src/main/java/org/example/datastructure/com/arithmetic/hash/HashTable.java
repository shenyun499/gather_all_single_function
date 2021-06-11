package org.example.datastructure.com.arithmetic.hash;

import util.Arrays;
import util.Hashtable;

/**
 * @Description 创建一个哈希表
 * 这是采用的是age作为关键字key value：为一个对象
 * 事实上是用对象.hascode()计算返回的int作为哈希值，然后再采取运行
 * @Author xuexue
 * @Date 2019/5/3016:17
 */
public class HashTable {
    /*
     *哈希表长度定义,长度随便给，模仿
     */
    private StudentInfo[] studentInfos = new StudentInfo[16];

    /**
     * 功能：向哈希表中添加学生
     * @param studentInfo 学生信息
     */
    public void put(StudentInfo studentInfo) {
        //取得哈希值
        int hash = studentInfo.hashCode();

        //直接寻址法，年龄作为index
        //studentInfos[hash] = studentInfo;


        //求余法
        //jdk1.8改进，hash = hash & studentInfo.getAge() -1;
        hash = hash % studentInfo.getAge();
        studentInfos[hash] = studentInfo;

    }

    /**
     * 功能：通过age，获得value值
     * @param age
     * @return
     */
    public StudentInfo get(int age) {
        return studentInfos[age];
    }

    @Override
    public String toString() {
        return "HashTable{" +
                "studentInfos=" + Arrays.toString(studentInfos) +
                '}';
    }
}
