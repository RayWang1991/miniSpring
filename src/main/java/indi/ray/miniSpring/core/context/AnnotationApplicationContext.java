package indi.ray.miniSpring.core.context;

import indi.ray.miniSpring.core.annotations.ComponentScan;
import indi.ray.miniSpring.core.beans.definition.BeanDefinition;
import indi.ray.miniSpring.core.context.scanner.AnnotationBeanDefinitionsProvider;
import indi.ray.miniSpring.core.utils.ArrayUtils;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

public class AnnotationApplicationContext extends AbstractApplicationContext {
    private static final Logger logger = Logger.getLogger(AnnotationApplicationContext.class);

    private AnnotationBeanDefinitionsProvider beanDefinitionsProvider = new AnnotationBeanDefinitionsProvider();

    private Class<?> componentScanClass;

    public AnnotationApplicationContext(Class<?> componentScanClass) {
        this.componentScanClass = componentScanClass;
        AssertUtils.assertTrue(componentScanClass.isAnnotationPresent(ComponentScan.class), "需要标注@ComponentScan");
        refresh();
    }



    @Override
    protected void prepareRefresh() {
        clear();
    }

    @Override
    protected void scanBeanDefinitions() {
        // scan all bean definitions
        ComponentScan componentScan = this.componentScanClass.getAnnotation(ComponentScan.class);
        int packagesLength = ArrayUtils.getLength(componentScan.basePackages());
        int classesLength = ArrayUtils.getLength(componentScan.basePackageClasses());
        String[] basePaths = new String[packagesLength + classesLength];
        System.arraycopy(componentScan.basePackages(), 0, basePaths, 0, packagesLength);
        for (int i = 0; i < classesLength; i++) {
            basePaths[i + packagesLength] = componentScan.basePackageClasses()[i].getPackage().getName().
                    replaceAll("\\.", File.separator);
        }

        // load and archive bean definitions
        List<BeanDefinition> beanDefinitions = this.beanDefinitionsProvider.findCandidateComponents(basePaths);
        loadBeanDefinitions(beanDefinitions);
    }

    @Override
    protected void postProcessBeanFactory() {
        // todo
    }

    @Override
    protected void invokeBeanFactoryPostProcessors() {
        // todo
    }

    @Override
    protected void finishBeanFactoryInitialization() {
        loadAllSingletons();
    }


}
