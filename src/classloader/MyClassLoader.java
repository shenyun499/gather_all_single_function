package classloader;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;

/**
 * @author huangzhixue
 * @date 2024/8/17 09:25
 * @Description
 * 自定义类加载器
 * 场景：
 * 我们可以对 Java 类的字节码（ .class 文件）进行加密，加载时再利用自定义的类加载器对其解密。
 *
 * 需要继承ClassLoader
 * 1、实现findClass接口，并且在里面定义加载类资源的逻辑
 * 2、如果想要破坏双亲委派模式，需要实现LoadClass，这样就不会走父类的LoadClass方法(注意会找不到Object类，所以需要拷贝一个Object放这里，或者
 * 报错找不到Object，加载A类前，会先加载其父类Object，此时可拷贝个Object的class到我这个目录，也可以修改自定义加载器的实现，xx开头时，则交给父类去加载)
 *
 * JVM判断类相同是要通过相同的类加载器加载和类全名相同两个条件判断。
 * 一个类被两个自定义类加载器去加载，会有两个class对象，那问题来了，双亲委派机制呢？不查？这是因为上面我写的自定义类加载器，直接重写了loadClass方法，
 * 而重写的实现里，没有原来的查父类（参考上面loadClass本来的源码），而是直接去指定路径把class读成一个二进制流传入。因此，如果 想在不打破双亲委派机制
 * 的前提下自定义类加载器，那正确姿势应该是重写loadClass内部调用的findClass方法，且常规开发自定义类加载器，重写的也是findClass方法，而非
 * loadClass方法
 *
 * 官方文档：
 * 如果我们不想打破双亲委派模型，就重写 ClassLoader 类中的 findClass() 方法即可，无法被父类加载器加载的类最终会通过这个方法被加载。
 * 但是，如果想打破双亲委派模型则需要重写 loadClass() 方法，为啥重写loadClass方法会打破双亲委派模型呢？
 * 因为类加载器在进行类加载的时候，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器去完成（调用父加载器 loadClass()方法来加载类）
 * 我们比较熟悉的 Tomcat 服务器为了能够优先加载 Web 应用目录下的类，然后再加载其他目录下的类，就自定义了类加载器 WebAppClassLoader 来打破双亲委托机制。
 * 这也是 Tomcat 下 Web 应用之间的类实现隔离的具体原理
 * https://zhuanlan.zhihu.com/p/612318527
 *
 *
 * 原文链接：https://blog.csdn.net/llg___/article/details/135253026
 *
 *
 * 面试题，自定义一个Object会怎么样
 * 如果没有使用双亲委派模型，而是每个类加载器加载自己的话就会出现一些问题，比如我们编写一个称为 java.lang.Object 类的话，那么程序运行的时候，
 * 系统就会出现两个不同的 Object 类。双亲委派模型可以保证加载的是 JRE 里的那个 Object 类，而不是你写的 Object 类。这是因为 AppClassLoader
 * 在加载你的 Object 类时，会委托给 ExtClassLoader 去加载，而 ExtClassLoader 又会委托给 BootstrapClassLoader，BootstrapClassLoader
 * 发现自己已经加载过了 Object 类，会直接返回，不会去加载你写的 Object 类。
 */
public class MyClassLoader extends ClassLoader {
    private String basePath;
    private final static String FILE_EXT = ".class";

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }


    /**
     * @param name
     * @return
     * @throws ClassNotFoundException
     *
     * 打破了双亲委派模型
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 我现在重写了loadClass方法，并把原来源码中的双亲委派机制代码那点代码去掉了（就判断parent是否为空，自己调自己的loadClass方法的那点源码）
        byte[] data = null;
        try {
            String tempName = name.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
            FileInputStream fis = new FileInputStream(basePath + tempName + FILE_EXT);
            try {
                data = IOUtils.toByteArray(fis);
            } finally {
                IOUtils.closeQuietly(fis);
            }

        } catch (Exception e) {
            System.out.println("自定义类加载器加载失败，错误原因：" + e.getMessage());
            return null;
        }
        return defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
//        myClassLoader.setBasePath("/Users/huangzhixue/IdeaProjects/gather_all_single_function/target/classes/");
//        myClassLoader.loadClass("com.Test.Interrupt");
        System.out.println(myClassLoader);
        System.out.println(myClassLoader.getParent());
        MyClassLoader2 myClassLoader2 = new MyClassLoader2();
        myClassLoader2.setBasePath("/Users/huangzhixue/IdeaProjects/gather_all_single_function/target/classes/");
        myClassLoader2.loadClass("com.Test.Interrupt");
        System.out.println(myClassLoader2);
        System.out.println(myClassLoader2.getParent());
        System.out.println(myClassLoader2.getParent().getParent());
        System.out.println(myClassLoader2.getParent().getParent().getParent());
    }
}

class MyClassLoader2 extends ClassLoader {

    private String basePath;
    private final static String FILE_EXT = ".class";

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    /**
     * @param name
     * @return
     * @throws ClassNotFoundException
     *
     * 自定义加载class的方式，不破坏双亲委派模型
     * 默认的findClass可以看ClassLoader里面的loadClass
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = null;
        try {
            String tempName = name.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
            FileInputStream fis = new FileInputStream(basePath + tempName + FILE_EXT);
            try {
                data = IOUtils.toByteArray(fis);
            } finally {
                IOUtils.closeQuietly(fis);
            }

        } catch (Exception e) {
            System.out.println("自定义类加载器加载失败，错误原因：" + e.getMessage());
            return null;
        }
        return defineClass(name, data, 0, data.length);
    }
}
