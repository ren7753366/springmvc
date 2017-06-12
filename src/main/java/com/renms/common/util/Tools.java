package com.renms.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Tools {

//	public static void main(String[] args) throws HttpException, IOException {
//		HttpClient client = new HttpClient();
//		// 设置代理服务器地址和端口
//		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
//		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
//		HttpMethod method = new GetMethod("http://127.0.0.1:8080/op_base/account/pwd.do?username=sunyf@126.com");
//		// 使用POST方法
//		// HttpMethod method = new PostMethod("http://java.sun.com");
//		client.executeMethod(method);
//		// 打印服务器返回的状态
//		System.out.println(method.getStatusLine());
//		// 打印返回的信息
//		System.out.println(method.getResponseBodyAsString());
//		// 释放连接
//		method.releaseConnection();
//		System.out.println(new StrAES().decrypt("87B6FB03DDCE2F8234F8513F94A7EE54AC83318E5C5CAEE171CD6E9E2FB163AF"));
//	}
	
	public static String getDayTimeOfNow(){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Calendar c = Calendar.getInstance();
		return sdf.format(c.getTime());
	}
	
	public static String getFirstDayTimeInMonth(){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		return sdf.format(c.getTime());
	}
    	
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

    /**
     * 获取IP
     * @param request request
     * @return ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] arrIp = ip.trim().split(",");
        ip = "";
        for (int i = 0;i < arrIp.length;i++) {
            if (!"unknown".equalsIgnoreCase(arrIp[i].trim())) {
                ip = arrIp[i];
                break;
            }
        }
        return ip.trim();
    }
    
	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	public static boolean isNumber(String num, String type) {
		String rex = "";
		if ("0+".equals(type)) {
			rex = "^\\d+$";
		} else if ("+".equals(type)) {
			rex = "^\\d*[1-9]\\d*$";
		} else if ("-0".equals(type)) {
			rex = "^((-\\d+)|(0+))$";
		} else if ("-".equals(type)) {
			rex = "^-\\d*[1-9]\\d*$";
		} else {
			rex = "^-?\\d+$";
		}
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(num);
		return m.matches();
	}
	
    /**
     * 验证输入的邮箱格式是否符合
     * @param email
     * @return 是否合法
     */
	public static boolean isEmail(String email) {
        final String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(reg);
        final Matcher m = pattern.matcher(email);
        return m.find();
	}
	
	
	/**
	 * 生成随即密码
	 * @param length 密码长度
	 * @return 密码的字符串
	 */
	public static String getRandStr(int length) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int max = 36;
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (length > 0) {
			// 生成随机数，取绝对值，防止生成负数，
			int i = Math.abs(r.nextInt(max)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				length--;
			}
		}
		return pwd.toString();
	}

	/**
	 * 判断参数是否为空，为空则返回""，否则返回其值
	 * 
	 * @param sSource
	 *            源字符串
	 * @return 字符串
	 */
	public String getString(String sSource) {
		String sReturn = "";
		if (sSource != null) {
			sReturn = sSource;
		}
		return sReturn;
	}

	/**
	 * 判断参数是否为0，为0则返回""，否则返回其值
	 * 
	 * @param iSource
	 *            源字符串
	 * @return 字符串
	 */
	public static String getString(int iSource) {
		if (iSource == 0) {
			return "";
		} else {
			return "" + iSource;
		}
	}

	/**
	 * 转码：GBK ----> iso-8859-1
	 * 
	 * @param s
	 *            转码字段
	 * @return 转码后的字段
	 */
	public static String GBKtoISO(String s) {
		try {
			s = new String(s.getBytes("GBK"), "iso-8859-1");
		} catch (Exception e) {
		}
		return s;
	}

	/**
	 * 转码：iso-8859-1 ----> GBK
	 * 
	 * @param s
	 *            转码字段
	 * @return 转码后的字段
	 */
	public static String ISOtoGBK(String s) {
		try {
			s = new String(s.getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
		}
		return s;
	}

	/**
	 * 判断参数是否为空，为空则返回一个长度为0的字符串数组，否则返回其值
	 * 
	 * @param aSource
	 *            源字符串数组
	 * @return 字符串
	 */
	public String[] getArray(String[] aSource) {
		String aReturn[] = new String[0];
		if (aSource != null) {
			aReturn = aSource;
		}
		return aReturn;
	}

	/**
	 * 判断参数是否为空，为空则返回0,不为空则返回其整型值
	 * 
	 * @param sSource
	 *            源字符串
	 * @return 整型数
	 */
	public int getInt(String sSource) {
		int iReturn = 0;
		if (sSource != null && !sSource.equals("")) {
			iReturn = Integer.parseInt(sSource);
		}
		return iReturn;
	}

	/**
	 * 判断参数是否为空，为空则返回一个长度为0的整形数组，否则返回其值
	 * 
	 * @param aSource
	 *            源字符串数组
	 * @return 整形数组
	 */
	public int[] getIntArray(String[] aSource) {
		int iReturn[] = new int[0];
		if (aSource != null) {
			iReturn = new int[aSource.length];
			for (int i = 0; i < aSource.length; i++) {
				iReturn[i] = Integer.parseInt(aSource[i]);
			}
		}
		return iReturn;
	}

	/**
	 * 判断参数是否为空，为空则返回0,不为空则返回其整型值
	 * 
	 * @param sSource
	 *            源字符串
	 * @return Double数
	 */
	public double getDouble(String sSource) {
		double dReturn = 0.00;
		if (sSource != null && !sSource.equals("")) {
			dReturn = (new Double(sSource)).doubleValue();
		}
		return dReturn;
	}

	/**
	 * 查找以逗号分隔的源字符串是否包含给定字符串
	 * 
	 * @param sSource
	 *            :源字符串
	 * @param sItem
	 *            :子串
	 * @return 是否包含
	 */
	public boolean isContain(String sSource, String sItem) {
		boolean isReturn = false;
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, ",");
		while (st.hasMoreTokens()) {
			if (sItem.equals(st.nextToken())) {
				isReturn = true;
				break;
			}
		}
		return isReturn;
	}

	/**
	 * 查找源字符串数组中是否包含给定字符串
	 * 
	 * @param aSource
	 *            :源字符串数组
	 * @param sItem
	 *            :子串
	 * @return 是否包含
	 */
	public boolean isContain(String[] aSource, String sItem) {
		boolean isReturn = false;
		for (int i = 0; i < aSource.length; i++) {
			if (sItem.equals(aSource[i])) {
				isReturn = true;
				break;
			}
		}
		return isReturn;
	}

	/**
	 * 将指定字符串从源字符串中删除掉，并返回替换后的结果字符串
	 * 
	 * @param source
	 *            源字符串
	 * @param subString
	 *            要删除的字符
	 * @return 替换后的字符串
	 */
	public String delete(String source, String subString) {
		StringBuffer output = new StringBuffer();
		// 源字符串长度
		int lengthOfSource = source.length();
		// 开始搜索位置
		int posStart = 0;
		// 搜索到老字符串的位置
		int pos;
		while ((pos = source.indexOf(subString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));
			posStart = pos + 1;
		}
		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	/**
	 * 此函数有三个输入参数，源字符串(将被操作的字符串),原字符串中被替换的字符串(旧字符串)
	 * 替换的字符串(新字符串)，函数接收源字符串、旧字符串、新字符串三个值后， 用新字符串代替源字符串中的旧字符串并返回结果
	 * 
	 * @param source
	 *            源字符串
	 * @param oldString
	 *            旧字符串
	 * @param newString
	 *            新字符串
	 * @return 替换后的字符串
	 */
	public static String replace(String source, String oldString,
			String newString) {
		StringBuffer output = new StringBuffer();
		int lengthOfSource = source.length(); // 源字符串长度
		int lengthOfOld = oldString.length(); // 老字符串长度
		int posStart = 0; // 开始搜索位置
		int pos; // 搜索到老字符串的位置
		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
			posStart = pos + lengthOfOld;
		}
		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	/**
	 * 将给定的源字符串加1 例如：“0001” 经本函数转换后返回为“0002”
	 * 
	 * @param sSource
	 *            :源字符串
	 * @return 返回字符串
	 */
	public String increaseOne(String sSource) {
		String sReturn = null;
		int iSize = 0;
		iSize = sSource.length();
		long l = (new Long(sSource)).longValue();
		l++;
		sReturn = String.valueOf(l);
		for (int i = sReturn.length(); i < iSize; i++) {
			sReturn = "0" + sReturn;
		}
		return sReturn;
	}

	/**
	 * 将给定的整数转化成字符串，结果字符串的长度为给定长度,不足位数的左端补"0" 例如val=10，len=5，那么生成的字符串为"00010"
	 * 
	 * @param val
	 *            将被转化成字符串的整数
	 * @param len
	 *            转化后的长度
	 * @return String 返回值
	 */
	public String intToStr(int val, int len) {
		String sReturn = new String();
		sReturn = String.valueOf(val);
		if (sReturn.length() < len) {
			for (int i = len - sReturn.length(); i > 0; i--) {
				sReturn = "0" + sReturn;
			}
		}
		return sReturn;
	}

	/**
	 * 将数组中的每个元素两端加上给定的符号
	 * 
	 * @param aSource
	 *            源数组
	 * @param sChar
	 *            符号
	 * @return 处理后的字符串数组
	 */
	public String[] arrayAddSign(String[] aSource, String sChar) {
		String aReturn[] = new String[aSource.length];
		for (int i = 0; i < aSource.length; i++) {
			aReturn[i] = sChar + aSource[i] + sChar;
		}
		return aReturn;
	}

	/**
	 * 将数组中的元素连成一个以逗号分隔的字符串
	 * 
	 * @param aSource
	 *            源数组
	 * @return 字符串
	 */
	public String arrayToString(String[] aSource) {
		String sReturn = "";
		for (int i = 0; i < aSource.length; i++) {
			if (i > 0) {
				sReturn += ",";
			}
			sReturn += aSource[i];
		}
		return sReturn;
	}

	/**
	 * 将数组中的元素连成一个以逗号分隔的字符串
	 * 
	 * @param aSource
	 *            源数组
	 * @return 字符串
	 */
	public String arrayToString(int[] aSource) {
		String sReturn = "";
		for (int i = 0; i < aSource.length; i++) {
			if (i > 0) {
				sReturn += ",";
			}
			sReturn += aSource[i];
		}
		return sReturn;
	}

	/**
	 * 将数组中的元素连成一个以给定字符分隔的字符串
	 * 
	 * @param aSource
	 *            源数组
	 * @param sChar
	 *            分隔符
	 * @return 字符串
	 */
	public String arrayToString(String[] aSource, String sChar) {
		String sReturn = "";
		for (int i = 0; i < aSource.length; i++) {
			if (i > 0) {
				sReturn += sChar;
			}
			sReturn += aSource[i];
		}
		return sReturn;
	}

	/**
	 * 将两个字符串的所有元素连结为一个字符串数组
	 * 
	 * @param array1
	 *            源字符串数组1
	 * @param array2
	 *            源字符串数组2
	 * @return String[]
	 */
	public String[] arrayAppend(String[] array1, String[] array2) {
		int iLen = 0;
		String aReturn[] = null;
		if (array1 == null) {
			array1 = new String[0];
		}
		if (array2 == null) {
			array2 = new String[0];
		}
		iLen = array1.length;
		aReturn = new String[iLen + array2.length];
		/**
		 * 将第一个字符串数组的元素加到结果数组中
		 */
		for (int i = 0; i < iLen; i++) {
			aReturn[i] = array1[i];
		}
		/**
		 * 将第二个字符串数组的元素加到结果数组中
		 */
		for (int i = 0; i < array2.length; i++) {
			aReturn[iLen + i] = array2[i];
		}
		return aReturn;
	}

	/**
	 * 将两个对象数组中的所有元素连结为一个对象数组
	 * 
	 * @param array1
	 *            源字符串数组1
	 * @param array2
	 *            源字符串数组2
	 * @return Object[]
	 */
	public Object[] arrayAppend(Object[] array1, Object[] array2) {
		int iLen = 0;
		Object aReturn[] = null;
		if (array1 == null) {
			array1 = new Object[0];
		}
		if (array2 == null) {
			array2 = new Object[0];
		}
		iLen = array1.length;
		aReturn = new Object[iLen + array2.length];
		/**
		 * 将第一个对象数组的元素加到结果数组中
		 */
		for (int i = 0; i < iLen; i++) {
			aReturn[i] = array1[i];
		}
		/**
		 * 将第二个对象数组的元素加到结果数组中
		 */
		for (int i = 0; i < array2.length; i++) {
			aReturn[iLen + i] = array2[i];
		}
		return aReturn;
	}

	/**
	 * 拆分以逗号分隔的字符串,并存入String数组中
	 * 
	 * @param sSource
	 *            源字符串
	 * @return String[]
	 */
	public String[] strToArray(String sSource) {
		String aReturn[] = null;
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, ",");
		aReturn = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			aReturn[i] = st.nextToken();
			i++;
		}
		return aReturn;
	}

	/**
	 * 拆分以给定分隔符分隔的字符串,并存入字符串数组中
	 * 
	 * @param sSource
	 *            源字符串
	 * @param sChar
	 *            分隔符
	 * @return String[]
	 */
	public static String[] strToArray(String sSource, String sChar) {
		String aReturn[] = null;
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, sChar);
		int i = 0;
		aReturn = new String[st.countTokens()];
		while (st.hasMoreTokens()) {
			aReturn[i] = st.nextToken();
			i++;
		}
		return aReturn;
	}

	/**
	 * 拆分以给定分隔符分隔的字符串,并存入整型数组中
	 * 
	 * @param sSource
	 *            源字符串
	 * @param sChar
	 *            分隔符
	 * @return int[]
	 */
	public static int[] strToArray(String sSource, char sChar) {
		int aReturn[] = null;
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, String.valueOf(sChar));
		int i = 0;
		aReturn = new int[st.countTokens()];
		while (st.hasMoreTokens()) {
			aReturn[i] = Integer.parseInt(st.nextToken());
			i++;
		}
		return aReturn;
	}

	/**
	 * 将以逗号分隔的字符串的每个元素加上单引号 如： 1000,1001,1002 --> '1000','1001','1002'
	 * 
	 * @param sSource
	 *            源串
	 * @return String
	 */
	public String addMark(String sSource) {
		String sReturn = "";
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, ",");
		if (st.hasMoreTokens()) {
			sReturn += "'" + st.nextToken() + "'";
		}
		while (st.hasMoreTokens()) {
			sReturn += "," + "'" + st.nextToken() + "'";
		}
		return sReturn;
	}

	/**
	 * 删除磁盘上的文件
	 * 
	 * @param fileName
	 *            文件全路径
	 * @return boolean
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	/**
	 * 判断字符串是否可转换成数字
	 * 
	 * @param fileName
	 *            源串
	 * @return boolean
	 */
	public static boolean isNumber(String strInput) {
		boolean bRs = false;
		try {
			Integer.parseInt(strInput);
			bRs = true;
		} catch (Exception e) {
			bRs = false;
		}
		return bRs;
	}

}