package org.example.datastructure.file;

import io.File;

/**
 * 文件过滤读取
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-29
 */
public class FileFilter {
    public static void main(String[] args) {
        //创建File对象，表示d盘下的fileTest目录
        File file = new File("d:/fileTest");
        //过滤所有的标准文件（匿名内部类）
        /*File[] files = org.example.datastructure.file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File org.example.datastructure.file) {
                return org.example.datastructure.file.isFile();
            }
        });*/
    }
}
