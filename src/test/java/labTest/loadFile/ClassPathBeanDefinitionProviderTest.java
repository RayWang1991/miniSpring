package labTest.loadFile;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.Qualifier;
import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.beans.exception.BeansException;
import com.ray.miniSpring.core.context.scanner.AnnotationBeanDefinitionsProvider;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.utils.Executable;
import org.apache.log4j.Logger;
import org.junit.Test;


@Component
public class ClassPathBeanDefinitionProviderTest {
    private static final Logger logger = Logger.getLogger(ClassPathBeanDefinitionProviderTest.class);

    protected AnnotationBeanDefinitionsProvider provider = new AnnotationBeanDefinitionsProvider();

    @Autowired
    @Qualifier("hahaha")
    private Integer integer;

    @Test
    public void testBeanFieldScan() {
        BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassPathBeanDefinitionProviderTest.class);
        logger.info(beanDefinition);
    }

    @Test
    public void testBeanConstructorScanWithOneAutowired() {
        BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassWithOneAutowireConstructor.class);
        logger.info(beanDefinition);
    }

    @Test
    public void testBeanConstructorScanWithMoreAutowire() {
        AssertUtils.assertThrows(BeansException.class, new Executable() {
            public void execute() throws Exception {
                BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassWithMoreAutowiredConstructor.class);
                logger.info(beanDefinition);
            }
        });
    }

    @Test
    public void testWithoutAutowire(){
        BeanDefinition beanDefinition = provider.generateBeanDefinition(ClassWithNoAutowiredConstructor.class);
        logger.info(beanDefinition);
    }
}
