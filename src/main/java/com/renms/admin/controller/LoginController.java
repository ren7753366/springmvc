package com.renms.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.renms.admin.model.User;
import com.renms.admin.service.UserService;
import com.renms.common.util.TicketUtil;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login.do")
	public String login_temp(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "userName", required = false)String userName){
		
		if(userName==null){
			return "redirect:login_temp.jsp";
		}
		User user1 = userService.findUserByLoginName(userName);
		
		TicketUtil.createCookies(request, response, user1);
		request.setAttribute("upn", user1.getUpn());
		if(user1.getUserStatus()!=2){
			//用户未激活页面
			return "redirect:main.jsp";
		}
		return "redirect:main.jsp";
	}
	
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		TicketUtil.deleteTicket(request, response);
		return "redirect:login.do";
	}
	
}
