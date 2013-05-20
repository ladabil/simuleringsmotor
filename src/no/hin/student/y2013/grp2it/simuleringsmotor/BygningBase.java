package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

public class BygningBase extends SimulatorBase {
	protected int bruttoAreal = 0;
	protected int pRomAreal = 0;
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("bruttoAreal") )
		{
			this.bruttoAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("pRomAreal") )
		{
			this.pRomAreal = Integer.parseInt(node.getTextContent());
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
		str.append(String.format("BruttoAreal        = %d\n", this.bruttoAreal));
		str.append(String.format("pRomAreal          = %d\n", this.pRomAreal));
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}
}
