package com.renms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-14
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class SysMd5Util {
    protected static char hexDigits[] = { '0', '1','2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f'};
    protected static MessageDigest messagedigest = null;

    static{
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static String getStringMD5(String text) {

    	return DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));
    	
    }

    public static String getFileMD5(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        in.close();
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]){
        return bufferToHex(bytes, 0,bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n){
        StringBuffer stringbuffer =new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l< k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }


    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt& 0xf0) >> 4];
        char c1 = hexDigits[bt& 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }


    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
