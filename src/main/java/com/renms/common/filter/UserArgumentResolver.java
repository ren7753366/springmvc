package com.renms.common.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.renms.admin.model.User;
import com.renms.admin.service.UserService;
import com.renms.common.constant.SysConstants;
import com.renms.common.util.TicketUtil;

/**
 * Spring MVC handler method argument resolver, configure using [mvc:argument-resolvers].
 * @author renms
 * @date 2015/9/16
 */
public class UserArgumentResolver implements WebArgumentResolver {

   	@Autowired
   	protected UserService userService;

    protected boolean NULL(Object...objects) {
   		for (Object object : objects) {
   			if(object == null) {
   				return true;
   			} else if(StringUtils.isBlank(object.toString())) {
   				return true;
   			}
   		}
   		return false;
   	}

    protected User obtainUser(HttpServletRequest request, String userId) {
   		if(NULL(userId)) {
   			userId = TicketUtil.getCookieValue(request, SysConstants.COOKIE_UC_SESSIONID);
   		}
   		if(!NULL(userId)) {
   			User result = (User) request.getSession(true).getAttribute(SysConstants.SESSION_USER);
   			if(null != result && userId.equals(result.getId())) {
   				return result;
   			}
   			
   			System.out.println("UserArgumentResolver   userId===================="+userId);
   			
   			try {
   				User user = userService.findOneUser(Integer.parseInt(userId));
   				
   				if(user.getPubid()!=null&&user.getPubid()==0){
   					String pubid = TicketUtil.getCookieValue(request, "pubid");
   					System.out.println("UserArgumentResolver   pubid===================="+pubid);
   					if(pubid!=null){
   						user.setPubid(Integer.parseInt(pubid));
   					}
   				}
   				request.getSession(true).setAttribute(SysConstants.SESSION_USER, user);
   				return user;
   			} catch (Exception e) {
   				e.printStackTrace();
   			}
   		}
   		return null;
   	}

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        //throw exceptions or return UNRESOLVED will mark this resolve not support this type, and will never try again.
    	System.out.println("------整理user参数----------");
    	if (methodParameter.getParameterType().equals(User.class)) {
            User user = obtainUser((HttpServletRequest) webRequest.getNativeRequest(), "");
            System.out.println("用户信息："+user.toString());
            return user;
        }
        return UNRESOLVED;
    }
}
