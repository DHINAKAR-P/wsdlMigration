package com.wsdl.migration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.WSDLParser;


public class ListWSDLOperations {
	 
	
	 public static String[] listOperations(String filename) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		  Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(filename));
		  NodeList elements = d.getElementsByTagName("wsdl:operation");
		  ArrayList<String> operations = new ArrayList<String>();
		  for (int i = 0; i < elements.getLength(); i++) {
		    operations.add(elements.item(i).getAttributes().getNamedItem("name").getNodeValue());
		  }
		  return operations.toArray(new String[operations.size()]);
		}
	 
	  public static void main(String[] args) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		  
		  listOperations("globalweather.wsdl");
	   /* WSDLParser parser = new WSDLParser();
	 
	    Definitions defs = parser
	       // .parse("https://community.workday.com/custom/developer/API/Time_Tracking/v23.0/Time_Tracking.wsdl");
	    		.parse("http://www.webservicex.com/globalweather.asmx?WSDL");
	 //http://www.webservicex.com/globalweather.asmx?WSDL
	    for (PortType pt : defs.getPortTypes()) {
	      System.out.println(pt.getName());
	      for (Operation op : pt.getOperations()) {
	        System.err.println(" --- >" + op.getName());
	      }
	    }
	  */
	  }
	}
