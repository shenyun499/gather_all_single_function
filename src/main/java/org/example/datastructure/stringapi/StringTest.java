package org.example.datastructure.stringapi;

import org.junit.Test;

/**
 * 字符串常用api操作
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-09-01
 */
public class StringTest {
    private final static String swiftMsg = "{1:F01PONCHKH0AXXX0000000000}{2:I103AAMBSGS1XXXXN}{3:{103:HKT}{108:392O200827010003}}{4:\n" +
            ":20:392O200827010003\n" +
            ":23B:CRED\n" +
            ":32A:211129HKD111,20\n" +
            ":50K:/500000352005\n" +
            "Test\n" +
            ":57A:AAMBSGS1XXX\n" +
            ":59:/TANG\n" +
            "CHINA\n" +
            "GD\n" +
            "SZ\n" +
            ":71A:SHA\n" +
            ":72:/SPRO/01\n" +
            "-}";

    @Test
    public void test1() {
        int pos = swiftMsg.lastIndexOf("{2:");//29 从后往前找，返回第一个出现的字符的索引（也可以指定从后面开始往前找的位置）
        int end = swiftMsg.indexOf("}", pos);//49 从前往后找，指定从pos开始找，返回第一个出现的字符的索引
        String field2 = swiftMsg.substring(pos + 3, end);//I103AAMBSGS1XXXXN
        String s = "PONCHKH0AXXX000";
        boolean p = s.contains("CHK");
        System.out.println(p);
        //System.out.println(pos + "," + end + "," + field2);
    }
}
