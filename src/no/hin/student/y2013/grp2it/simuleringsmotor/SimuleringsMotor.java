package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

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
		
		simulatorTest();
		
		System.out.println("Died");
	}
	
	public void simulatorTest()
	{
	    try {
	    	 
	    	File file = new File("./testData.xml");
	     
	    	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
	                                 .newDocumentBuilder();
	     
	    	Document doc = dBuilder.parse(file);
	     
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     
	    	if (doc.hasChildNodes()) {
	    		this.setXMLNodeList(doc.getChildNodes());
	    		this.parseXML();
//	    		printNote(doc.getChildNodes());
	    	}
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	}
}
