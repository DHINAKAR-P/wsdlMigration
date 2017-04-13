package com.wsdl.mysql;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wsdl.domain.WsdlData;

@Service("WsdlMigrationService")
public class WsdlMigrationService {
	 

	@Autowired
	DataLoaderDao dataLoaderDao;
	
	@Autowired
	WsdlDataDao wsdlDataDao;
	

	private String wsdlURL;
	
	@Value("${javafile.destination}")
	private String javafile_destination;
	
	@Value("${javafile.copy}")
	private String javafile_copy;
 
	@Value("${wsdl.conversion.command}")
	private String wsdl_conversion_command;
	
	@Value("${wsdl_axis_command_for_operation.command1}")
	private String wsdlAxisCommandForOperation1;
	
	@Value("${wsdl_axis_command_for_operation.command2}")
	private String wsdlAxisCommandForOperation2;
	
	@Value("${javafile.copyOperation}")
	private String javaFileCopyOperation;
	
	@Value("${ant_command}")
	private String antCommand;
	
	@Value("${project.id}")
	private Long projectId;
	
	@Value("${user.id}")
	private Long userId;

	@Value("${move_to_testAPP}")
	private String moveToTestAPP;
	
	private String hostname;
		             
	public String getHostname() {
		return hostname;
	}

	public String getWsdlURL() {
		return wsdlURL;
	}
	public void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String insertAttribute(String wsdl_Name,String className,HashMap<String, String> atrribute_type_map,Long projectId,Long userId ) throws Exception{
		
		dataLoaderDao.insertData(wsdl_Name ,className,atrribute_type_map,projectId,userId);
		
		return "Insert Succesfull!!!";
	}
	
	public  String downlaodWSDL(String wsdlEndpoing) throws Exception	{
		String site = wsdlEndpoing;
		String filename;
		String wsdlNameToReturn;
		String [] temp;
	    String partial_filename = site.substring(site.lastIndexOf("/") + 1);
	    if(partial_filename.contains("?")){
	    	System.out.println("partial_filename- inside ? > "+partial_filename);
	    	String[] questionMark = partial_filename.split("\\?");
	    	System.out.println("questionMark[0]"+questionMark[0]);
	    	 filename = "testAPP\\"+questionMark[0]+".wsdl" ;
	    	 wsdlNameToReturn=questionMark[0];
	    }else{
	    	  temp = partial_filename.split("\\.");
	 	     filename="testAPP\\"+temp[0]+".wsdl";
	 	    wsdlNameToReturn = temp[0];
	    }
	   
	    try 
	    {
	        URL url=new URL(site);
	        HttpURLConnection connection =(HttpURLConnection)  url.openConnection();
	        java.io.BufferedInputStream in = new java.io.BufferedInputStream(connection.getInputStream());
	        java.io.FileOutputStream fos = new java.io.FileOutputStream(filename);
	        java.io.BufferedOutputStream bout = new  BufferedOutputStream(fos,1024);
	        byte[] data = new byte[1024];
	        int i=0;
	        while((i=in.read(data,0,1024))>=0)
	        {
	            bout.write(data,0,i); 
	        }
	        bout.close();
	        in.close();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return wsdlNameToReturn;
	  }
	
	//parse wsdl 
	public  void parseWSDL(String packagename) throws InterruptedException{
		Process p = null;
		String s;
		//String command = "cmd /c mkdir testAPP && cd testAPP && wsimport -keep -verbose -p  com."+packagename+" "+packagename+".wsdl";
		String command = wsdl_conversion_command +packagename+" "+packagename+".wsdl";
		System.out.println("command- > "+command);
		try {
			p = Runtime.getRuntime().exec(command); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		try {
			while ((s = br.readLine()) != null) {
			    	System.out.println("" + s);

			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 p.waitFor();
        if(p.exitValue() == 0) {
        }
	}
	
	public void parseWSDLForOperation(String wsdlURL,String wsdl_name) throws InterruptedException {
		Process p = null;
		String s;
		String command = wsdlAxisCommandForOperation1 + wsdlURL +" "+ wsdlAxisCommandForOperation2 +wsdl_name;
		System.out.println("command- > "+command);
		try {
			p = Runtime.getRuntime().exec(command); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		try {
			while ((s = br.readLine()) != null) {
			    	System.out.println("" + s);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 p.waitFor();
        if(p.exitValue() == 0) {
        	System.out.println ("wsdl file parsed, Now we have java Files");
        }
	}
	
	public void changeDirectory(String wsdl_name) throws InterruptedException{
		String command2 = moveToTestAPP +" "+wsdl_name + " &&  ant";//+antCommand;
		 System.err.println("command2- > "+command2);
	        
	        Process p3 =null;
	        String s3;
	        try {
				p3 = Runtime.getRuntime().exec(command2); 
				System.out.println("= ? ??  ? -command2  - > ");
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader br3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
			try {
				while ((s3 = br3.readLine()) != null) {
				    	System.out.println("= ? ??  ? - > " + s3);

				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			 p3.waitFor();
	        if(p3.exitValue() == 0) {
	        	System.out.println ("wsdl file parsed, Now we have java Files");
	        }
	}
	
	
	public void copyJavaFiles() throws InterruptedException{
		 Process p2 = null;
			String s2;
			//String command2 = javafile_copy +"\""+"C:\\Users\\10Decoders\\Videos\\mongo workspace\\WsdlMigration\\bin\\"+"\"";
			String command2 = javafile_copy +"\""+javafile_destination+"\"";
			System.out.println("command2--- > "+command2);
			try {
				p2 = Runtime.getRuntime().exec(command2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			try {
				while ((s2 = br2.readLine()) != null) {
				    	System.out.println("" + s2);
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			 p2.waitFor();
	        if(p2.exitValue() == 0) {
	        	System.err.println ("code copied..!!!");
	        }
	}

	public void copyJavaClassesForOperation(String wsdlname) throws InterruptedException {
		 Process copyOperation = null;
			String bufferString;
			String commandForCopyOperation =  moveToTestAPP +" "+wsdlname +" "+ javaFileCopyOperation +" build\\classes  "+"\""+javafile_destination+"\"";
			System.out.println("commandForCopyOperation--- > "+commandForCopyOperation);
			try {
				copyOperation = Runtime.getRuntime().exec(commandForCopyOperation);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader br2 = new BufferedReader(new InputStreamReader(copyOperation.getInputStream()));
			try {
				while ((bufferString = br2.readLine()) != null) {
				    	System.out.println("" + bufferString);
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			copyOperation.waitFor();
	        if(copyOperation.exitValue() == 0) {
	        	System.err.println ("code copied..for operation!!!");
	        }		
	}

	public void methodInsertion(HashMap<String, List<String>> method_parameter_map ) {
		
		dataLoaderDao.insertMethod(method_parameter_map);
	}
	
	
	public List<WsdlData> getWsdlData (Long project_id ,Long user_id) throws Exception{
		
		return wsdlDataDao.getWsdlData(project_id, user_id);
	}
}
