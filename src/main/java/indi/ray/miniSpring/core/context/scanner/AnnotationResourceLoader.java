package indi.ray.miniSpring.core.context.scanner;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AnnotationResourceLoader {
    private static final Logger logger = Logger.getLogger(AnnotationResourceLoader.class);

    private static final String DEL_SYMBOL = "&DEL&";


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
    public Set<String> findClassNamesForGivenPaths(String... basePaths) {
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
