package indi.ray.miniSpring.aop.advisor;

import indi.ray.miniSpring.aop.advise.Advice;
import indi.ray.miniSpring.aop.pointCut.NamedMatchMethodPointCut;
import indi.ray.miniSpring.aop.pointCut.PointCut;

public class NamedMatchMethodPointCutAdvisor extends AbstractPointCutAdvisor {

    private NamedMatchMethodPointCut matchMethodPointCut = new NamedMatchMethodPointCut();

    public void setMappedNames(String... names) {
        this.matchMethodPointCut.setMappedNames(names);
    }

    public NamedMatchMethodPointCut addMappedName(String mappedName) {
        return this.matchMethodPointCut.addMappedName(mappedName);
    }

    public void setMatchMethodPointCut(NamedMatchMethodPointCut matchMethodPointCut) {
        this.matchMethodPointCut = matchMethodPointCut;
    }

    public PointCut getPointCut() {
        return this.matchMethodPointCut;
    }

}
