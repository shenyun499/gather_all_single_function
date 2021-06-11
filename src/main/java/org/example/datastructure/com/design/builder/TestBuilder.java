package org.example.datastructure.com.design.builder;

import com.sun.xml.internal.ws.util.StringUtils;
import org.junit.Test;

/**
 * @Description
 * 测试建造者模式
 * @Author xuexue
 * @Date 2019/9/1410:02
 */
public class TestBuilder {

    @Test
    public void test() {
        HourseDirector myHourseDirector = new MyHourseDirector();
        Hourse hourse = myHourseDirector.director();
        if (hourse != null) {
            //可以入住了
            myHourseDirector.isIn();
        }

    }

}
