package indi.ray.miniSpring.aop.advisor;

import indi.ray.miniSpring.aop.pointCut.PointCut;

public interface PointCutAdvisor extends Advisor {

    /**
     * Get the pointcut to drive the advice
     *
     * @return
     */
    PointCut getPointCut();
}
