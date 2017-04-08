package com.wsdl.migration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.wsdl.mysql.WsdlMigrationService;


public class SpringWsdlFieldIdentification {
	
	public static void main(String[] args) throws Exception {
		String wsdl_Name = null;
		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		  WsdlMigrationService wsdlMigrationService = (WsdlMigrationService) context
					.getBean("WsdlMigrationService");
		  
		try {
			wsdl_Name = wsdlMigrationService.downlaodWSDL();
			System.err.println("status - downloaded- > >"+wsdl_Name); 
			wsdlMigrationService.parseWSDL(wsdl_Name);
			System.out.println(" - > parse wsdl finished ...!!!");
			wsdlMigrationService.copyJavaFiles();
			System.err.println(" - > copy file finished ...!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, List<String>> fielsList = new HashMap<String, List<String>>();
		HashMap<String, String> atrribute_type_map = new HashMap<String, String>();

		// create scanner and disable default filters (that is the 'false'
		// argument)
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		// add include filters which matches all the classes (or use your own)
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

		
		// get matching classes defined in the package 
		//final Set<BeanDefinition> classes = provider.findCandidateComponents("com.compensation.content");//
		final Set<BeanDefinition> classes = provider.findCandidateComponents("com."+wsdl_Name);//
		// this is how you can load the class type from BeanDefinition instance
		int count =0;
		for (BeanDefinition bean : classes) {
			List<String> Fields = new ArrayList<String>();
			Class<?> clazz = Class.forName(bean.getBeanClassName());
			// ... do your magic with the class ...
			Method[] method = clazz.getDeclaredMethods();
			
			for(Method m : method){
				System.err.println("method - > "+m.getName());
				Type [] typeOfMethod = m.getParameterTypes();
				for(Type ty :typeOfMethod){
					System.out.println("type of method- > "+ty);
				}
			}
			
			System.err.println("class- > "+clazz.getSimpleName());
			Field[] allFields = clazz.getDeclaredFields();
			String finalType = null;
			for (Field field : allFields) {
				Fields.add(field.getName());
				
				if(!field.getType().getSimpleName().equals("List")){
					atrribute_type_map.put(field.getName(), field.getType().getSimpleName());
				}else{
					
					String tempType= field.getGenericType().getTypeName().toString();
					String [] test =tempType.split("\\.");
					finalType = test[test.length-1];//;
					finalType = finalType.substring(0, test[test.length-1].length() - 1);
					finalType = "List<"+finalType+">";
					atrribute_type_map.put(field.getName(), finalType);
				}
			}
			if(!clazz.getSimpleName().equals("ObjectFactory")){
				 wsdlMigrationService.display(wsdl_Name,clazz.getSimpleName(),atrribute_type_map);
			}
		}
		System.exit(0);
	}
}
