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

import com.wsdl.domain.WsdlData;
import com.wsdl.mysql.WsdlMigrationService;


public class SpringWsdlFieldIdentification {

	public static void main(String[] args) throws Exception {
		String wsdlName = null;
		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		  WsdlMigrationService wsdlMigrationService = (WsdlMigrationService) context.getBean("WsdlMigrationService");
		  List<WsdlData> listOfEndPoint = wsdlMigrationService.getWsdlData(wsdlMigrationService.getProjectId(), wsdlMigrationService.getUserId());
		  for(WsdlData wsdl : listOfEndPoint){
		try {
			wsdlName = wsdlMigrationService.downlaodWSDL(wsdl.getWsdl_endpoint());
			System.err.println("status - downloaded- > >"+wsdl.getWsdl_endpoint()); 
			wsdlMigrationService.parseWSDL(wsdlName);
			wsdlMigrationService.parseWSDLForOperation(wsdl.getWsdl_endpoint(),wsdlName);
			wsdlMigrationService.changeDirectory(wsdlName);
			wsdlMigrationService.copyJavaClassesForOperation(wsdlName);
			System.out.println(" - > parse wsdl finished ...!!!");
			wsdlMigrationService.copyJavaFiles();
			System.err.println(" - > copy file finished ...!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HashMap<String, List<String>> fielsList = new HashMap<String, List<String>>();
		HashMap<String, String> atrribute_type_map = new HashMap<String, String>();

			final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
		final Set<BeanDefinition> classes = provider.findCandidateComponents("com."+wsdlName);
		for (BeanDefinition bean : classes) {
			System.out.println("Enabling for class == "+bean.getBeanClassName());
			List<String> Fields = new ArrayList<String>();
			Class<?> clazz = Class.forName(bean.getBeanClassName());
			System.err.println("class- > "+clazz.getSimpleName());
			Field[] allFields = clazz.getDeclaredFields();
			String finalType;
			for (Field field : allFields) {
				Fields.add(field.getName());
				if(!field.getType().getSimpleName().equals("List")){
					atrribute_type_map.put(field.getName(), field.getType().getSimpleName());
				}else{
					String tempType= field.getGenericType().getTypeName();
					String [] test =tempType.split("\\.");
					finalType = test[test.length-1];
					finalType = finalType.substring(0, test[test.length-1].length() - 1);
					finalType = "List<"+finalType+">";
					atrribute_type_map.put(field.getName(), finalType);
				}
			}
			if(!clazz.getSimpleName().equals("ObjectFactory")){
				 wsdlMigrationService.insertAttribute(wsdlName,clazz.getSimpleName(),atrribute_type_map,wsdlMigrationService.getProjectId(),wsdlMigrationService.getUserId());
			}
		}
		System.err.println("Method Initialization - > ");
		//method identification
		HashMap<String, String> method_map = new HashMap<String, String>();
		
		List<String> listOfParameters = new ArrayList<String>();
		
		final ClassPathScanningCandidateComponentProvider methodProvider = new ClassPathScanningCandidateComponentProvider(
				false);
		methodProvider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
		final Set<BeanDefinition> methodClasses = methodProvider.findCandidateComponents("com.methods");
		
		for (BeanDefinition bean : methodClasses) {
			Class<?> clazz = Class.forName(bean.getBeanClassName());
			Method[] method = clazz.getDeclaredMethods();
			if(clazz.getSimpleName().endsWith("Skeleton")){
			for(Method m : method){
				HashMap<String, List<String>> method_parameter_map = new HashMap<String, List<String>>();
				Type [] typeOfMethod = m.getParameterTypes();
				for(Type ty :typeOfMethod){
					listOfParameters.add(ty.getTypeName());
				}
				method_parameter_map.put(m.getName(), listOfParameters);
				wsdlMigrationService.methodInsertion(method_parameter_map);
			}
				
			}
		}
		break;
}
		System.exit(0);
	}
	private SpringWsdlFieldIdentification(){};
}
