package indi.ray.miniSpring.aop.advise;

public interface MethodInterceptor extends Interceptor {

    /**
     * Interceptor 通过实现该方法来在调用前后插入额外逻辑。
     * 良好的实现会调用{@link JoinPoint#proceed()}
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    Object invoke(MethodInvocation invocation) throws Throwable;

}
