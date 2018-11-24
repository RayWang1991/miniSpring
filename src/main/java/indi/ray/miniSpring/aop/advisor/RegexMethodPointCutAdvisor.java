package indi.ray.miniSpring.aop.advisor;

import indi.ray.miniSpring.aop.pointCut.JdkRegexMethodPointCut;
import indi.ray.miniSpring.aop.pointCut.PointCut;

public class RegexMethodPointCutAdvisor extends AbstractPointCutAdvisor {

    JdkRegexMethodPointCut methodPointCut = new JdkRegexMethodPointCut();

    public void setPatterns(String... patterns) {
        this.methodPointCut.setPatterns(patterns);
    }

    public void setExcludedPatterns(String... excludedPatterns) {
        this.methodPointCut.setExcludedPatterns(excludedPatterns);
    }

    public PointCut getPointCut() {
        return this.methodPointCut;
    }
}
