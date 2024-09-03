package thread;

/**
 * @author huangzhixue
 * @date 2024/8/14 10:46
 * @Description
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        new Thread(() -> {
            ThreadLocal<String> threadLocal = new ThreadLocal<>();
            threadLocal.set("a");
            threadLocal.set("b");
            System.out.println(threadLocal.get());
        }, "T1").start();
    }
}
