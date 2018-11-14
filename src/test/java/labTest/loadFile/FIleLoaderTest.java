package labTest.loadFile;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FIleLoaderTest {
    private static final Logger logger = Logger.getLogger(FIleLoaderTest.class);

    @Test
    public void getClassPath() {
        System.out.println(System.getProperties().getProperty("java.class.path"));
    }

    @Test
    public void testLoadJar() {
        String basePackagePath = "myJar.jar";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(basePackagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resources == null || !resources.hasMoreElements()) {
            logger.debug("no resource loaded");
            return;
        }
        Set<String> result = new HashSet<String>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            logger.debug("resource:" + resource);
            logger.debug("resource path:" + resource);
            String protocol = resource.getProtocol();
            logger.debug(protocol);
            if (protocol.equals("file")) {
                File file = new File(resource.getPath());
                searchClassNamesWithFile(file, basePackagePath.replaceAll(File.pathSeparator, "."), result);
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

    public void searchClassNamesWithJar(JarFile jarFile, String prefixWithSlash, boolean checkPrefix, Set<String> result) {
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

    public void searchClassNamesWithFile(File rootFile, String prefixWithDot, Set<String> result) {
        if (!rootFile.canRead()) {
            logger.debug("file can't read:" + rootFile);
            return;
        }
        String name = rootFile.getName();
        if (rootFile.isDirectory()) {
            for (File file : rootFile.listFiles()) {
            }
        } else if (name.endsWith(".class")) {
            result.add(prefixWithDot + "." + rootFile.getName());
        } else if (name.endsWith(".jar")) {
            try {
                searchClassNamesWithJar(new JarFile(rootFile), "", false, result);
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
