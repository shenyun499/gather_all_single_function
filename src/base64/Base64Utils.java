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
     * @param encryptEcert 加密后的文件
     * @param charset 编码格式
     * @return 明文
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
}
