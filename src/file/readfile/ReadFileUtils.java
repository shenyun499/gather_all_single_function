package file.readfile;

import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;

/**
 * 几种高效读取文件的方式
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-17
 */
public class ReadFileUtils {
    @Test
    public void test12() {
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void test1() throws IOException {
        //定义可变字符串进行接收
        StringBuffer stringBuffer = new StringBuffer();
        //定义小数组读取(一次读8kb，2m文件大概读取2*1024/8=256次，也就是与磁盘交换256次)
        byte[] bytes = new byte[8*1024];
        //使用FileInputStream输入流
        try(FileInputStream fileInputStream = new FileInputStream("D:\\project_location\\datastructure\\src\\json.json")) {
            long startTime = System.currentTimeMillis();
            int len,i=0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                i++;
                stringBuffer.append(new String(bytes, 0, len));
            }
            System.out.println(i);
            //消耗时间（大概消耗0.16s）
            System.out.println("使用小数组+FileInputStream耗时:"+ (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws IOException {
        //定义可变字符串进行接收
        StringBuffer stringBuffer = new StringBuffer();
        //定义小数组读取
        byte[] bytes = new byte[8*1024];
        //使用BufferedInputStream缓冲流
        try(BufferedInputStream is = new BufferedInputStream(new FileInputStream("D:\\project_location\\datastructure\\src\\json.json"));) {
            long startTime = System.currentTimeMillis();
            int len;
            while ((len = is.read(bytes)) != -1) {
                stringBuffer.append(new String(bytes, 0, len));
            }
            //消耗时间（大概消耗0.17s）
            System.out.println("使用小数组+BufferedIuputStream耗时:"+ (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        //创建可变字符流接收
        StringBuffer stringBuffer = new StringBuffer();
        //使用BufferedReader流   将字节流转为字符流，进行高效读取
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("D:\\project_location\\datastructure\\src\\json.json"),"utf-8"))) {
            String str = null;
            //记录开始时间
            long startTime = System.currentTimeMillis();
            //按行读取
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
            //花费时间（大概0.26s)
            System.out.println("使用BufferedReader耗时：" + (System.currentTimeMillis() - startTime) / 1000.0  + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        //创建可变字符流接收
        StringBuilder stringBuilder = new StringBuilder();
        //定义小数组读取
        char[] chars = new char[8*1024];
        //使用BufferedReader流   将字节流转为字符流，进行高效读取
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("D:\\project_location\\datastructure\\src\\json.json"),"utf-8"))) {
            //记录开始时间
            long startTime = System.currentTimeMillis();
            int len = 0;
            while ((len = bufferedReader.read(chars)) != -1) {
                stringBuilder.append(new String(chars, 0, len));
            }
            //花费时间（大概0.18s)
            System.out.println("使用BufferedReader耗时：" + (System.currentTimeMillis() - startTime) / 1000.0  + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        byte[] bytes = new byte[8*1024];
        //使用BufferedInputStream输入流
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("D:\\project_location\\datastructure\\src\\json.json"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("D:\\project_location\\datastructure\\src\\json2.json"))) {
            long startTime = System.currentTimeMillis();
            int len, i = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                i++;
                bufferedOutputStream.write(bytes,0, len);
            }
            System.out.println(i);
            //消耗时间（大概消耗0.04s）
            System.out.println("使用BufferedInputStream进行拷贝耗时:" + (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test6() throws IOException {
        //定义可变字符串进行接收
        StringBuffer stringBuffer = new StringBuffer();
        //定义小数组读取(一次读8kb，2m文件大概读取2*1024/8=256次，也就是与磁盘交换256次)
        byte[] bytes = new byte[8*1024];
        //使用FileInputStream输入流
        try(FileInputStream fileInputStream = new FileInputStream("D:\\project_location\\datastructure\\src\\json.json");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\project_location\\datastructure\\src\\json3.json")) {
            long startTime = System.currentTimeMillis();
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                stringBuffer.append(new String(bytes, 0, len));
            }
            System.out.println(stringBuffer);
            //消耗时间（大概消耗0.16s）
            System.out.println("使用小数组+FileInputStream耗时:"+ (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
