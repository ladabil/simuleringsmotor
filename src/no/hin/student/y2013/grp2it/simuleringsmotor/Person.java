package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

/*
 * Person - 5/11-2013
 */
public class Person extends SimulatorBase {
	static public int kvinne = 1;
	static public int mann = 2;
	
	private int alder;
	private int kjonn;
	private String virke;
	
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
			this.setVirke(node.getTextContent()); 
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

	public String getVirke() {
		return virke;
	}

	public void setVirke(String virke) {
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
}
