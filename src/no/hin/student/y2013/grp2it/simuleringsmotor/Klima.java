package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

/*
 * Klima - 20/5-2013
 */
public class Klima extends SimulatorBase {
	private int sone = 0;
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("sone") )
		{
			this.sone = Integer.parseInt(node.getTextContent());
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(String.format("+++++ Type: %s\n", this.getClass().getName()));
		str.append(String.format("Klimasone          = %d\n", this.sone));
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}
}
