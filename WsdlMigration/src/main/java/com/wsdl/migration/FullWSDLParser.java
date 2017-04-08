package com.wsdl.migration;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Fault;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.WSDLParser;

import groovy.xml.QName;
 

public class FullWSDLParser {
 
    public static void main(String[] args) {
    	

    	// the fix
    	/*MetaMethod originalSetType = Declaration.metaClass.getMetaMethod("setType",[Object]);
    	Declaration.metaClass.setType = { Object obj ->
    	   if (obj)
    	       originalSetType.invoke((QName) obj)
    	};*/
    	
        WSDLParser parser = new WSDLParser();
 
        //https://community.workday.com/custom/developer/API/Time_Tracking/v23.0/Time_Tracking.wsdl
       // Definitions defs = parser.parse("http://www.thomas-bayer.com/axis2/services/BLZService?wsdl");
        Definitions defs = parser.parse("https://community.workday.com/custom/developer/API/Time_Tracking/v23.0/Time_Tracking.wsdl");
        //setType((QName) qname))
   //unwanted      defs.getTypeQName(token.getAttributeValue( null , 'type'));
      //  Definitions defs = parser.parse("http://www.webservicex.com/globalweather.asmx?WSDL");
        //http://www.webservicex.com/globalweather.asmx?WSDL
 
        out("-------------- WSDL Details --------------");
        System.out.println("TargenNamespace: \t" + defs.getTargetNamespace());
       /* if (defs.getDocumentation() != null) {
            out("Documentation: \t\t" + defs.getDocumentation());
        }
        out("\n");
 
         For detailed schema information see the FullSchemaParser.java sample.
        out("Schemas: ");
        for (Schema schema : defs.getSchemas()) {
            out("  TargetNamespace: \t" + schema.getTargetNamespace());
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
        out("");*/
 
        out("PortTypes: ");
        for (PortType pt : defs.getPortTypes()) {
        	System.err.println("  PortType Name: " + pt.getName());
        	System.out.println("  PortType Operations: ");
            for (Operation op : pt.getOperations()) {
            	System.err.println("    Operation Name: " + op.getName());
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
 
        /*out("Services: ");
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
        out("");*/
    }
 
    private static void out(String str) {
        System.out.println(str);
    }
}