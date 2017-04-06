package com.wsdl.migration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.wsdl.mysql.WsdlMigrationService;

import ch.qos.logback.core.net.SyslogOutputStream;

public class InpackageTest {
	/*private String Dhina;
	public String sasi;
	protected boolean jill;*/
	/*
	@SuppressWarnings("unchecked")
	private static Class[] getClasses(String packageName)throws ClassNotFoundException, IOException {
		File classpathLocation =null; 
		System.out.println("package name - > "+packageName);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        System.out.println("path --- name - > "+path);
        Enumeration resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            classpathLocation = new File(resource.getFile());
            System.err.println("resource.getFile()- > "+resource.getFile());
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        System.out.println("dirs- > "+dirs.size());
        for (File directory : dirs) {
            classes.addAll(findClasses(classpathLocation, packageName));
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }
	
	 @SuppressWarnings("unchecked")
	private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
	        List classes = new ArrayList();
	        System.out.println("directory > "+directory.getName()+" -- > pacakge- > "+packageName);
	        if (directory.exists()) {
	        	System.out.println("Dir exist - > "+directory.exists());
	            return classes;
	        }
	     //   File[] files = new File("D:\\Study\\com\\compensation\\content").listFiles();
	        System.out.println("directory- > "+directory.getAbsolutePath());
	        File[] files = directory.listFiles();
	       // System.out.println("file list - > "+files.length);
	        for (File file : files) {
	        	System.out.println("file name - > "+file.getName());
	            if (file.isDirectory()) {
	                assert !file.getName().contains(".");
	                classes.addAll(findClasses(file, packageName + "." + file.getName()));
	            } else if (file.getName().endsWith(".class")) {
	                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	            }
	        }
	        return classes;
	    }*/

	public static void main(String[] args){
		  ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		  WsdlMigrationService wsdlMigrationService = (WsdlMigrationService) context
					.getBean("WsdlMigrationService");
		  
		  System.exit(0);
	}

}
