package indi.ray.miniSpring.aop.pointCut;

import indi.ray.miniSpring.core.utils.ArrayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdkRegexMethodPointCut extends AbstractRegexpMethodPointCut {
    private Pattern[] compiledPatterns;

    private Pattern[] compiledExcludedPatterns;

    /**
     * public method to compile an array of raw patterns
     *
     * @param rawPatterns
     * @return compiled patterns in array
     */
    private Pattern[] doCompilePatterns(String[] rawPatterns) {
        Pattern[] patterns = new Pattern[ArrayUtils.getLength(rawPatterns)];
        for (int i = 0; i < ArrayUtils.getLength(rawPatterns); i++) {
            Pattern pat = Pattern.compile(rawPatterns[i]);
            patterns[i] = pat;
        }
        return patterns;
    }

    @Override
    protected void initPatterns(String[] patterns) {
        this.compiledPatterns = doCompilePatterns(patterns);
    }

    @Override
    protected void initExcludedPatterns(String[] excludedPatterns) {
        this.compiledExcludedPatterns = doCompilePatterns(excludedPatterns);
    }

    @Override
    protected boolean matches(String str, int patIndex) {
        Matcher matcher = this.compiledPatterns[patIndex].matcher(str);
        return matcher.matches();
    }

    @Override
    protected boolean matchesExcluded(String str, int patIndex) {
        Matcher matcher = this.compiledExcludedPatterns[patIndex].matcher(str);
        return matcher.matches();
    }
}
