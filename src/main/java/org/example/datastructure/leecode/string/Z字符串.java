package org.example.datastructure.leecode.string;

import util.ArrayList;
import util.List;
import util.Map;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/1/7 20:43
 */
public class Z字符串 {
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        int row = 0;
        boolean isVer = false;
        int min = Math.min(numRows, s.length());
        List<StringBuffer> list = new ArrayList<>();
        for (int i = 0; i < min; i++) {
            list.add(new StringBuffer());
        }
        for (char c : s.toCharArray()) {
            list.get(row).append(c);
            if (row == 0 || row == numRows -1) {
                isVer = !isVer;
            }
            //根据isVer作方向转换
            row += isVer ? 1 : -1;
        }
        StringBuffer s1 = new StringBuffer();
        for (StringBuffer str : list) {
            s1.append(str);
        }
        return s1.toString();
    }

}
