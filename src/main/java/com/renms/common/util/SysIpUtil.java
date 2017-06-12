package com.renms.common.util;

import javax.servlet.http.HttpServletRequest;

public class SysIpUtil {

    //比较用户ip前三位是否符合规则
    public static boolean compareUserIp(String userIp, String[] dbIpArray) {
        if (SysStringUtil.hasValue(userIp)) {
            boolean b = false;
            String[] userIpArray = userIp.split("\\.");
            if (userIpArray.length < 4) {
                return false;
            }
            for (int i = 0; i < dbIpArray.length; i++) {
                String[] oneDbIp = dbIpArray[i].split("\\.");
                if ((userIpArray[0] + "." + userIpArray[1] + "." + userIpArray[2]).equals((oneDbIp[0] + "." + oneDbIp[1] + "." + oneDbIp[2]))) {
                    b = true;
                    break;
                } else {
                    b = false;
                }
            }
            return b;
        } else {
            return false;
        }
    }
    
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		String localIP = "127.0.0.1";
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase(localIP) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase(localIP) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase(localIP) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip.split(",") != null){
			ip = ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 判断ip是否为内网
	 */

		public static boolean isInnerIP(String ipAddress){
		        boolean isInnerIp = false;
		        long ipNum = getIpNum(ipAddress);
		        /**
		        私有IP：A类  10.0.0.0-10.255.255.255
		               B类  172.16.0.0-172.31.255.255
		               C类  192.168.0.0-192.168.255.255
		        当然，还有127这个网段是环回地址
		        **/
		        long aBegin = getIpNum("10.0.0.0");
		        long aEnd = getIpNum("10.255.255.255");
		        long bBegin = getIpNum("172.16.0.0");
		        long bEnd = getIpNum("172.31.255.255");
		        long cBegin = getIpNum("192.168.0.0");
		        long cEnd = getIpNum("192.168.255.255");
//		        long dBegin =getIpNum("192.254.1.0");
//		        long dEnd =getIpNum("192.254.1.255");
		        isInnerIp = isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd) || isInner(ipNum,cBegin,cEnd) || ipAddress.equals("127.0.0.1") || ipAddress.equals("124.193.192.82");
		         return isInnerIp;
		}

		/*获取IP数*/
		private static long getIpNum(String ipAddress) {
		    String [] ip = ipAddress.split("\\.");
		    long a = Integer.parseInt(ip[0]);
		    long b = Integer.parseInt(ip[1]);
		    long c = Integer.parseInt(ip[2]);
		    long d = Integer.parseInt(ip[3]);

		    long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		    return ipNum;
		}


		private static boolean isInner(long userIp,long begin,long end){
		     if((userIp>=begin) && (userIp<=end)){
		    	 return true;
		     }else{
		    	 return false;
		     }
		}

		/**
		 * @Title: iptoLong
		 * @Description:IP转换为十进制
		 * @param @param strip
		 * @param @return
		 * @return Long
		 * @throws
		 */
		public static Long ipToLong(String strip)
		{
			try{
				Long [] ip=new Long[4];
				int position1=strip.indexOf(".");
				int position2=strip.indexOf(".",position1+1);
				int position3=strip.indexOf(".",position2+1);
				ip[0]=Long.parseLong(strip.substring(0,position1));
				ip[1]=Long.parseLong(strip.substring(position1+1,position2));
				ip[2]=Long.parseLong(strip.substring(position2+1,position3));
				ip[3]=Long.parseLong(strip.substring(position3+1));
				return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3]; //ip1*256*256*256+ip2*256*256+ip3*256+ip4
			}catch(Exception e){
				return 0L;
			}
		}

		/**
		 * @Title: iptoLong
		 * @Description:已经转换为十进制Ip转换为实际IP
		 * @param @param strip
		 * @param @return
		 * @return Long
		 * @throws
		 */
		public static String longToIp(Long Longip)
		{
			try{
				StringBuffer sb=new StringBuffer("");
				sb.append(String.valueOf(Longip>>>24));//直接右移24位
				sb.append(".");
				sb.append(String.valueOf((Longip&0x00ffffff)>>>16)); //将高8位置0，然后右移16位
				sb.append(".");
				sb.append(String.valueOf((Longip&0x0000ffff)>>>8));
				sb.append(".");
				sb.append(String.valueOf(Longip&0x000000ff));
				sb.append(".");
				return sb.toString();
			}catch(Exception e){
				return null;
			}

		}
}
