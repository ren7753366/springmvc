package com.renms.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SysStringUtil {

    /**
     * 获得混合中英文的字符串长度
     *
     * @param str String
     * @return int
     */
    public static int getStringLength(String str) {
        int len = 0;
        int temp_len = str.length();
        String singleStr = "";
        byte bStr = 0;
        for (int i = 0; i < temp_len; i++) {
            singleStr = str.substring(i, i + 1);
            bStr = singleStr.getBytes()[0];
            if (bStr < 0 || bStr > 255) {
                len = len + 2;
            } else {
                len = len + 1;
            }
        }
        return len;
    }

    /**
     * 判断是否是中文字符
     *
     * @param c 字符
     * @return
     */
    public static boolean isChinese(char c) {
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z'))
            // 字母, 数字
            return false;
        else if (Character.isLetter(c))
            return true;
        else
            return false;
    }

    /**
     * 使HTML的标签失去作用
     *
     * @param input 被操作的字符串
     * @return String
     */
    public static final String escapeHTMLTag(String input) {
        if (input == null) {
            return "";
        }
        input = input.trim().replaceAll("&", "&amp;");
        input = input.trim().replaceAll("<", "&lt;");
        input = input.trim().replaceAll(">", "&gt;");
        input = input.trim().replaceAll("\t", "    ");
        input = input.trim().replaceAll("\r\n", "\n");
        input = input.trim().replaceAll("\n", "<br>");
        input = input.trim().replaceAll("  ", " &nbsp;");
        input = input.trim().replaceAll("'", "&#39;");
        input = input.trim().replaceAll("\\\\", "&#92;");
        return input;
    }

    public static String cleanHtmlTag(String htmlText) {
        String reg = "</?[a-z][a-z0-9]*[^<>]*>?";
        return htmlText.replaceAll(reg, "");
    }


    /**
     * 判断字符串是否有值，null或空代表没值
     *
     * @param str
     * @return
     */
    public static boolean hasValue(String str) {
        if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
            return false;
        } else {
            return true;
        }
    }

    public static String convertNull(String str) {
        if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
            return "";
        } else {
            return str;
        }
    }

    public static String replaceXmlEntity(String xml) {
        xml = xml.replaceAll("&amp;", "&");
        xml = xml.replaceAll("&quot;", "\"");
        xml = xml.replaceAll("&gt;", ">");
        xml = xml.replaceAll("&nbsp;", " ");
        xml = xml.replaceAll("&apos;", "'");
        return xml;
    }

    /**
     * 对字符串进行编码
     *
     * @param source
     * @return
     */
    public static String encodeStr(String source) {
        String resultStr = "";
        if (source != null && source.length() > 0) {
            try {
                resultStr = URLEncoder.encode(source, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }

    /**
     * 将object类型转换成string
     *
     * @param obj
     * @return
     */
    public static String object2String(Object obj) {
        return (obj == null) ? null : obj.toString();
    }

    /**
     * 对可能的编码字符串进行转码的方法
     *
     * @param source
     * @return
     */
    public static String decodeStr(String source) {
        String resultStr = "";
        if (source != null && source.length() > 0) {
            if (source.contains("%")) {
                try {
                    resultStr = URLDecoder.decode(source, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                resultStr = source;
            }
        }
        return resultStr;
    }

}
