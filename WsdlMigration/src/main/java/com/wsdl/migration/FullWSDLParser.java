package com.wsdl.migration;
import com.predic8.schema.Schema;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Fault;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;

import groovy.lang.GrabResolver;
import groovy.lang.MetaMethod;

import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
//import groovy.xml.QName;
//import javax.xml.namespace.QName;
import javax.xml.namespace.*;
 /*
@GrabResolver(name='membrane', root='http://repository.membrane-soa.org/content/repositories/releases')
@Grab(group='com.predic8', module='soa-model-core', version='1.5.3')

//for some reason, I'm getting "java.lang.NoClassDefFoundError: Unable to load class com.predic8.schema.SchemaComponent due to missing dependency Lorg/slf4j/Logger;" in my GroovyConsole when I don't have this
@GrabConfig(systemClassLoader=true, initContextClassLoader=true)
*/
public class FullWSDLParser {
 
    public static void main(String[] args) throws WSDLException {
    
        WSDLParser parser = new WSDLParser();
        
     /*   WSDLFactory factory = WSDLFactory.newInstance();
        WSDLReader reader = factory.newWSDLReader();
        Definition defs = reader.readWSDL( null, "http://www.webservicex.com/globalweather.asmx?WSDL" );
        
        System.err.println("getTargetNamespace- > "+defs.getTargetNamespace());
      //  System.err.println("serice name - > "+defs.getAllServices().toString());
        System.out.println(""+defs.getQName());
     
        QName news = new QName(defs.getTargetNamespace(), "GlobalWeather");
        System.out.println(defs.getService(news).getPorts());*/
     //   Map m = defs.getAllServices();
 
       Definitions defs = parser.parse("http://www.thomas-bayer.com/axis2/services/BLZService?wsdl");
   //Definitions defs = parser.parse("https://community.workday.com/custom/developer/API/Time_Tracking/v23.0/Time_Tracking.wsdl");
        //setType((QName) qname))
      //QName type = (QName) defs.getTypeQName(token.getAttributeValue());
   //unwanted      defs.getTypeQName(token.getAttributeValue( null , 'type'));
      //  Definitions defs = parser.parse("http://www.webservicex.com/globalweather.asmx?WSDL");
        //http://www.webservicex.com/globalweather.asmx?WSDL
 
        out("-------------- WSDL Details --------------");
        System.err.println("TargenNamespace: \t ---- > " + defs.getTargetNamespace());
        if (defs.getDocumentation() != null) {
            out("Documentation: \t\t" + defs.getDocumentation());
        }
        out("\n");
        out("Schemas: ");
        for (Schema schema : defs.getSchemas()) {
            out("  TargetNamespace:---->>>>>>>>>>>>>>>>>>>>>>>. \t" + schema.getTargetNamespace());
        }
        out("\n");
         
        out("Messages: ");
        for (Message msg : defs.getMessages()) {
        	System.out.println("  Message Name: " + msg.getName());
        	System.out.println("  Message Parts: ");
            for (Part part : msg.getParts()) {
                out("    Part Name: " + part.getName());
                out("    Part Element: " + ((part.getElement() != null) ? part.getElement() : "not available!"));
                out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!" ));
                out("");
            }
        }
        out("");
 
        out("PortTypes: ");
        for (PortType pt : defs.getPortTypes()) {
        	System.err.println("  PortType Name: " + pt.getName());
        	System.out.println("  PortType Operations: ");
            for (Operation op : pt.getOperations()) {
            	System.err.println("    Operation Name: " + op.getName());
            	System.out.println("------------QNAME - > "+op.getQName());
            	System.err.println("    Operation Input Name: "
                    + ((op.getInput().getName() != null) ? op.getInput().getName() : "not available!"));
            	System.out.println("    Operation Input Message: "
                    + op.getInput().getMessage().getQname());
            	System.out.println("    Operation Output Name: "
                    + ((op.getOutput().getName() != null) ? op.getOutput().getName() : "not available!"));
            	System.out.println("    Operation Output Message: "
                    + op.getOutput().getMessage().getQname());
            	System.out.println("    Operation Faults: ");
                if (op.getFaults().size() > 0) {
                    for (Fault fault : op.getFaults()) {
                    	System.out.println("      Fault Name: " + fault.getName());
                    	System.out.println("      Fault Message: " + fault.getMessage().getQname());
                    }
                } else out("      There are no faults available!");
                 
            }
            out("");
        }
        out("");
 
      /*  out("Bindings: ");
        for (Binding bnd : defs.getBindings()) {
            out("  Binding Name: " + bnd.getName());
            out("  Binding Type: " + bnd.getPortType().getName());
            out("  Binding Protocol: " + bnd.getBinding().getProtocol());
            if(bnd.getBinding() instanceof AbstractSOAPBinding) out("  Style: " + (((AbstractSOAPBinding)bnd.getBinding()).getStyle()));
            out("  Binding Operations: ");
            for (BindingOperation bop : bnd.getOperations()) {
                out("    Operation Name: " + bop.getName());
                if(bnd.getBinding() instanceof AbstractSOAPBinding) {
                    out("    Operation SoapAction: " + bop.getOperation().getSoapAction());
                    out("    SOAP Body Use: " + bop.getInput().getBindingElements().get(0).getUse());
                }
            }
            out("");
        }
        out("");*/
 
        out("Services: ");
        for (Service service : defs.getServices()) {
            out("  Service Name: " + service.getName());
            out("  Service Potrs: ");
            for (Port port : service.getPorts()) {
                out("    Port Name: " + port.getName());
                out("    Port Binding: " + port.getBinding().getName());
                out("    Port Address Location: " + port.getAddress().getLocation()
                    + "\n");
            }
        }
        out("");
    }
 
    private static void out(String str) {
        System.out.println(str);
    }
}