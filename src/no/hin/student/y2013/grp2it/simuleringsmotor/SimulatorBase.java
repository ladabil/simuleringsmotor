package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Mai 2013 - Gruppe 2 IT
 */

public class SimulatorBase {
	public static final String packageName = "no.hin.student.y2013.grp2it.simuleringsmotor";
	private NodeList nodeList;
	private SimulatorBase[] simulatorBaseList;

	public SimulatorBase()
	{
		this.nodeList = null;
	}
	
	public double getBeregning()
	{
		return 0.0;
	}
	
	// Setter nodelisten (noder i XML-filen som tilhører meg..)
	public void setXMLNodeList(NodeList nodeList) 
	{
		this.nodeList = nodeList;
	}
	  
	// Hent nodelisten
	public NodeList getXMLNodeList()
	{
		return this.nodeList;
	}
	
	// parse XML-nodelisten..
	public boolean parseXML()
	{
		SimulatorBase tmpSimBase;
		
		for (int count = 0; count < this.nodeList.getLength(); count++) {
			  
			Node currentNode = this.nodeList.item(count);
		 
			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + currentNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				this.parseXMLNodeElement(currentNode);
				
				// Sjekk om vi har attributter
				if (currentNode.hasAttributes()) {

					// Hent alle attributer og verdier..
					NamedNodeMap nodeMap = currentNode.getAttributes();
					
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);

						// Hvis attributten heter type og verdien er class, last inn klassen og fortsett prosesseringen herfra
						if ( node.getNodeName().equalsIgnoreCase("type") && node.getNodeValue().equalsIgnoreCase("class"))
						{
							// Last klasse
							System.out.println("------------------------------------------------");
							System.out.println("Laster klassen: " + currentNode.getNodeName());
							System.out.println("------------------------------------------------");

							tmpSimBase = this.loadClass(currentNode.getNodeName());
							
							if ( tmpSimBase == null )
							{
								continue;
							}
								
							if (currentNode.hasChildNodes()) {
								tmpSimBase.setXMLNodeList(currentNode.getChildNodes());
							}
							
							tmpSimBase.parseXML();
							
							System.out.println(tmpSimBase);
						}
		 
//						System.out.println("attr name : " + node.getNodeName());
					}
				}
		 
				System.out.println("Node Name =" + currentNode.getNodeName() + " [CLOSE]");		
			}
		}

		return true;
	}
	
	public void parseXMLNodeElement(Node node)
	{
		System.out.println("parseXMLNodeElement(Node node): Not implemented in SimulatorBase");
	}
	
	
	// Laster inn en ny klasse
	public SimulatorBase loadClass(String className)
	{
		String fullClassName = packageName + "." + className;
		
		Class loadedClass = null;
		SimulatorBase baseClass = null;
		
		try {
			loadedClass = Class.forName(fullClassName);
			baseClass = (SimulatorBase) loadedClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baseClass;
	}		
}
