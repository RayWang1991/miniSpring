package com.ray.miniSpring.aop.functionTest.proxySetUp;

import com.ray.miniSpring.aop.advise.MethodInterceptor;
import com.ray.miniSpring.aop.advise.MethodInvocation;
import com.ray.miniSpring.core.utils.ArrayUtils;
import org.apache.log4j.Logger;

public class MethodLoggerInterceptor implements MethodInterceptor {
    private static final Logger logger = Logger.getLogger(MethodLoggerInterceptor.class);

    private String genMethodInvocationInfo(MethodInvocation invocation) {
        return invocation.getMethod().getName() + " " + ArrayUtils.genStringForObjArray(invocation.getArguments());
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            logger.info(genMethodInvocationInfo(invocation) + "start");
            return invocation.proceed();
        } finally {
            logger.info(genMethodInvocationInfo(invocation) + "end");
        }
    }
}
