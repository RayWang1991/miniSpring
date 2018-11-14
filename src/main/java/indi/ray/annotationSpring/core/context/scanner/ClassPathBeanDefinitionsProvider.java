package indi.ray.annotationSpring.core.context.scanner;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.annotations.DependsOn;
import indi.ray.annotationSpring.core.annotations.Primary;
import indi.ray.annotationSpring.core.annotations.Qualifier;
import indi.ray.annotationSpring.core.annotations.Scope;
import indi.ray.annotationSpring.core.beans.definition.AnnotationBeanDefinition;
import indi.ray.annotationSpring.core.beans.definition.BeanConstructor;
import indi.ray.annotationSpring.core.beans.definition.BeanConstructorImpl;
import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.definition.BeanField;
import indi.ray.annotationSpring.core.beans.definition.BeanFieldImpl;
import indi.ray.annotationSpring.core.beans.definition.DependOnRelation;
import indi.ray.annotationSpring.core.beans.definition.DependOnRelationImpl;
import indi.ray.annotationSpring.core.beans.definition.ScopeEnum;
import indi.ray.annotationSpring.core.beans.exception.BeansException;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.BeanDefinitionUtils;
import indi.ray.annotationSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ClassPathBeanDefinitionsProvider provide 2 functions
 * 1. Scan the path of the packages, get the class names
 * 2. Generate BeanDefinitions of the class
 */
public class ClassPathBeanDefinitionsProvider {
    private static final Logger logger     = Logger.getLogger(ClassPathBeanDefinitionsProvider.class);
    private static final String DEL_SYMBOL = "&DEL&";

    /**
     * given one or more basePackage path, find all candidate beans
     *
     * @param basePackages the package paths to be Scanned
     * @return the set of bean definitions of the candidates
     */
    public Set<BeanDefinition> findCandidateComponents(String... basePackages) {
        // compress packages
        String[] compressedPackages = compressPackages(basePackages);

        // find classNames
        Set<String> classNames = findClassNamesForGivenPaths(compressedPackages);

        // generate bean definitions
        Set<BeanDefinition> beanDefinitions = generateBeanDefinitions(classNames);

        return beanDefinitions;
    }

