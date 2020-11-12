package base64;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 加密、解密封装
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-11-04
 */
public class Base64Utils {

    /**
     * 将加密后的文件解密
     * @param encryptEcert Base64编码的字符串
     * @param charset 编码格式
     * @return 明文字符串
     */
    public static String decryptByBase64(String encryptEcert, String charset) {// UTF-8
        String ecert = null;
        byte[] bytes = Base64.getDecoder().decode(encryptEcert);
        try {
            ecert = new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ecert;
    }

    /**
     * 转成base64编码
     * @param encodeEcert 明文
     * @param charset 编码格式
     * @return base64编码的字符串
     */
    public static String encodeByBase64(String encodeEcert, String charset) {
        String ecert = null;
        byte[] bytes = Base64.getEncoder().encode(encodeEcert.getBytes());
        try {
            ecert = new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ecert;
    }

}
