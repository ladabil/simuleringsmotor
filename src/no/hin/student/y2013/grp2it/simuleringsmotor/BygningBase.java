package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

/*
 * BygningsBase - 11/9-2013
 */
public class BygningBase extends SimulatorBase {
	protected int bruttoAreal = 0;
	protected int pRomAreal = 0;
	protected int byggstandard = 0; //***J***
	protected int ytterveggAreal = 0; //***J***
	protected int yttertakAreal = 0; //***J***
	protected int vinduDorAreal = 0; //***J***
	protected int luftVolum = 0; //***J***
	protected int onsketTemp = 0;//***J***
	protected int priBoilerSize = 0; //***Rune***
	protected int priBoilerPower = 0; //***Rune***
	protected int secBoilerSize = 0; //***Rune***
	protected int secBoilerPower = 0; //***Rune***
	protected int bathtubeSize = 0; //***Rune***
	protected double priHeat = 73;
	protected double secHeat = 0;
	protected double heatDiff = 0;
	protected double floorHeatWa = 0; 
	protected double floorHeatEl = 0;
	
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
			this.vinduDorAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("luftVolum") ) //***J***
		{
			this.luftVolum = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("onsketTemp") ) //***J***
		{
			this.onsketTemp = Integer.parseInt(node.getTextContent());
		}
		if ( node.getNodeName().equalsIgnoreCase("priBoilerSize") )	//***Rune***
		{
			this.priBoilerSize = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("priBoilerPower") ) //***Rune***
		{
			this.priBoilerPower = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("secBoilerSize") ) //***Rune***
		{
			this.secBoilerSize = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("secBoilerPower") ) //***Rune***
		{
			this.secBoilerPower = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("priHeat") ) //***Rune***
		{
			this.priHeat = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("secHeat") ) //***Rune***
		{
			this.secHeat = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("heatDiff") ) //***Rune***
		{
			this.heatDiff = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("floorHeatWa") ) //***Rune***
		{
			this.floorHeatWa = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("floorHeatEl") ) //***Rune***
		{
			this.floorHeatEl = Integer.parseInt(node.getTextContent());
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
		
	}
	
	// Skriver ut parset data
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(String.format("+++++ Type: %s\n", this.getClass().getName()));
		str.append(String.format("BruttoAreal        = %d\n", this.bruttoAreal));
		str.append(String.format("pRomAreal          = %d\n", this.pRomAreal));
		str.append(String.format("Byggstandard        = %d\n", this.byggstandard)); //***J***
		str.append(String.format("YtterveggAreal        = %d\n", this.ytterveggAreal)); //***J***
		str.append(String.format("YttertakAreal        = %d\n", this.yttertakAreal)); //***J***
		str.append(String.format("vinduDorAreal        = %d\n", this.vinduDorAreal)); //***J***
		str.append(String.format("LuftVolum        = %d\n", this.luftVolum)); //***J***
		str.append(String.format("Onsket temperatur        = %d\n", this.onsketTemp)); //***J***
		str.append(String.format("priBoilerSize        = %d\n", this.priBoilerSize)); //***Rune***
		str.append(String.format("priBoilerPower        = %d\n", this.priBoilerPower)); //***Rune***
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}
}
