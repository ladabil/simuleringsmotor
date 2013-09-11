package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

/*
 * Familie - 11/9-2013
 */
public class Familie extends SimulatorBase {
	protected int personAlder = 0;
	protected int antallPersoner = 0;
	protected String personVirke;
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("personAlder") )
		{
			this.personAlder = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("antallPersoner") )
		{
			this.antallPersoner = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("personVirke") )
		{
			this.personVirke = node.getTextContent(); 
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
	}
	
	public double getFamilieAntallPersoner(double antallPersoner)
	{
		return this.antallPersoner;
	}
	public double getFamilieAlder(double personAlder)
	{
		return this.personAlder;
	}
	// Skriver ut parset data
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(String.format("+++++ Type: %s\n", this.getClass().getName()));
		str.append(String.format("antallPersoner        = %d\n", this.antallPersoner));
		str.append(String.format("personAlder        = %d\n", this.personAlder));
		str.append(String.format("personVirke          = %d\n", this.personVirke));
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}
}
