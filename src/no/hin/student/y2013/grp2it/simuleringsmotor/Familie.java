package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.ArrayList;
import java.util.List;

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
	
	public int getFamilieAntallPersoner()
	{
		int antPersoner = 0;
		
		if ( simulatorBaseList.size() > 0 )
		{
			for ( int i=0; i<simulatorBaseList.size(); i++ )
			{
//				System.out.println(simulatorBaseList.get(i).getClass().getCanonicalName());
				
				if ( simulatorBaseList.get(i).getClass().getCanonicalName().equals("no.hin.student.y2013.grp2it.simuleringsmotor.Person") )
				{
					antPersoner++;
				}
			}
		}
		
		return antPersoner;
	}
	
	public List<SimulatorBase> getPersonList()
	{
		List<SimulatorBase> simulatorBaseList = new ArrayList<SimulatorBase>();
		
		if ( simulatorBaseList.size() > 0 )
		{
			for ( int i=0; i<simulatorBaseList.size(); i++ )
			{
//				System.out.println(simulatorBaseList.get(i).getClass().getCanonicalName());
				
				if ( simulatorBaseList.get(i).getClass().getCanonicalName().equals("no.hin.student.y2013.grp2it.simuleringsmotor.Person") )
				{
					simulatorBaseList.add(simulatorBaseList.get(i));
				}
			}
		}

		return simulatorBaseList;
	}
	
	public int getFamilieAlder()
	{
		return this.personAlder;
	}
	public String getFamilieVirke()
	{
		return this.personVirke;
	}
	
	// Skriver ut parset data
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(String.format("+++++ Type: %s\n", this.getClass().getName()));
		str.append(String.format("antallPersoner        = %d\n", this.getFamilieAntallPersoner()));
		str.append(String.format("personAlder        = %d\n", this.personAlder));
		str.append(String.format("personVirke          = %d\n", this.personVirke));
		str.append(String.format("----- Type Slutt\n"));

		return str.toString();
	}
}
