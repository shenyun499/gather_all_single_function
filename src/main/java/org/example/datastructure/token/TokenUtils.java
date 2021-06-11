package org.example.datastructure.token;

import org.junit.Test;

import util.UUID;

/**
 * token生成
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-28
 */
public class TokenUtils {

    private static String[] chars36 = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "", "1", "2", "3", "4", "5", "6", "7", "8",
            "9"};

    @Test
    public void test() {
        /**
         * 生成32位唯一码
         */
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 32; i++) {
            String str = uuid.substring(i * 1, i * 1 + 1);
            int x = Integer.parseInt(str, 16);
            // 如果是 chars62,则是x%62
            shortBuffer.append(chars36[x % 36]);
        }
        System.out.println(shortBuffer.toString());
    }
}
