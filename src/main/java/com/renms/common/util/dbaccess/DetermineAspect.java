package com.renms.common.util.dbaccess;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.renms.common.annotation.DataSourceFile;
import com.renms.common.annotation.DataSourceMethod;

/**
 * @ClassName: DetermineAspect 
 * @Description: 切换数据源
 * @author renms
 * @date 2015年12月10日 下午9:01:42 
 */
@Aspect
@lombok.extern.log4j.Log4j
public class DetermineAspect {
	@Pointcut("within(com.renms.*.service.impl.*)")
	public void withController() {}

	@Before("withController()")
	public void beforeAdvice(JoinPoint jp) {
		if(log.isDebugEnabled()) {
			log.debug("Reset db router before " + jp.getSignature().getDeclaringType().getSimpleName() + "." + jp.getSignature().getName() + "()");
		}
		@SuppressWarnings("unchecked")
		DataSourceMethod datasourceMethod = ((MethodSignature)jp.getSignature()).getMethod().getAnnotation(DataSourceMethod.class);
		DataSourceFile datasourceFile = ((MethodSignature)jp.getSignature()).getMethod().getDeclaringClass().getAnnotation(DataSourceFile.class);
		
		if(datasourceMethod!=null){
			DataSourceHolder.determineDS(datasourceMethod.value());
		}else if(datasourceFile!=null){
			DataSourceHolder.determineDS(datasourceFile.value());
		}else{
			DataSourceHolder.determineDefault();
		}
	}

}
