package musthandwritecode;

/**
 * 单例模式
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-11
 */
public class Singleton {
    private volatile static Singleton singleton = null;

    private Singleton(){}

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
