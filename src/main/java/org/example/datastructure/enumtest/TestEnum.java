package org.example.datastructure.enumtest;

import org.junit.Test;

/**
 * 枚举类测试
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-30
 */
public class TestEnum {

    @Test
    public void test1() {
        //通过FIRST获取索引
        System.out.println(XueEnum.getIndex("FIRST"));
        //通过索引1获取枚举描述
        System.out.println(XueEnum.getNum(1));
    }

    String json = "{\n" +
            "    \"config\": {\n" +
            "        \"dataTime\": 1577667600016,\n" +
            "        \"restCode\": \"ZREST_FPGL01\",\n" +
            "        \"sysCode\": \"S159\",\n" +
            "        \"uuid\": \"5499994543\"\n" +
            "    },\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"id\": \"PAGENO\",\n" +
            "            \"sign\": \"I\",\n" +
            "            \"option\": \"EQ\",\n" +
            "            \"low\": \"1\",\n" +
            "            \"high\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"PAGESIZE\",\n" +
            "            \"sign\": \"I\",\n" +
            "            \"option\": \"EQ\",\n" +
            "            \"low\": \"1000\",\n" +
            "            \"high\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"I_WERKS\",\n" +
            "            \"sign\": \"I\",\n" +
            "            \"option\": \"EQ\",\n" +
            "            \"low\": \"0101\",\n" +
            "            \"high\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"I_LIFNR\",\n" +
            "            \"sign\": \"I\",\n" +
            "            \"option\": \"EQ\",\n" +
            "            \"low\": \"0000700030\",\n" +
            "            \"high\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"IR_BUDAT\",\n" +
            "            \"sign\": \"I\",\n" +
            "            \"option\": \"BT\",\n" +
            "            \"low\": \"20100722\",\n" +
            "            \"high\": \"20200722\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

}
