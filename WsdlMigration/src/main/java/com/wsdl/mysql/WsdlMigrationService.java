package com.wsdl.mysql;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("WsdlMigrationService")
public class WsdlMigrationService {
	 

	@Autowired
	DataLoaderDao dataLoaderDao;
	
	@Value("${wsdl.url}")
	private String wsdlURL;
	
	@Value("${javafile.destination}")
	private String javafile_destination;
	
	@Value("${javafile.copy}")
	private String javafile_copy;
 
	@Value("${wsdl.conversion.command}")
	private String wsdl_conversion_command;
	private String hostname;
		             
	public String getHostname() {
		return hostname;
	}
	public String display(String className,List<String> nounAttributes){
		dataLoaderDao.insertData(className,nounAttributes);
		return "Insert Succesfull!!!";
	}
	public String getWsdlURL() {
		return wsdlURL;
	}
	public void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}
	
	
	public  String downlaodWSDL() throws Exception	{
		// String site= "https://community.workday.com/custom/developer/API/Time_Tracking/v23.0/Time_Tracking.wsdl";
	   // String site="http://www.webservicex.com/globalweather.asmx?WSDL";
		System.out.println("wsdlURL- > "+wsdlURL);
		String site = wsdlURL;
	    String partial_filename = site.substring(site.lastIndexOf("/") + 1);
	    String [] temp = partial_filename.split("\\.");
	    String filename="testAPP\\"+temp[0]+".wsdl";
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
	    return temp[0];
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
			System.out.println("p - > started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		try {
			while ((s = br.readLine()) != null) {
			    	System.out.println("" + s);

			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 p.waitFor();
        if(p.exitValue() == 0) {
        	System.out.println ("wsdl file parsed, Now we have java Files");
        }
	}
	
	public void copyJavaFiles() throws InterruptedException{
		 Process p2 = null;
			String s2;
			//String command2 = "cmd /c  xcopy /E /I  testAPP "+"\""+"C:\\Users\\10Decoders\\Videos\\mongo workspace\\WsdlMigration\\src\\main\\java\\"+"\"";
			//String command2 = javafile_copy +"\""+"C:\\Users\\10Decoders\\Videos\\mongo workspace\\WsdlMigration\\bin\\"+"\"";
			String command2 = javafile_copy +"\""+javafile_destination+"\"";
			System.out.println("command2--- > "+command2);
			try {
				p2 = Runtime.getRuntime().exec(command2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			try {
				while ((s2 = br2.readLine()) != null) {
				    	System.out.println("" + s2);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 p2.waitFor();
	        if(p2.exitValue() == 0) {
	        	System.err.println ("code copied..!!!");
	        }
	}
	
	 public static synchronized void loadLibrary(java.io.File jar){
	        try {
	            /*We are using reflection here to circumvent encapsulation; addURL is not public*/
	            java.net.URLClassLoader loader = (java.net.URLClassLoader)ClassLoader.getSystemClassLoader();
	            java.net.URL url = jar.toURI().toURL();
	            /*Disallow if already loaded*/
	            for (java.net.URL it : java.util.Arrays.asList(loader.getURLs())){
	                if (it.equals(url)){
	                    return;
	                }
	            }
	            java.lang.reflect.Method method = java.net.URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{java.net.URL.class});
	            method.setAccessible(true); /*promote the method to public access*/
	            method.invoke(loader, new Object[]{url});
	        } catch (final java.lang.NoSuchMethodException | 
	            java.lang.IllegalAccessException | 
	            java.net.MalformedURLException | 
	            java.lang.reflect.InvocationTargetException e){
	            //throw new MyException(e);
	        }
	    }
	 
	 
}
