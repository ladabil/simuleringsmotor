package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

public class BygningBase extends SimulatorBase {
	protected int bruttoAreal = 0;
	protected int pRomAreal = 0;
	protected int byggstandard = 0; //***J***
	protected int ytterveggAreal = 0; //***J***
	protected int yttertakAreal = 0; //***J***
	protected int vinduDørAreal = 0; //***J***
	protected int luftVolum = 0; //***J***
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
		else if ( node.getNodeName().equalsIgnoreCase("byggstandard") ) //***J***
		{
			this.byggstandard = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("ytterveggAreal") ) //***J***
		{
			this.ytterveggAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("yttertakAreal") ) //***J***
		{
			this.yttertakAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("vinduDorAreal") ) //***J***
		{
			this.vinduDørAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("luftVolum") ) //***J***
		{
			this.luftVolum = Integer.parseInt(node.getTextContent());
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
		str.append(String.format("Byggstandard        = %d\n", this.byggstandard)); //***J***
		str.append(String.format("YtterveggAreal        = %d\n", this.ytterveggAreal)); //***J***
		str.append(String.format("YttertakAreal        = %d\n", this.yttertakAreal)); //***J***
		str.append(String.format("vinduDørAreal        = %d\n", this.vinduDørAreal)); //***J***
		str.append(String.format("LuftVolum        = %d\n", this.luftVolum)); //***J***
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}
}
