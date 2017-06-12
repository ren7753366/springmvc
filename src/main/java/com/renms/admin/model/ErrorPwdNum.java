
package com.renms.admin.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: ErrorPwdNum.java
 * @Description: 一段时间内用户密码错误次数 
 * @author renms
 * @date 2015年9月15日 上午9:56:39 
 * @version 1.0
 */

public class ErrorPwdNum implements Serializable {
	private static final long serialVersionUID = 458846938512752546L;
	
	public static final int LIMIT_NUM = 10;
	//millisecond(一小时)
	private static final long EXPIRED = 3600000L;
	
	private long loginTime;
	
	private int loginNum;

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public int getLoginNum() {
		return loginNum;
	}
	
	public int getRemainingLoginNum() {
		return LIMIT_NUM-loginNum;
	}

	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}

	public static long getExpired() {
		return EXPIRED;
	}
	
	public static long getExpiredSecond() {
		return EXPIRED / 1000;
	}
	
	
}
