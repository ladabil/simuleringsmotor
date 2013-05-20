package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class SimuleringsMotor extends SimulatorBase {
	static SimuleringsMotor simMotor = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		simMotor = new SimuleringsMotor();
		simMotor.doRun();
	}
	
	public void doRun()
	{
		System.out.println("Running");
		
		parseXMLFile();
		
		System.out.println("Died");
	}
	
	public void parseXMLFile()
	{
	    try {
	    	 
	    	File file = new File("./testData.xml");
	     
	    	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
	                                 .newDocumentBuilder();
	     
	    	Document doc = dBuilder.parse(file);
	     
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     
	    	if ( !doc.getDocumentElement().getNodeName().equalsIgnoreCase("simulering") )
	    	{
	    		System.out.println("feil i root tag i xml-filen, forventer \"simulering\" - fikk: " + doc.getDocumentElement().getNodeName());
	    		return;
	    	}
	    	
	    	if (doc.hasChildNodes()) {
	    		Node simuleringsNode = doc.getChildNodes().item(0);
	    		
	    		if ( simuleringsNode.hasChildNodes() )
	    		{
		    		this.setXMLNodeList(simuleringsNode.getChildNodes());
		    		this.parseXML();
	    		}
	    	}
	    	
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	}

}
