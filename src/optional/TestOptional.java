package optional;

import entity.User;
import org.junit.Test;
import tools.JudgeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Optional类学习
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-21
 */
public class TestOptional {
    @Test
    public void test1() {
        //Optional中的ofNullable与of
        String str = null;
        Optional<String> str1 = Optional.ofNullable(str);
        System.out.println(str1);//输出Optional.empty
        //Optional<String> str1 = Optional.of(str);//直接报NullPointerException
    }

    @Test
    public void test2() {
        //ifPresent方法，判断Optional是否返回有值，有则执行lambda表达式，否则不执行
        String str = null;
        //此时返回str1肯定没有值，如调用str1.get()方法就会报错：NoSuchElementException
        Optional<String> str1 = Optional.ofNullable(str);
        str1.ifPresent(System.out::print);//当str1不为空时，才会执行里面的lambda表达式
    }

    @Test
    public void test3() {
        //isPresent方法，判断返回有没有值，从而安全使用get方法
        String str = null;
        //此时返回str1肯定没有值，如调用str1.get()方法就会报错：NoSuchElementException
        Optional<String> str1 = Optional.ofNullable(str);
        System.out.println(str1.get());
        if (str1.isPresent()) {
            System.out.println(str1.get());
        } else {
            System.out.println("无值");
        }
    }

    @Test
    public void test4() {
        //orElse 有值返回条件中的值，否则返回orElse参数值；orElseGet这里不讲了
        String str = null;
        //此时返回str1肯定没有值，如调用str1.get()方法就会报错：NoSuchElementException
        String opt = Optional.ofNullable(str).orElse("无值");
        System.out.println(opt);//无值就返回字符串”无值“，有值则返回str的值
    }

    @Test
    public void test9() {
        // 如果没值则抛出异常，否则正常返回，一般jpa 可以用到 生产者函数式
        Optional.ofNullable("the value is exist").orElseThrow(() -> new RuntimeException("xx"));
    }

    @Test
    public void test5() {
        //get使用，打印
//        String str = null;
        String[] str = new String[]{"111", null, "333"};
        Optional<String[]> str1 = Optional.ofNullable(str);
        String[] strings = str1.get();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            System.out.println(string);
        }
    }

    @Test
    public void test6() {
        //peek中间操作，设值；map集合化，filer过滤操作
        List<User> list = new ArrayList<>();
        list.add(new User("张三", "1322"));
        list.add(new User("李四", "1323"));
        list.add(new User("王五", null));
        list.add(new User("张六", "1324"));
        System.out.println(list.get(0));

        //list.stream().map(u -> u.getUsername()).forEach(System.out::println);
        //如果密码为null，则改成123456默认值；然后取所有密码进行打印
        list.stream().peek(u -> u.setPassword(JudgeUtils.isEmpty(u.getPassword()) ? "123456" : u.getPassword()))
                .map(u -> u.getPassword())
                .forEach(System.out::println);

        //如果密码为null，则改成123456默认值；然后过滤出姓名以王开头的对象，取所有密码进行打印
        list.stream().peek(u -> u.setPassword(JudgeUtils.isEmpty(u.getPassword()) ? "123456" : u.getPassword()))
                .filter(u -> u.getUsername() != null && u.getUsername().substring(0, 1).equals("王"))
                .map(u -> u.getPassword())
                .forEach(System.out::println);

        //List<String> list1 = Optional.ofNullable(list).get().stream().map(entity.User::getUsername).collect(Collectors.toList());
        //list1.stream().peek()

        /*entity.User user = new entity.User("张三", "123");
        String password = Optional.ofNullable(user).map(u -> u.getPassword()).orElse("123456");
        System.out.println(password);*/

    }

    @Test
    public void test7() {
        /*//过滤器的使用
        String[] str = new String[]{"111", null, "333"};
        Optional<String[]> str1 = Optional.ofNullable(str).filter(JudgeUtils::isNotEmpty);
        if (str1.isPresent()) {
            String[] strings = str1.get();
            for (int i = 0; i < strings.length; i++) {
                String s = strings[i];
                System.out.println(s);
            }
        }*/

        User user = new User("lisi",null);
        Optional<User> opt = Optional.ofNullable(user).filter(u -> u.getPassword() != null);
        System.out.println(opt.isPresent() ? opt.get().toString() : "不存在");
    }

    @Test
    public void test8() {
        User user = new User("zs", "235");
        List<User> userList = null;
        user = null;
        //String s = Optional.ofNullable(user).map(u -> u.getPassword()).orElse(null);
        //System.out.println(s);
//        String str ="abcdefg";
//        System.out.println(str.substring(str.length() - 1));

        //对于集合为null，给个默认值
        List<String> nameList = Optional.ofNullable(userList)
                .orElse(new ArrayList<>())
                .stream()
                .map(u -> u.getUsername())
                .collect(Collectors.toList());

        if (!nameList.isEmpty()) {
            System.out.println(nameList);
        }
    }

}

