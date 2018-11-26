package indi.ray.miniSpring.aop.functionTest.proxySetUp;

import indi.ray.miniSpring.aop.advise.MethodInterceptor;
import indi.ray.miniSpring.aop.advise.MethodInvocation;

import java.util.List;

public class MethodNameRecordInterceptor extends AbstractMethodNameRecorder {
    public MethodNameRecordInterceptor(List<String> recorder) {
        super(recorder);
    }

    @Override
    protected String genRecord(MethodInvocation invocation) {
        String methodName = invocation.getMethod().getName();
        return methodName;
    }
}
