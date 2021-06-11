package org.example.datastructure.file;

import org.junit.Test;

import io.IOException;

/**
 * fpsadmi003处理，对账逻辑测试
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-03
 */
public class FpsAdmi003FileTest {

    /**
     * 报文路径
     */
    private String bakFilePath = "D:\\支付\\2月\\four_pg测试案例\\case62";

    /**
     * 报文文件名
     */
    private String fileName = "FPSPAYI123202fpsadmi003.xml";

    @Test
    public void readMsgXmlFile() {
        HiFile t = null;
        try {
            //读取文件
            t = FileUtil.readBakFile(bakFilePath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String content = t.getFileBody().toString();
    }
}
