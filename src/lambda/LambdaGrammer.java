package lambda;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @Description
 * Lambda语法学习 练习
 * 有无参数主要看左边接收的函数
 * @Author xuexue
 * @Date 2019/12/14 15:21
 */
public class LambdaGrammer {
    private static final Logger log = LoggerFactory.getLogger(LambdaGrammer.class);

    private List<Integer> list = Arrays.asList(20, 30, 40, 10, 15);

    @Test
    public void test1() {
        //一、无参数，无返回值，一条语句
/*        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("我是普通的实现，一条语句");
            }
        };*/
        Runnable r = () -> System.out.println("我是Lambda表达式实现，一条语句");
        Thread thread = new Thread(r);
        thread.start();
    }

    @Test
    public void test2() {
        //二、无参数，无返回值，多条语句
/*        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("我是普通的实现");
                System.out.println("我是多条语句");
            }
        };*/
        Runnable r = () -> {
            System.out.println("我是Lambda表达式实现");
            System.out.println("我是多条语句");
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    @Test
    public void test3() {
        //三、无参数，有返回值，一条语句（函数式接口函数-Supplier）
        /*Supplier<String> su = () -> new String("aaa");
        String s = su.get();
        System.out.println(s);*/
        //四、无参数，有返回值，多条语句（函数式接口函数-P）
        Supplier<String> su = () -> {
            System.out.println("多条语句");
            return new String("aaa");
        };
        String s = su.get();
        System.out.println(s);
    }

    @Test
    public void test4() {
        //五、有参数，无返回值，一条语句（函数式接口函数-Consumer）
        /*Consumer<Integer> con = (e) -> System.out.println(e);
        con.accept(4);*/
        //六、有参数，无返回值，多条语句（函数式接口函数-Consumer）
        Consumer<Integer> con = (e) -> {
            System.out.println(e);
            System.out.println("多条语句");
        };
        con.accept(4);
    }

    @Test
    public void test5() {
        //七、有一个参数，有返回值，一条语句（函数式接口函数-Function）
        Function<String, String> fu = (str) -> str.toUpperCase();
        String str = fu.apply("abcde");
        System.out.println(str);
    }

    @Test
    public void test6() {
        List<Integer> newList = new ArrayList<>();
        //八、有一个参数，有返回值，一条语句（函数式接口函数 Predicate 扩展就是BiPredicate)
        //求年龄大于20的数
        Predicate<Integer> pre = (age) -> age > 20;
        for (Integer item : list) {
            if (pre.test(item)) {
                newList.add(item);
            }
        }
        for (Integer newItem : newList) {
            System.out.println(newItem);
        }
    }

    @Test
    public void test7() {
        //需要，排序器实现
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> treeSet = new TreeSet<>(com);
        treeSet.addAll(Arrays.asList(6, 3, 2, 7, 1));
        for (Integer item : treeSet) {
            System.out.println(item);
        }
    }

    @Test
    public void test8() {
        //实例::方法名 条件：函数式接口的方法参数列表和返回值要和Lambda表达式中使用了的方法方法参数和返回值保持一致 如Consumer的accept和System.out的println
        Consumer<Integer> con = System.out::println;
        con.accept(5);
    }

    @Test
    public void test9() {
        //类名::静态方法名 条件：函数式接口的方法参数列表和返回值要和Lambda表达式中使用了的方法方法参数和返回值保持一致 如Comparator的compare和Integer的静态方法compare
        Comparator<Integer> com = Integer::compare;
        TreeSet<Integer> treeSet = new TreeSet<>(com);
        treeSet.addAll(Arrays.asList(20, 3, 6, 1, 30));
        for (Integer item : treeSet) {
            System.out.println(item);
        }
    }

    @Test
    public void test10() {
        //类名::方法名
        //条件1：函数式接口的方法参数列表和返回值要和Lambda表达式中使用了的方法方法参数和返回值保持一致 如Comparator的compare和Integer的静态方法compare
        //条件2：传入第一个参数刚好是函数调用者，第二个参数刚好是函数参数 如equals
        //BiPredicate<String, String> biP = (str1, str2) -> str1.equals(str2);
        BiPredicate<String, String> biP = String::equals;
        boolean test = biP.test("abc", "cba");
        System.out.println(test);
    }

    @Test
    public void test11() {
        Runnable r = getRunnable();
        Thread thread = new Thread(r);
        thread.start();
    }

    public static Runnable getRunnable() {
        /*return () -> {
        // 这里指的是Runnable 的run方法的实现作用，仅仅实现作用
            log.info("aaa");
            System.out.println("我是Lambda表达式实现，一条语句");
            System.out.println("我是Lambda表达式实现，一条语句");
        };*/
        return () -> log.info("aaa");
    }

    /**
     * BigDecimal  求和
     * https://www.debug8.com/java/t_48084.html
     */
    @Test
    public void test12() {
        List<BigDecimal> bigDecimals = Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(20));
        BigDecimal reduce = bigDecimals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(reduce);

        List<AmountObject> amountObjects = Arrays.asList(new AmountObject(BigDecimal.valueOf(200), 1), new AmountObject(BigDecimal.valueOf(300), 2));
        BigDecimal amountSum = amountObjects.stream().map(AmountObject::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(amountSum);
    }

    /**
     * 基本类型 求和
     */
    @Test
    public void test13() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6);
        int sum = ints.stream().mapToInt(value -> value).sum();
        System.out.println(sum);

        List<Long> longs = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        Long longSum = longs.stream().mapToLong(value -> value).sum();
        System.out.println(longSum);

        IntSummaryStatistics collect = list.stream().collect(Collectors.summarizingInt(value -> value));
        System.out.println(collect);
        System.out.println("统计集合元素的个数：" + collect.getCount());
        System.out.println("集合元素累加之和：" + collect.getSum());
        System.out.println("集合中最小值：" + collect.getMax());
        System.out.println("集合中最大值：" + collect.getMin());
        System.out.println("集合中平均值：" + collect.getAverage());
    }

    static class AmountObject {
        private BigDecimal amount;
        private int num;

        public AmountObject(BigDecimal amount, int num) {
            this.amount = amount;
            this.num = num;
        }

        public BigDecimal getAmount() {
            return amount;
        }
    }

}
