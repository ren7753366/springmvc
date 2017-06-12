package com.renms.admin.service;

import java.util.List;

import com.renms.admin.model.User;


public interface UserService {

	public List<User> findAllUser(Integer pubid,int userType,int pageNum,String userName);
	
    public int getTotalNum(Integer pubid,int userType, String userName);
	
	public User findUserByLoginName(String loginName);
	
	public void deleteUser(int userId);
	public int saveUser(User user);
	public void updateUser(User user);
	public void deleteUserById(int userId);
	public User findOneUser(int userId);
}
