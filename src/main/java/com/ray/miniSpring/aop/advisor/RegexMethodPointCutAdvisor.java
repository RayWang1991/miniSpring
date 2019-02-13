package com.ray.miniSpring.aop.advisor;

import com.ray.miniSpring.aop.pointCut.PointCut;
import com.ray.miniSpring.aop.pointCut.JdkRegexMethodPointCut;

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
