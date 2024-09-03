package pers.xue.boot_jpa_mul_datasouce.config;

/**
 * @author huangzhixue
 * @date 2024/9/3 11:46
 * @Description
 *  用来保存当前线程的EntityManagerFactory名称 的工具类
 */
public class EntityManagerContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setEntityManagerFactoryType(String entityManagerFactoryType) {
        contextHolder.set(entityManagerFactoryType);
    }

    public static String getEntityManagerFactoryType() {
        return contextHolder.get();
    }

    public static void clearEntityManagerFactoryType() {
        contextHolder.remove();
    }
}
