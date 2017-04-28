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

import com.wsdl.domain.WsdlClass;
import com.wsdl.domain.WsdlData;
import com.wsdl.mysql.WsdlMigrationService;

public class SpringWsdlFieldIdentification {

	public static void main(String[] args) throws Exception {
		String wsdlName = null;
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		WsdlMigrationService wsdlMigrationService = (WsdlMigrationService) context.getBean("WsdlMigrationService");
		List<WsdlData> listOfEndPoint = wsdlMigrationService.getWsdlData(wsdlMigrationService.getProjectId(),
				wsdlMigrationService.getUserId());
		WsdlData wsdlInsert = null;
		System.err.println("________"+listOfEndPoint.size());
		/*List<HashMap<String, List<String>>> listOfMethodsForInsertion = new ArrayList<HashMap<String, List<String>>>();
		int loopCount = 0;*/
		for (WsdlData wsdl : listOfEndPoint) {
			wsdlInsert = wsdl;
			System.err.println("Wsdl ID - > "+wsdl.getId());
			try {
				wsdlName = wsdlMigrationService.downlaodWSDL(wsdl);
				//wsdlName ="BLZService";
				System.err.println("status - downloaded- > >" + wsdl.getWsdl_endpoint());
				wsdlMigrationService.parseWSDL(wsdlName);
				wsdlMigrationService.parseWSDLForOperation(wsdl, wsdlName);
				wsdlMigrationService.changeDirectory(wsdlName);
				wsdlMigrationService.copyJavaClassesForOperation(wsdlName);
				wsdlMigrationService.addClasspathJar(wsdlName); 
				System.out.println(" - > parse wsdl finished ...!!!");
			//	wsdlMigrationService.copyJavaFiles();
			} catch (Exception e) {
				e.printStackTrace();
			}

			HashMap<String, List<String>> fielsList = new HashMap<String, List<String>>();
			HashMap<String, String> atrribute_type_map = new HashMap<String, String>();

			final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
					false);
			provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
			final Set<BeanDefinition> classes = provider.findCandidateComponents("com." + wsdlName);
			for (BeanDefinition bean : classes) {
				System.out.println("Enabling for class == " + bean.getBeanClassName());
				List<String> Fields = new ArrayList<String>();
				Class<?> clazz = Class.forName(bean.getBeanClassName());
				System.err.println("class- > " + clazz.getSimpleName());
				Field[] allFields = clazz.getDeclaredFields();
				String finalType;
				for (Field field : allFields) {
					Fields.add(field.getName());
					if (!field.getType().getSimpleName().equals("List")) {
						atrribute_type_map.put(field.getName(), field.getType().getSimpleName());
					} else {
						String tempType = field.getGenericType().getTypeName();
						String[] test = tempType.split("\\.");
						finalType = test[test.length - 1];
						finalType = finalType.substring(0, test[test.length - 1].length() - 1);
						finalType = "List<" + finalType + ">";
						atrribute_type_map.put(field.getName(), finalType);
					}
				}
				
				if (!clazz.getSimpleName().equals("ObjectFactory")) {
					wsdlMigrationService.insertAttribute(wsdlName, clazz.getSimpleName(), atrribute_type_map,wsdlInsert);
				}
			}

			// method identification
			HashMap<String, String> method_map = new HashMap<String, String>();

			final ClassPathScanningCandidateComponentProvider methodProvider = new ClassPathScanningCandidateComponentProvider(
					false);
			methodProvider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
			final Set<BeanDefinition> methodClasses = methodProvider.findCandidateComponents("com.methods."+wsdlName);
			System.err.println(" - > in Method scanner - >...!!!");
			for (BeanDefinition bean : methodClasses) {
				Class<?> clazz = Class.forName(bean.getBeanClassName());
				Method[] method = clazz.getDeclaredMethods();
				if (clazz.getSimpleName().endsWith("Skeleton")) {
					System.err.println(" - > Skeleton class - ...!!!");
					for (Method m : method) {
						WsdlClass wsdlClass = new WsdlClass();
						wsdlClass.setClassName(clazz.getSimpleName()); 
						wsdlClass.setWsdlname(wsdlName);
						wsdlClass.setProjectId(wsdlMigrationService.getProjectId());
						wsdlClass.setUserId(wsdlMigrationService.getUserId());
						wsdlClass.setWsdlId(wsdlInsert.getId()); 
						
						HashMap<String, List<String>> method_parameter_map = new HashMap<String, List<String>>();
						
						List<String> listOfParameters = new ArrayList<String>();
						Type[] typeOfMethod = m.getParameterTypes();
						System.out.println("return type - > "+m.getReturnType());
						for (Type ty : typeOfMethod) {
							listOfParameters.add(ty.getTypeName());
						}
						method_parameter_map.put(m.getName(), listOfParameters);
						wsdlMigrationService.methodInsertion(method_parameter_map,m.getReturnType(),wsdlInsert,wsdlClass);
					}
				}
			}
			/*System.err.println("loopcount before - > - > "+loopCount+"listOfEndPoint.size() - 1 - > "+listOfEndPoint.size());
			if (loopCount == listOfEndPoint.size() - 1) {
				System.out.println("loopcount - > "+loopCount);
				System.err.println("listOfMethodsForInsertion.get(loopCount)- > "+listOfMethodsForInsertion.toString().toString());
				wsdlMigrationService.methodInsertion(listOfMethodsForInsertion.get(listOfMethodsForInsertion.size()
						-1));
			}
			loopCount++;*/
		}
		System.exit(0);
	}

	private SpringWsdlFieldIdentification() {
	};
}
