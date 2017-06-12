package com.renms.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.renms.admin.model.User;
import com.renms.admin.service.UserService;
import com.renms.common.constant.SysConstants;
import com.renms.common.util.SpringContextHolder;
import com.renms.common.util.SysIpUtil;
import com.renms.common.util.TicketUtil;

public class AuthFilter implements Filter {

//    private RedisValueOps redisValueOps = SpringContextHolder.getBean("redisValueOps");
//    
    private UserService userService = SpringContextHolder.getBean("userService");
	
    private static final Log log = LogFactory.getLog(AuthFilter.class);

    private static final String errorTip = "会话超时或没有权限使用该功能";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String ip = SysIpUtil.getIpAddr(request);
        String url = request.getServletPath();
        String realUrl = request.getRequestURL().toString();
        Map<String, String> map = TicketUtil.getAllCookies(request);
        String upn = map.get("upn");
        String path = request.getContextPath();

        // 格式化URL
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, url.indexOf("?"));
        }

        System.out.println("登陆名-----------"+upn);
        
        System.out.println("请求ip地址："+ip);
        
        // 未登录
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        response.setDateHeader("Expires", 0);
        if (upn == null || "".equals(upn.trim())) {
            if (url.indexOf("login") != -1 || url.indexOf("logout") != -1) {
                chain.doFilter(request, response);
                return;
            } else if (url.indexOf(".jsp") != -1) {
                log.info("-------------AuthIntercepter-[not login and not layout page(IP:" + ip + ")]----------------");
                response.sendRedirect(path + "/login.do?sto=error");
                return;
            } else {
                String xmlHttpRequest = request.getHeader("X-Requested-With");
                if ("XMLHttpRequest".equalsIgnoreCase(xmlHttpRequest)) {
                    try {
                        PrintWriter out = response.getWriter();
                        out.println(errorTip);
                        out.flush();
                        out.close();
                        log.info("-------------AuthIntercepter-[error request1(IP:" + ip + ")]----------------");
                        return;
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                } else {
                    response.sendRedirect(path + "/login.do?sto=error");
                    log.info("-------------AuthIntercepter-[error request2(IP:" + ip + ")]----------------");
                    return;
                }
            }
        } else {
            // 登录后
            // 不检查jsp页面
            // 不检查注销
        	
        	System.out.println("url==========="+url);
        	
            if (url.indexOf("login") != -1 || url.indexOf("logout") != -1 || url.indexOf("toAuth") != -1 ||url.indexOf("404") != -1 ||url.indexOf("error") != -1) {
            	request.getSession(true).removeAttribute(SysConstants.SESSION_USER);
                chain.doFilter(request, response);
                return;
            } else {
            	
                User user = (User) request.getSession(true).getAttribute(SysConstants.SESSION_USER);
                if(user==null){
                	user = userService.findUserByLoginName(upn);
                }
                if(user!=null){
                	//用户未授权，无权访问，跳转到错误页面
                	if((user.getUserStatus()==0||user.getUserStatus()==1)){
                		TicketUtil.deleteTicket(request, response);
                		response.sendRedirect(path + "/error.jsp?errorType=2");
                		return;
                	}
                	
                	//TODO：验证用户是否有权限请求连接

                	
                	//验证用户登陆是否合法
                	if(TicketUtil.verifyTicket(request)){
                		System.out.println("-------验证权限是否合法------"+url);
                    	chain.doFilter(request, response);
                        return;
                	}else{
                		TicketUtil.deleteTicket(request, response);
    					response.sendRedirect(path + "/error.jsp?errorType=1");
                		return;
                	}
                }else{
                	//用户不存在,清除cookie，重定向到登陆页面
                	TicketUtil.deleteTicket(request, response);
                	response.sendRedirect(path + "/login.do?sto=error");
                }
            }
        }
        return;
    }

    @Override
    public void destroy() {

    }

}
