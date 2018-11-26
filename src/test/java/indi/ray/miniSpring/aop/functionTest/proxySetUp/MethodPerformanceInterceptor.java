package indi.ray.miniSpring.aop.functionTest.proxySetUp;

import indi.ray.miniSpring.aop.advise.MethodInterceptor;
import indi.ray.miniSpring.aop.advise.MethodInvocation;
import org.apache.log4j.Logger;

public class MethodPerformanceInterceptor implements MethodInterceptor {
    private static final Logger logger = Logger.getLogger(MethodPerformanceInterceptor.class);

    private long startTime;

    private void start() {
        this.startTime = System.currentTimeMillis();
    }

    private void end(MethodInvocation invocation) {
        long endTime = System.currentTimeMillis();
        logger.info(invocation.getMethod().getName() + " has executed " + (endTime - startTime) + "ms");
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            start();
            return invocation.proceed();
        } finally {
            end(invocation);
        }
    }
}
