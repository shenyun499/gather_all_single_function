package pers.xue.encrypt.aes;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author huangzhixue
 * @date 2022/11/23 22:46
 * @Description
 */
@Slf4j
public class AESUtil {
    public static String encrypt(String message) {
        if (message == null) {
            return null;
        }
        SecretKeySpec spec = new SecretKeySpec("1234567891234567".getBytes(StandardCharsets.UTF_8), "AES");
        try {
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, spec);
            byte[] bytes = encryptCipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("encrypt fail", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String ss = encrypt("ss");
        System.out.println(ss);
    }
}
