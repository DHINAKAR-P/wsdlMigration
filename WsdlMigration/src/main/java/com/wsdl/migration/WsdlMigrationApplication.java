/*package com.wsdl.migration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.*"})
public class WsdlMigrationApplication {

		private String Dhina;
		public String sasi;
		protected boolean jill;

		public static void main(String[] args) throws ClassNotFoundException {
			List<String> results = new ArrayList<String>();
			List<String> listOfClasses_Name = new ArrayList<String>();
			  List<Field> Fields = new ArrayList<>();
			
			File[] files = new File("D:\\Study\\com\\compensation\\content").listFiles();
			System.out.println("files - > "+files.toString().toString());
			for (File file : files) {
			    if (file.isFile()) {
			    	System.out.println("file.get - > "+file.getName());
			        results.add(file.getName());
			    }
			}
			
			for(String fileName : results){
				 if (fileName.endsWith(".java")) {
	                 fileName = fileName.substring(0, fileName.length() - 5);  
	                 listOfClasses_Name.add(fileName);
	                 
	                // Class class_name_dynamic = Class.forName(fileName+".java");
	                 // to find the field in class
	               
	                // Field[] allFields =class_name_dynamic.getDeclaredFields();
	                 Field[] allFields = fileName.getClass().getDeclaredFields();
	                 for (Field field : allFields) {
	                	 System.err.println("field - > "+field.getName());
	                	 Fields.add(field);
	                     if (Modifier.isPrivate(field.getModifiers())) {
	                         privateFields.add(field);
	                     }
	                 }
	         }
				 System.out.println("Fields- > "+Fields.toString().toString());
			}
			
			
			SpringApplication.run(WsdlMigrationApplication.class, args);
		}

		
	
	}
*/