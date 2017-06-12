package com.renms.common.intercepter;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.renms.common.util.TicketUtil;
import com.renms.common.util.Tools;

public class LogInterceptor extends HandlerInterceptorAdapter {

    private Logger log = Logger.getLogger("operateLog");

    /**
     * 在业务处理器处理请求之前被调用
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // logOfBefore(request);
        return true;
    }

    // 在业务处理器处理请求执行完成后,生成视图之前执行的动作
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logOfAfter(request, modelAndView == null ? "" : modelAndView.toString());
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @SuppressWarnings("unchecked")
    public void logOfBefore(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        Map<String, String> map = TicketUtil.getAllCookies((HttpServletRequest) request);
        sb.append("=====[" + map.get("upn")).append(":" + Tools.getRemoteIp(request)).append("][" + request.getServletPath() + "]").append("[");
        Map<String, String> paramMap = request.getParameterMap();
        Set<String> keys = paramMap.keySet();
        for (String paramName : keys) {
            String paramValue = request.getParameter(paramName).trim();
            sb.append(paramName + ":" + paramValue + ",");
        }
        sb.append("]");
        log.info(sb.toString());
    }

    @SuppressWarnings("unchecked")
    public void logOfAfter(HttpServletRequest request, String json) {
        Map<String, String> map = TicketUtil.getAllCookies((HttpServletRequest) request);
        StringBuffer sb = new StringBuffer();
        sb.append("=====[" + map.get("upn")).append(":" + Tools.getRemoteIp(request)).append("][" + request.getServletPath() + "]").append("[");
        Map<String, String> paramMap = request.getParameterMap();
        Set<String> keys = paramMap.keySet();
        for (String paramName : keys) {
            String paramValue = request.getParameter(paramName).trim();
            sb.append(paramName + ":" + paramValue + ",");
        }
        sb.append("]");
        if (json == null || json.indexOf("rows") == -1) {
            sb.append("[" + json + "]");
        }
        log.info(sb.toString());
    }

    @SuppressWarnings("unchecked")
    public String getReqParams(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        Map<String, String> paramMap = request.getParameterMap();
        Set<String> keys = paramMap.keySet();
        for (String paramName : keys) {
            String paramValue = request.getParameter(paramName).trim();
            sb.append(paramName + ":" + paramValue + ",");
        }
        sb.append("]");
        return sb.toString();
    }

}