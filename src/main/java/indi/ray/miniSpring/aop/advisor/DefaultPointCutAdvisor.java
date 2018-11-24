package indi.ray.miniSpring.aop.advisor;

import indi.ray.miniSpring.aop.advise.Advice;
import indi.ray.miniSpring.aop.pointCut.PointCut;

public class DefaultPointCutAdvisor extends AbstractPointCutAdvisor {
    private PointCut pointCut = PointCut.TRUE;

    public DefaultPointCutAdvisor() {
    }

    public DefaultPointCutAdvisor(PointCut pointCut, Advice advice) {
        this.pointCut = pointCut;
        this.setAdvice(advice);
    }

    public DefaultPointCutAdvisor(Advice advice) {
        this.setAdvice(advice);
    }

    public void setPointCut(PointCut pointCut) {
        this.pointCut = pointCut;
    }

    public PointCut getPointCut() {
        return this.pointCut;
    }
}
