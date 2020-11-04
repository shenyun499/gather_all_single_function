package enumtest;

/**
 * 测试枚举类
 * 定义枚举(index, desc)...
 * 重载get方法，通过枚举索引获取枚举描述、通过枚举描述获取枚举索引
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-30
 */
public enum XueEnum {

    /**
     * 第一个
     */
    FIRST(0, "FIRST"),

    /**
     * 第二个
     */
    SENCOND(1, "SENCOND");

    private Integer index;
    private String num;

    XueEnum(Integer index, String num) {
        this.index = index;
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public Integer getIndex() {
        return index;
    }

    /**
     * 根据index获取枚举值
     *
     * @param index 枚举索引
     * @return 枚举描述值
     */
    public static String getNum(Integer index) {
        for (XueEnum xueEnum : XueEnum.values()) {
            if (index.equals(xueEnum.getIndex())) {
                return xueEnum.getNum();
            }
        }
        return null;
    }


    /**
     * @param num 枚举描述值
     * @return 枚举索引
     */
    public static Integer getIndex(String num) {
        for (XueEnum xueEnum : XueEnum.values()) {
            if (num.equals(xueEnum.getNum())) {
                return xueEnum.getIndex();
            }
        }
        return null;
    }
}
