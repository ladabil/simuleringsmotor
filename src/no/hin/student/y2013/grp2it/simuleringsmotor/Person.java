package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

/*
 * Person - 5/11-2013
 */
public class Person extends SimulatorBase {
	static public int kvinne = 1;
	static public int mann = 2;
	
	static public int skole = 1;
	static public int arbeid = 2;
	static public int trygd = 3;

	
	private int alder;
	private int kjonn;
	private double multiplier;
	private int virke;
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("Alder") )
		{
			this.setAlder(Integer.parseInt(node.getTextContent()));
		}
		else if ( node.getNodeName().equalsIgnoreCase("Kjonn") )
		{
			if ( node.getTextContent().equalsIgnoreCase("kvinne") )
			{
				this.setKjonn(Person.kvinne);
			}
			else
			{
				this.setKjonn(Person.mann);
			}
		}
		else if ( node.getNodeName().equalsIgnoreCase("virke") )
		{
			this.setVirke(Integer.parseInt(node.getTextContent())); 
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
		str.append(String.format("Alder        = %d\n", this.getAlder()));
		str.append(String.format("Kjønn        = %d\n", this.getKjonn()));
		str.append(String.format("Virke        = %d\n", this.getVirke()));
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}

	public int getVirke() {
		return virke;
	}

	public void setVirke(int virke) {
		this.virke = virke;
	}

	public int getAlder() {
		return alder;
	}

	public void setAlder(int alder) {
		this.alder = alder;
	}

	public int getKjonn() {
		return kjonn;
	}

	public void setKjonn(int kjonn) {
		this.kjonn = kjonn;
	}
	
	public void setMultiplier (double multiplier) {
		this.multiplier = multiplier;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
}
