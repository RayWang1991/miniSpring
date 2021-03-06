package com.ray.miniSpring.aop.functionTest.proxySetUp;

import com.ray.miniSpring.aop.advise.MethodInvocation;

import java.util.List;

public class MethodNameDoubleRecordInterceptor extends AbstractMethodNameRecorder {
    public MethodNameDoubleRecordInterceptor(List<String> recorder) {
        super(recorder);
    }

    @Override
    protected String genRecord(MethodInvocation invocation) {
        String methodName = invocation.getMethod().getName();
        return methodName + methodName;
    }
}
