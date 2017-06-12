package com.renms.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renms.admin.dao.UserMapper;
import com.renms.admin.model.User;
import com.renms.admin.service.UserService;
import com.renms.common.constant.SysConstants;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> findAllUser(Integer pubid,int userType,int pageNum,String userName){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(pubid!=null){
			paramMap.put("pubid", pubid);
		}
		paramMap.put("userType", userType);
		paramMap.put("start", (pageNum-1)*SysConstants.pageSize);
		paramMap.put("pageSize", SysConstants.pageSize);
    	if(userName!=null&&!userName.equals("")){
    		paramMap.put("userName", "%"+userName+"%");
    	}
		
		return userMapper.findAllUser(paramMap);
	}
	
	@Override
    public int getTotalNum(Integer pubid,int userType, String userName) {
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	if(pubid!=null){
    		paramMap.put("pubid", pubid);
    	}
    	paramMap.put("userType", userType);
    	if(userName!=null&&!userName.equals("")){
    		paramMap.put("userName", "%"+userName+"%");
    	}
        return userMapper.getTotal(paramMap);
    }
	
	
	@Override
	public User findUserByLoginName(String loginName) {
		return userMapper.findUserByUpn(loginName);
	}
	
	
	@Override
	public void deleteUser(int userId){
		userMapper.deleteUserById(userId);
	}
	
	@Override
	public int saveUser(User user) {
		return userMapper.saveUser(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public void deleteUserById(int userId) {
		userMapper.deleteUserById(userId);
	}

	@Override
	public User findOneUser(int userId) {
		return userMapper.findOneUser(userId);
	}
	
}