package com.renms.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.renms.admin.model.User;

/**
 * @ClassName: TicketUtil.java
 * @Description: 登录验证
 * @author renms
 * @date 2015年9月28日 上午10:21:51 
 * @version 1.0
 */
public class TicketUtil {

	public static final String LOGIN_KEY = "tuH$^aOH-(Ewo$zUop*eNgyo#u_Ba";
	
	public static final int COOKIE_LIFETIME = 86400000;
	
	 /**
     * @Description 创建Cookies
     * @param response
     * @param a
     * @param maxAge
     * @return void 返回类型 
     */
    public static void createCookies(HttpServletRequest request, HttpServletResponse response, User user) {
    	response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
    	int userId = user.getId();
    	Integer pubid = user.getPubid();
    	String userIp = user.getUserIp();
    	String upn = user.getUpn();
    	Integer userType = user.getUserType();
    	String domain = getDomain(request);
    	String sign =  SysMd5Util.getStringMD5(userId + userIp + upn + userType + LOGIN_KEY);
    	setCookie(response, "userId", String.valueOf(userId), domain);
    	if(pubid!=null){
    		setCookie(response, "pubid", String.valueOf(pubid), domain);
    	}
    	setCookie(response, "userIp", String.valueOf(userIp), domain);
    	setCookie(response, "upn", String.valueOf(upn), domain);
    	setCookie(response, "userType", String.valueOf(userType), domain);
    	setCookie(response, "timeFlag", String.valueOf(System.currentTimeMillis()), domain);
    	setCookie(response, "sign", sign, domain);
    }
	
    
    public static void addCookies(HttpServletRequest request, HttpServletResponse response, String name, String value) {
    	String domain = getDomain(request);
    	setCookie(response, name, value, domain);
    }
    
    
    
    /**
     * 验证登陆状态
     * @param request
     * @return
     */
    public static boolean verifyTicket(HttpServletRequest request){
        Map<String, String> map = getAllCookies(request);
        if(map == null || map.size() <= 0) {
            return false;
        }
        //签名验证
        String userId = StringUtils.trimToEmpty(map.get("userId"));
        String upn = StringUtils.trimToEmpty(map.get("upn"));
        String userIp = StringUtils.trimToEmpty(map.get("userIp"));
        String userType = StringUtils.trimToEmpty(map.get("userType"));
        String sign = StringUtils.trimToEmpty(map.get("sign"));
        String md5 = SysMd5Util.getStringMD5(userId + userIp + upn + userType + LOGIN_KEY);
        if(!sign.equals(md5)) {
            return false;
        }
        // 验证cookie的生命期
        long cookieMillis = getLong(map.get("timeFlag"), 0l);
        if(cookieMillis == 0l || System.currentTimeMillis() - cookieMillis > COOKIE_LIFETIME) {
            return false;
        }
        return true;
    }
    
    public static Long getLong(Object value, Long def) {
    	if (value != null && !"".equals(value)) {
    		try {
                return Long.valueOf(value.toString());
            } catch (Exception e) {
                
            }
    	}
        return def;
    }

    /**
     * 删除登陆状态
     * @param request
     * @param response
     */
	/**
	 * @param request
	 * @param response
	 */
	public static void deleteTicket(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
		String domain = getDomain(request);
		deleteCookie(response, "userId", domain);
		deleteCookie(response, "pubid", domain);
        deleteCookie(response, "upn", domain);
        deleteCookie(response, "userIp", domain);
        deleteCookie(response, "userType", domain);
        deleteCookie(response, "timeFlag", domain);
        deleteCookie(response, "sign", domain);
    }

	 /**
     * 获取所有cookie
     * @param request
     * @return
     */
    public static Map<String, String> getAllCookies(HttpServletRequest request) {
        Map<String, String> cookiesMap = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	cookiesMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookiesMap;
    }
    
    
    /**
     * 获取cookie
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	if(key.equals(cookie.getName())){
            		return cookie.getValue();
            	}
            }
        }
        return null;
  	}
    
    
    /**
     * 删除Cookie
     * @param response response
     * @param key key
     * @param domain domain
     */
    public static void deleteCookie(HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
  
    /**
     * 设置Cookie, 关闭浏览器过期
     * @param response response
     * @param name name
     * @param value value
     * @param domain domain
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String domain) {
        Cookie cookie = new Cookie(name, value == null ? "" : value);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
  
    /**
     * 设置Cookie
     * @param response response
     * @param name name
     * @param value value
     * @param maxAge maxAge
     * @param domain domain
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(name, StringUtils.trimToEmpty(value));
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
    
    /**
     * 获取Domain
     * @param request request
     * @return domain
     */
    public static String getDomain(HttpServletRequest request) {
        String domain = request.getServerName();
        int index = domain.lastIndexOf(".");
        if(index == -1){
        	return domain;
        }
        index = domain.substring(0, index).lastIndexOf('.');
        if (index == -1) {
            return domain;
        }
        return domain.substring(index + 1);
    }
}