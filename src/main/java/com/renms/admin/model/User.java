package com.renms.admin.model;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class User {
	private int id;
	private Integer pubid;
	private String userIp;
	private Integer userType;
	private Integer userStatus;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String username;
	private String displayName;
	private String upn;
	private String mail;
	private String employeeID;
	private String department;
	private String title; 
	private String lang;
	private String pubName;
	
	private String createTimeStr;
	private String userTypeStr;
	private String userStatusStr;
	
}