    /**
     * given a collection of class names, find the candidates among them and
     * generate bean definitions for them
     *
     * @param classNames the class names
     * @return {@code Set} of {@code BeanDefinitions}
     */
    public Set<BeanDefinition> generateBeanDefinitions(Collection<String> classNames) {
        Set<BeanDefinition> bds = new HashSet<BeanDefinition>();
        for (String className : classNames) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                // should not be
                logger.error(e);
                continue;
            }
            BeanDefinition bd = generateBeanDefinition(clazz);
            if (bd != null) {
                bds.add(bd);
            }
        }
        return bds;
    }

    /**
     * given a certain {@code class}, generate the {@code BeanDefinition} of the class
     *
     * @param clazz
     * @return
     */
    public BeanDefinition generateBeanDefinition(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Component.class)) {
            return null;
        }

        if (clazz.isAnnotation() || clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }

        AnnotationBeanDefinition bd = new AnnotationBeanDefinition();

        bd.setBeanClass(clazz);

        Component component = clazz.getAnnotation(Component.class);
        if (StringUtils.isNotBlank(component.value())) {
            bd.setBeanName(component.value());
        } else {
            bd.setBeanName(StringUtils.unCapitalize(clazz.getSimpleName()));
        }
        bd.setLazyInit(component.lazyInit());

        Scope scope = clazz.getAnnotation(Scope.class);
        if (scope != null && scope.value() == ScopeEnum.PROTOTYPE) {
            bd.setScopeEnum(ScopeEnum.PROTOTYPE);
        } else {
            bd.setScopeEnum(ScopeEnum.SINGLETON);
        }

        if (clazz.getAnnotation(Primary.class) != null) {
            bd.setPrimary(true);
        } else {
            bd.setPrimary(false);
        }

        // depends on
        DependsOn dependsOn = clazz.getAnnotation(DependsOn.class);
        if (dependsOn != null) {
            bd.setDependsOnBeanNames(dependsOn.value());
        }

        // analyze field dependencies
        List<BeanField> fieldList = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                String name = field.getName();
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (qualifier != null && StringUtils.isNotBlank(qualifier.value())) {
                    name = qualifier.value();
                }
                BeanField beanField = new BeanFieldImpl(field, field.getType(), name, autowired.required());
                fieldList.add(beanField);
            }
        }
        bd.setFields(fieldList.toArray(new BeanField[fieldList.size()]));

        // analyze constructor dependencies
        Constructor[] cts = clazz.getDeclaredConstructors();
        Constructor constructor = null;
        boolean found = false;
        for (Constructor ct : cts) {
            if (ct.isAnnotationPresent(Autowired.class)) {
                if (found) {
                    throw new BeansException("只允许有一个构造器标注为@Autowired");
                } else {
                    found = true;
                    constructor = ct;
                }
            }
        }

        if (constructor != null) {
            bd.setConstructor(buildBeanConstructor(constructor, false));
        } else {
            try {
                Constructor<?> defaultCt = clazz.getDeclaredConstructor();
                bd.setConstructor(buildBeanConstructor(defaultCt, true));
            } catch (Exception e) {
                logger.error(e);
                throw new BeansException("类必须有标注为Autowired的构造器或者无参数构造器");
            }
        }

        return BeanDefinitionUtils.validateAndReturn(bd, logger);
    }

    /**
     * Build bean constructor wrapper with certain constructor for bean.
     * There are 2 cases: a constructor with @Autowried annotation or a default no-arg
     * constructor
     *
     * @param constructor          the constructor for the bean
     * @param isDefaultConstructor the constructor wrapper
     * @return
     */
    public BeanConstructor buildBeanConstructor(Constructor<?> constructor, boolean isDefaultConstructor) {
        AssertUtils.assertTrue(Modifier.isPublic(constructor.getModifiers()), constructor + "构造器必须为public");
        if (isDefaultConstructor) {
            return new BeanConstructorImpl(constructor, null);
        }
        Class<?>[] parameterizedTypes = constructor.getParameterTypes();
        Annotation[][] annotations = constructor.getParameterAnnotations();
        DependOnRelation[] dependOnRelations = new DependOnRelation[parameterizedTypes.length];
        for (int i = 0; i < parameterizedTypes.length; i++) {
            Class<?> clazz = parameterizedTypes[i];
            String name = StringUtils.unCapitalize(clazz.getSimpleName());
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof Qualifier) {
                    Qualifier qualifier = (Qualifier) annotation;
                    if (StringUtils.isNotBlank(qualifier.value())) {
                        name = qualifier.value();
                    }
                }
            }
            dependOnRelations[i] = new DependOnRelationImpl(clazz, name, true);
        }
        return new BeanConstructorImpl(constructor, dependOnRelations);
    }

    /**
     * Compress packages in order to reduce the search work.
     * For example, A/B/C path contains A/B/C/D path. In this case,
     * the A/B/C/D path is eliminated
     *
     * @param origPackages
     * @return the compressed package paths
     */
    public String[] compressPackages(String[] origPackages) {
        String[] packages = origPackages.clone();
        int del = 0;
        for (int i = 0; i < packages.length; i++) {
            String stri = packages[i];
            for (int j = i + 1; j < packages.length; j++) {
                String strj = packages[j];
                if (strj.equals(DEL_SYMBOL)) {
                    continue;
                }
                if (stri.startsWith(strj)) {
                    // strj to be deleted
                    packages[j] = DEL_SYMBOL;
                    del++;
                } else if (strj.startsWith(stri)) {
                    // stri to be deleted
                    packages[i] = DEL_SYMBOL;
                    del++;
                    break;
                }
            }
        }
        String[] compressedPackages = new String[packages.length - del];
        int index = 0;
        for (String packagePath : packages) {
            if (!packagePath.equals(DEL_SYMBOL)) {
                compressedPackages[index++] = packagePath;
            }
        }
        return compressedPackages;
    }

    /**
     * Given one or more base package paths, find all class names in them
     *
     * @param basePaths the package paths
     * @return the class name in them
     */
    private Set<String> findClassNamesForGivenPaths(String... basePaths) {
//        System.out.println(System.getProperties().getProperty("java.class.path"));
        Set<String> result = new HashSet<String>();
        for (String basePackagePath : basePaths) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = null;
            try {
                resources = classLoader.getResources(basePackagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (resources == null || !resources.hasMoreElements()) {
                logger.debug("no resource loaded in " + basePackagePath);
                continue;
            }
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                logger.debug("resource:" + resource);
                logger.debug("resource path:" + resource);
                String protocol = resource.getProtocol();
                logger.debug(protocol);
                if (protocol.equals("file")) {
                    File file = new File(resource.getPath());
                    searchClassNamesWithFile(file,
                            findPrefixForFile(basePackagePath, file), result);
                } else if (protocol.equals("jar")) {
                    try {
                        JarFile jarFile = ((JarURLConnection) resource.openConnection()).getJarFile();
                        searchClassNamesWithJar(jarFile, basePackagePath.replaceAll(File.pathSeparator, "."), true, result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            logger.debug(result);
        }
        return result;
    }

    /**
     * Search class names in a jar file
     *
     * @param jarFile
     * @param prefixWithSlash
     * @param checkPrefix
     * @param result
     */
    private void searchClassNamesWithJar(JarFile jarFile, String prefixWithSlash, boolean checkPrefix, Set<String> result) {
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            logger.debug("jarEntry:" + name);
            if (name.endsWith(".class")) {
                if (!checkPrefix || name.startsWith(prefixWithSlash)) {
                    result.add(name);
                }
            }
        }
    }

    /**
     * Search class names in a normal file
     *
     * @param rootFile
     * @param prefixWithDot
     * @param result
     */
    private void searchClassNamesWithFile(File rootFile, String prefixWithDot, Set<String> result) {
        if (!rootFile.canRead()) {
            logger.debug("file can't read:" + rootFile);
            return;
        }
        String name = rootFile.getName();
        if (rootFile.isDirectory()) {
            for (File file : rootFile.listFiles()) {
                searchClassNamesWithFile(file, prefixWithDot + "." + rootFile.getName(), result);
            }
        } else if (name.endsWith(".class")) {
            result.add(prefixWithDot + "." + name.substring(0, name.length() - 6));
        } else if (name.endsWith(".jar")) {
            try {
                searchClassNamesWithJar(new JarFile(rootFile), "", false, result);
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    private String findPrefixForFile(String basePath, File file) {
        String basePathWithDot = basePath.replaceAll(File.separator, ".");
        if (file.isDirectory()) {
            int lastIndexOfDot = basePathWithDot.lastIndexOf('.');
            if (lastIndexOfDot > -1) {
                basePathWithDot = basePathWithDot.substring(0, lastIndexOfDot);
            } else {
                // todo,verify
                basePathWithDot = "";
            }
        }
        return basePathWithDot;
    }
}
