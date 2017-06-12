package com.renms.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renms.admin.model.User;
import com.renms.admin.service.UserService;
import com.renms.common.constant.SysConstants;
import com.renms.common.resutl.Result;
import com.renms.common.util.SysIpUtil;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/queryList.do")
    @ResponseBody
    public Object findAllUser(User user,@RequestParam(value = "pageNum", required = false)Integer pageNum,
    		@RequestParam(value = "userName", required = false)String userName) throws Exception {
    	
    	Integer pubid = user.getPubid();
    	//如果是超级管理员
    	if(user.getUserType()==1){
    		pubid = null;
    	}
    	
        List<User> list = userService.findAllUser(pubid,user.getUserType(),pageNum,userName);
        
        int total = userService.getTotalNum(pubid,user.getUserType(),userName);
    	Map<String,Object> map = new HashMap<String,Object>();
    	System.out.println("总数----"+total);
    	map.put("pageNum", pageNum);
    	map.put("total", Math.ceil((total+SysConstants.pageSize-1)/SysConstants.pageSize));
    	map.put("rows", list);
        
        Result result = new Result();
		result.setSuccess(true);
		result.setObj(map);
		return result;
    }
    
    @RequestMapping("/authOper.do")
    @ResponseBody
    public Object authOper(@RequestParam(value = "userId", required = true)int userId,@RequestParam(value = "userStatus", required = true)int userStatus) throws Exception {
    	
    	User user = new User();
    	user.setId(userId);
    	user.setUserStatus(userStatus);
        userService.updateUser(user);

        String msg = "停权成功！";
        if(userStatus==2){
        	msg = "授权成功！";	
        }
        
        Result result = new Result();
		result.setMsg(msg);
		result.setSuccess(true);
		return result;
    }

    @RequestMapping("/toAuth.do")
    @ResponseBody
    public Object update(User user,HttpServletRequest request,@RequestParam(value = "pubid", required = true)int pubid
    		,@RequestParam(value = "userType", required = true)int userType) throws Exception {
    	
    	System.out.println(pubid+"-----------"+userType);
    	
    	String visitorIp = SysIpUtil.getIpAddr(request);
    	user.setUserIp(visitorIp);
    	user.setPubid(pubid);
    	user.setUserType(userType);
    	user.setUserStatus(1);
        userService.updateUser(user);
        Result result = new Result();
		result.setMsg("申请授权成功，请等待管理员审批！");
		result.setSuccess(true);
		return result;
    }
    
    @RequestMapping("/delete.do")
    @ResponseBody
    public Object delete(int userId) throws Exception {
        userService.deleteUser(userId);
        Result result = new Result();
		result.setMsg("删除成功！");
		result.setSuccess(true);
		return result;
    }
    
    @RequestMapping("/update.do")
    @ResponseBody
    public Object update(@RequestParam(value = "id", required = true)int id
    		,@RequestParam(value = "pubid", required = true)int pubid
    		,@RequestParam(value = "userType", required = true)int userType
    		,@RequestParam(value = "userIp", required = true)String userIp) throws Exception {
    	
    	User user = new User();
    	user.setId(id);
    	user.setPubid(pubid);
    	user.setUserType(userType);
    	user.setUserIp(userIp);
    	
    	userService.updateUser(user);
        Result result = new Result();
        result.setMsg("修改成功！");
		result.setSuccess(true);
		return result;
    }
}
