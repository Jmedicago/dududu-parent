package cn.org.dududu.core.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
    public static List<Class<?>> getClassList(String packName, boolean isRecursive) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            String strFile = packName.replaceAll("\\.", "/");
            Enumeration<URL> urls = loader.getResources(strFile);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    String pkgPath = url.getPath();
                    if ("file".equals(protocol)) {
                        findClassName(classes, packName, pkgPath, isRecursive);
                    } else if ("jar".equals(protocol)) {
                        findClassName(classes, packName, url, isRecursive);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void findClassName(List<Class<?>> clazzList, String packName, String packPath, boolean isRecursive) {
        if (clazzList == null) {
            return;
        }
        File[] files = filterClassFiles(packPath);
        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (f.isFile()) {
                    String clazzName = getClassName(packName, fileName);
                    addClassName(clazzList, clazzName);
                } else {
                    if (isRecursive) {
                        String subPkgName = packName + "." + fileName;
                        String subPkgPath = packPath + "/" + fileName;
                        findClassName(clazzList, subPkgName, subPkgPath, true);
                    }
                }
            }
        }
    }

    public static void findClassName(List<Class<?>> clazzList, String packName, URL url, boolean isRecursive) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();
            String clazzName = jarEntryName.replace("/", ".");
            int endIndex = clazzName.lastIndexOf(".");
            String prefix = null;
            if (endIndex > 0) {
                clazzName = clazzName.substring(0, endIndex);
                endIndex = clazzName.lastIndexOf(".");
                if (endIndex > 0) {
                    prefix = clazzName.substring(0, endIndex);
                }
            }
            if (prefix != null && jarEntryName.endsWith(".class")) {
                if (prefix.equals(packName)) {
                    addClassName(clazzList, clazzName);
                } else if (isRecursive && prefix.startsWith(packName)) {
                    addClassName(clazzList, clazzName);
                }
            }
        }
    }

    private static File[] filterClassFiles(String packPath) {
        if (packPath == null) {
            return null;
        }
        return new File(packPath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }

    private static String getClassName(String packName, String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        String clazz = null;
        if (endIndex >= 0) {
            clazz = fileName.substring(0, endIndex);
        }
        String clazzName = null;
        if (clazz != null) {
            clazzName = packName + "." + clazz;
        }
        return clazzName;
    }

    private static void addClassName(List<Class<?>> clazzList, String clazzName) {
        if (clazzList != null && clazzName != null) {
            Class<?> clazz = null;
            try {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                clazz = Class.forName(clazzName, false, contextClassLoader);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz != null) {
                clazzList.add(clazz);
            }
        }
    }
}
