package com.renms.common.util;

import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密算法
 * <p>Title: StrAES.java</p>
 * <p>Descripton: StrAES</p>
 * @author sunyu
 * date 2013-8-23 下午7:35:46
 * @version 1.0
 */
public class StrAES {
    
    private static final String DEFAULT_KEY = "renms*&webgame*^&*&*!hello*world";

    private Cipher cipher = null;
    
    private SecretKeySpec secretKey = null;

    /**
     * 默认密钥构造方法
     * @throws Exception java.lang.Exception
     */
	public StrAES() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        initSecretKey(DEFAULT_KEY);
    }
    
    /**
     * 指定密钥构造方法
     * @param strKey 指定的密钥
     * @throws Exception java.lang.Exception
     */
	public StrAES(String key) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        initSecretKey(key);
    }
    
    
    /**
     * 获取密钥
     * @param key 密钥
     * @return SecretKey
     * @throws Exception
     */
    private void initSecretKey(String key) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            generator.init(128, random);
            byte[] encoded = generator.generateKey().getEncoded();
            secretKey = new SecretKeySpec(encoded, "AES");
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密字符串
     * @param value 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception java.lang.Exception
     */
    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return bytes2Hex(cipher.doFinal(value.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密字符串
     * @param value 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception java.lang.Exception
     */
    public String decrypt(String value) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(hex2Bytes(value)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /** 
     * <p>Title: bytes2HexStr</p> 
     * <p>Description: 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程</p> 
     * @param bytes 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private static String bytes2Hex(byte[] bytes) {
        int length = bytes.length;
        StringBuffer sb = new StringBuffer(length * 2);
        for (byte b : bytes) {  
            String hex = Integer.toHexString(b & 0xFF);  
            if (hex.length() == 1) {  
                    hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());
        }  
        return sb.toString();
    }

    /** 
     * <p>Title: hexStr2Bytes</p> 
     * <p>Description: 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程</p> 
     * @param hexStr  需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private static byte[] hex2Bytes(String hexStr) {
        int length = hexStr.length();
        byte[] result = new byte[length / 2];
        for (int i = 0; i < length; i = i + 2) {
            result[i / 2] = (byte) Integer.parseInt(hexStr.substring(i, i + 2), 16);
        }
        return result;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(DEFAULT_KEY.length());
        StrAES aes = new StrAES();
        String value="xxx@cyou-inc.com";
        String result = aes.encrypt(value);
        System.out.println(value + "  >>>>  " + result);
        String value2 = aes.decrypt(result);
        System.out.println(result + "  >>>>  " + value2);
    }

}