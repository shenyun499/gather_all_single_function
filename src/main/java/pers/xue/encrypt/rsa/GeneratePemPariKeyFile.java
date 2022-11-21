package pers.xue.encrypt.rsa;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author huangzhixue
 * @date 2022/11/15 09:55
 * @Description
 * refer to https://www.baeldung.com/java-rsa
 */
@Slf4j
public class GeneratePemPariKeyFile {
    private static final String pairKeyDirectory = "src/main/resources/rsa";
    private static final String publicKeyPath = "src/main/resources/rsa/rsaPublicKey.pem";
    /**
     * 上面已经创建了src/main/resources/rsa，这里可以用全称，然后通过Paths.get去创建rsaPrivateKey.pem文件
     */
    private static final String privateKeyPath = "src/main/resources/rsa/rsaPrivateKey.pem";
    private static final String publicKeyPrefix = "PUBLIC KEY";
    private static final String privateKeyPrefix = "PRIVATE KEY";

    public static void generatePublicKeyPemFile() throws NoSuchAlgorithmException, IOException {
        // algorithm 指定算法为RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 指定密钥长度为2048
        keyPairGenerator.initialize(2048);
        // 生成密钥
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 文件夹不存在，则先创建
        Files.createDirectories(Paths.get(pairKeyDirectory));

        try(FileWriter priFileWriter = new FileWriter(privateKeyPath);
            PemWriter priPemWriter = new PemWriter(priFileWriter);
            FileWriter pubFileWriter = new FileWriter(publicKeyPath);
            PemWriter pubPemWriter = new PemWriter(pubFileWriter)) {
            priPemWriter.writeObject(new PemObject(privateKeyPrefix, keyPair.getPrivate().getEncoded()));
            pubPemWriter.writeObject(new PemObject(publicKeyPrefix, keyPair.getPublic().getEncoded()));
        } catch (IOException e) {
            log.error("generate pem file fail", e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
//        generatePublicKeyPemFile();
        int[][] arr = new int[][]{{1,2,3},{1,2,3}};
        System.out.println(arr.length);
        System.out.println(arr[0].length);
    }
}