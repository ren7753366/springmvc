package com.renms.admin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.renms.admin.model.User;

/**
 * 
 * @ClassName: UserMapper.java
 * @Description: 用户信息dao类
 * @author renms
 * @date 2015年9月15日 下午2:35:59 
 * @version 1.0
 */
@Component
public interface UserMapper {

	public List<User> findAllUser(Map<String,Object> map);
	public int getTotal(Map<String,Object> map);
	
	public int saveUser(User user);
	public void updateUser(User user);
	public void deleteUserById(int userId);
	public User findOneUser(int userId);
	public User findUserByUpn(String upn);
	
}
