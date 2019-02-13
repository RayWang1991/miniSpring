package com.ray.miniSpring.aop.functionTest.proxySetUp;

import com.ray.miniSpring.aop.advise.MethodInterceptor;
import com.ray.miniSpring.aop.advise.MethodInvocation;
import com.ray.miniSpring.core.utils.AssertUtils;

import java.util.List;

public abstract class AbstractMethodNameRecorder implements MethodInterceptor {
    List<String> recorder;

    public AbstractMethodNameRecorder(List<String> recorder) {
        AssertUtils.assertNotNull(recorder, "recorder should not be null");
        this.recorder = recorder;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (shouldRecord(invocation)) {
            recorder.add(genRecord(invocation));
        }
        return invocation.proceed();
    }

    protected boolean shouldRecord(MethodInvocation invocation) {
        return !invocation.getMethod().getName().equals("toString");
    }

    protected abstract String genRecord(MethodInvocation invocation);
}
