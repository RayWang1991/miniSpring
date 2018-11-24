package indi.ray.miniSpring.aop.advisor;

import indi.ray.miniSpring.aop.advise.Advice;
import indi.ray.miniSpring.aop.pointCut.PointCut;
import indi.ray.miniSpring.core.API.Ordered;

public abstract class AbstractPointCutAdvisor implements PointCutAdvisor, Ordered {

    private Integer order;

    private Advice advice;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Advice getAdvice() {
        return advice;
    }

    public int getOrder() {
        if (this.order != null) {
            return this.order;
        }
        Advice advice = this.getAdvice();
        if (advice instanceof Ordered) {
            return ((Ordered) advice).getOrder();
        }
        return Ordered.LOWEST_ORDER;
    }
}
