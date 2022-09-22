package pers.xue.jpa.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * jasypt test
 *
 *    3.0前默认PBEWithMD5AndDES - MD5加密， 3.0后默认支持的算法为PBEWITHHMACSHA512ANDAES_256 ，该种加密方式由sha512 加 AES 高级加密组成
 *    因为现在是最新的3.0.4版本，默认是PBEWITHHMACSHA512ANDAES_256
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-09-07
 */
public class JasyptTest {
    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        // 默认是PBEWithMD5AndDES加密
        // 盐值
        standardPBEStringEncryptor.setPassword("salt20220907");
        // 加密明文
        String encryptValue1 = standardPBEStringEncryptor.encrypt("shenyun");
        String encryptValue2 = standardPBEStringEncryptor.encrypt("shenyun20220907");
        // 解密密文
        String decryptValue = standardPBEStringEncryptor.decrypt("1PcipFvXoDg95eW3ZP1MOw==");
        System.out.println(encryptValue1);
        System.out.println(encryptValue2);
        System.out.println(decryptValue);
    }

    public static void main1(String[] args) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // 加密方式
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        // 盐值
        config.setPassword("salt20220907");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        //config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 加密明文
        String encryptValue1 = encryptor.encrypt("shenyun");
        String encryptValue2 = encryptor.encrypt("shenyun20220907");
        // 解密密文
//        String decryptValue = encryptor.decrypt("MaF3AwiV/aXCCDMEx+nOgAtXreq62JMgRM+2M9NIpUe0vko3fahZ+IxnvocfaGnJ");
        System.out.println(encryptValue1);
        System.out.println(encryptValue2);
//        System.out.println(decryptValue);

    }
}
