package com.hzy.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加解密
 * Created by ziye_huang on 2018/7/17.
 */
public final class DesUtil {

    private DesUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * DES加密
     *
     * @param srcFile  要加密的文件
     * @param destFile 加密后存储的文件
     * @param password 密码（最少8位）
     * @throws Exception
     */
    public static void encryptData(File srcFile, File destFile, String password) throws Exception {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        Cipher cipher = Cipher.getInstance("DES");
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(desKey), random);
        CipherInputStream cis = new CipherInputStream(fis, cipher);

        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = cis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        cis.close();
        fis.close();
        fos.close();
    }

    /**
     * DES解密
     *
     * @param srcFile  要解密的文件
     * @param destFile 解密后存储的文件
     * @param password 密码（最少8位）
     * @throws Exception
     */
    public static void decryptData(File srcFile, File destFile, String password) throws Exception {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        Cipher cipher = Cipher.getInstance("DES");
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(desKey), random);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);

        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = fis.read(buffer)) != -1) {
            cos.write(buffer, 0, len);
        }
        cos.close();
        fis.close();
        fos.close();
    }

}
