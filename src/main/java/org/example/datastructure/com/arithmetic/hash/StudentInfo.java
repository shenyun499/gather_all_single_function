package org.example.datastructure.com.arithmetic.hash;

/**
 * @Description
 * @Author xuexue
 * @Date 2019/5/3016:12
 */
public class StudentInfo {
    /*
     *学生姓名
     */
    private String name;

    /*
     * 学生年龄
     */
    private int age;

    public StudentInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public int hashCode() {
        return age;
    }
}
