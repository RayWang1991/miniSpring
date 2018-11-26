package indi.ray.miniSpring.core.context;

import indi.ray.miniSpring.core.beans.definition.BeanDefinition;
import indi.ray.miniSpring.core.beans.definition.RuntimeBeanConstructorResolver;
import indi.ray.miniSpring.core.context.creator.DefaultBeanCreator;
import indi.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider;

import java.util.List;

public class XmlApplicationContext extends AbstractApplicationContext {

    private XmlBeanDefinitionProvider beanDefinitionProvider = new XmlBeanDefinitionProvider();

    private RuntimeBeanConstructorResolver runtimeBeanConstructorResolver;

    private String[] paths;

    public XmlApplicationContext(String... paths) {
        this.paths = paths;
        refresh();
    }

    @Override
    protected void prepareRefresh() {
        clear();
        runtimeBeanConstructorResolver = new RuntimeBeanConstructorResolver(beanDefinitionProvider.getStaticValueRefResolver());
        runtimeBeanConstructorResolver.setBeanFactory(this);
        ((DefaultBeanCreator) beanCreator).setRuntimeBeanConstructorResolver(runtimeBeanConstructorResolver);
    }

    @Override
    protected void scanBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = this.beanDefinitionProvider.findBeanDefinitions(paths);
        // load and archive bean definitions
        loadBeanDefinitions(beanDefinitions);
    }

    @Override
    protected void postProcessBeanFactory() {
        //todo
    }

    @Override
    protected void invokeBeanFactoryPostProcessors() {
    }

    @Override
    protected void finishBeanFactoryInitialization() {
        loadAllSingletons();
    }
}
