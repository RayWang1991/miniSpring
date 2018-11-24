package indi.ray.miniSpring.aop.pointCut;


import indi.ray.miniSpring.core.utils.StringUtils;

import java.lang.reflect.Method;

public abstract class AbstractRegexpMethodPointCut extends StaticMethodMatcherPointCut {
    private String[] patterns = new String[0];

    private String[] excludedPatterns = new String[0];

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String... patterns) {
        this.patterns = new String[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            this.patterns[i] = StringUtils.trimWhiteSpace(patterns[i]);
        }
        this.initPatterns(this.patterns);
    }

    public String[] getExcludedPatterns() {
        return excludedPatterns;
    }

    public void setExcludedPatterns(String... excludedPatterns) {
        this.excludedPatterns = new String[excludedPatterns.length];
        for (int i = 0; i < excludedPatterns.length; i++) {
            this.excludedPatterns[i] = StringUtils.trimWhiteSpace(excludedPatterns[i]);
        }
        this.initExcludedPatterns(this.excludedPatterns);
    }

    protected abstract void initPatterns(String[] patterns);

    protected abstract void initExcludedPatterns(String[] excludedPatterns);

    @Override
    public boolean matches(Method method) {
        boolean match = false;
        String methodName = method.getName();
        for (int i = 0; i < this.patterns.length; i++) {
            if (matches(methodName, i)) {
                match = true;
                break;
            }
        }
        if (match) {
            for (int i = 0; i < this.excludedPatterns.length; i++) {
                if (matchesExcluded(methodName, i)) {
                    match = false;
                    break;
                }
            }
        }
        return match;
    }

    protected abstract boolean matches(String str, int patIndex);

    protected abstract boolean matchesExcluded(String str, int patIndex);

}
