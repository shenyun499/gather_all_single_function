package org.example.datastructure.stream;

import org.example.datastructure.entity.TestC;
import org.example.datastructure.entity.User;
import org.junit.Test;
import org.example.datastructure.tools.JudgeUtils;

import util.*;
import util.stream.Collectors;
import util.stream.IntStream;

/**
 * stream流使用
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-08-14
 */
public class StreamGrammer {

    //https://blog.csdn.net/shine_guo_star/article/details/94383319 stream操作
    @Test
    public void test1() {
        //map(对象map操作)、collect(集合操作，转为集合最常用)、forEach(打印操作)使用
        //1、使用.map得到对象的一个属性集合,然后使用.collect返回一个集合列表或者用.forEach进行打印输出
        //只能集合、数组.才可以stream流化
        List<TestC> list = Arrays.asList(new TestC("abc"), new TestC("abd"), new TestC("abe"));
        List<String> nameList = list.stream()
                .map(TestC::getName)
                .collect(Collectors.toList());
        list.stream().map(t -> t.getName()).forEach(System.out::println);
    }

    @Test
    public void test2() {
        //peek(中间操作)、filter(过滤)、ditinct(去重)使用
        List<TestC> list = Arrays.asList(new TestC("abc"), new TestC("bbd"), new TestC("abe"), new TestC("abe"));
        //将name为abc的改成abf(影响整个集合list的值)，打印
        List<String> peList = list.stream()
                .peek(t -> t.setName("abc".equals(t.getName()) ? "abf" : t.getName()))
                .map(t -> t.getName())
                .collect(Collectors.toList());
        peList.stream().forEach(st -> System.out.print(st + ","));
        for (TestC testC : list) {
            System.out.println(testC.getName());
        }

        //过滤出name为abf的值，如果有多个属性，则可以扩大规则
        System.out.println("filter使用");
        List<String> fiList = list.stream()
                .filter(t -> JudgeUtils.isNotEmpty(t) && "abf".equals(t.getName()))
                .map(t -> t.getName())
                .collect(Collectors.toList());

        //对name相同的disctint去重
        System.out.println("disctinct使用");
        list.stream()
                .map(t -> t.getName())
                .distinct()
                .forEach(System.out::println);

        //混合使用
        System.out.println("三者混合使用");
        list.stream()
                //将name为abc的改成abf
                .peek(t -> t.setName("abc".equals(t.getName()) ? "abf" : t.getName()))
                //过滤出name第一个字符为a的对象
                .filter(t -> t.getName().substring(0, 1).equals("a"))
                //取name属性集合
                .map(t -> t.getName())
                //去重
                .distinct()
                //打印
                .forEach(System.out::println);


    }

    @Test
    public void test3() {
        Random random = new Random(100);
//        limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据
        random.ints().limit(10).forEach(System.out::println);
//        sorted(): 用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序
        random.ints().limit(10).sorted().forEach(System.out::println);
//        filter():filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("空字符数量：" + count);

        //排序
        int[] a = new int[]{1, 3, 5, 2, 9, 4};
        int[] array = Arrays.stream(a).sorted().toArray();
        for (Object it : array) {
            System.out.println(it);
        }
    }

    @Test
    public void test4() {
        //高级排序
        List<User> list = new ArrayList<>();
        list.add(new User("张三", "50"));
        list.add(new User("李四", "40"));
        list.add(new User("王五", null));
        list.add(new User("张六", "20"));

        //按名字排序，从低到高
        List<User> userList = list.stream()
                .filter(u -> JudgeUtils.isNotEmpty(u.getPassword()))
                .sorted(Comparator.comparing(u -> u.getPassword()))
                .collect(Collectors.toList());
        userList.stream().forEach(u -> System.out.println("用户名：" + u.getUsername() + "，密码:" + u.getPassword()));
        //list.org.example.datastructure.stream().forEach(u -> System.out.println("用户名：" + u.getUsername() + "，密码:" + u.getPassword()));

        List<User> userList2 = list.stream()
                .filter(u -> JudgeUtils.isNotEmpty(u.getPassword()))
                .sorted(Comparator.comparing(User::getPassword).reversed())
                .collect(Collectors.toList());

        /*list.org.example.datastructure.stream()
                .filter(u -> JudgeUtils.isNotEmpty(u.getPassword()))
                .sorted(Comparator.comparing(u -> u.getPassword()).reversed())
                .collect(Collectors.toList());*/

        userList2.stream().forEach(u -> System.out.println("用户名：" + u.getUsername() + "，密码:" + u.getPassword()));
    }


    @Test
    public void test5() {
        //peek中间操作，设值；map集合化，filer过滤操作
        List<User> list = new ArrayList<>();
        list.add(new User("张三", "1322"));
        list.add(new User("李四", "1323"));
        list.add(new User("王五", null));
        list.add(new User("张六", "1324"));

        //list.org.example.datastructure.stream().map(u -> u.getUsername()).forEach(System.out::println);
        //如果密码为null，则改成123456默认值；然后取所有密码进行打印
        list.stream().peek(u -> u.setPassword(JudgeUtils.isEmpty(u.getPassword()) ? "123456" : u.getPassword()))
                .map(u -> u.getPassword())
                .forEach(u -> System.out.print(u + "，"));

        //如果密码为null，则改成123456默认值；然后过滤出姓名以王开头的对象，取所有密码进行打印
        list.stream().peek(u -> u.setPassword(JudgeUtils.isEmpty(u.getPassword()) ? "123456" : u.getPassword()))
                .filter(u -> u.getUsername() != null && u.getUsername().substring(0, 1).equals("王"))
                .map(u -> u.getPassword())
                .forEach(System.out::println);

        //List<String> list1 = Optional.ofNullable(list).get().org.example.datastructure.stream().map(org.example.datastructure.entity.User::getUsername).collect(Collectors.toList());
        //list1.org.example.datastructure.stream().peek()

        /*org.example.datastructure.entity.User user = new org.example.datastructure.entity.User("张三", "123");
        String password = Optional.ofNullable(user).map(u -> u.getPassword()).orElse("123456");
        System.out.println(password);*/
        List<TestC> list2 = Arrays.asList(new TestC("abc"), new TestC("abd"), new TestC("abe"));
        //先过滤，然后求姓名的集合，然后输出
        list2.stream()
                .filter(testc -> testc.getName().equals("abc"))
                .map(TestC::getName)
                .forEach(System.out::println);
    }

}

