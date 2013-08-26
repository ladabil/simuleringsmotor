package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.Node;

public class Varmetap extends SimulatorBase {
	protected int bruttoAreal = 0;
	protected int ytterveggAreal = 0;
	protected int yttertakAreal = 0;
	protected int vinduDørAreal = 0;
	protected int byggstandard = 0;
	protected int luftVolum = 0;
	//U-verdier som varierer etter byggstandard
	private double gjenvinningsgrad, varmetapet, kuldebro, uvegg, utak, uvinduDør, ugulv, lekkasjetall = 0;
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("bruttoAreal") )
		{
			this.bruttoAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("ytterveggAreal") )
		{
			this.ytterveggAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("yttertakAreal") )
		{
			this.yttertakAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("vinduDørAreal") )
		{
			this.vinduDørAreal = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("byggstandard") )
		{
			this.byggstandard = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("luftVolum") )
		{
			this.luftVolum = Integer.parseInt(node.getTextContent());
		}
		
		else
		{
			super.parseXMLNodeElement(node);
		}
	}
	//Angir verdiene i henhold til byggstandard, og beregner varmetapet
	public double varmetapBeregning(int byggstandard, int bAreal, int yveggAreal, int ytakAreal, int vinDørAreal, int lVolum)
	{
	if (this.byggstandard < 1987) {
		this.utak = 0.23;
		this.uvegg = 0.45;
		this.uvinduDør = 0;
		this.ugulv = 0.30;
		this.kuldebro = 0.05;
		this.lekkasjetall = 5;
		this.gjenvinningsgrad = 0;
		
	}
	
	if (this.byggstandard >= 1987 && this.byggstandard < 1997) {
		this.utak = 0.20;
		this.uvegg = 0.30;
		this.ugulv = 0.30;
		this.uvinduDør = 2.4;
		this.kuldebro = 0.05;
		this.lekkasjetall = 4;
		this.gjenvinningsgrad = 0.5;
	}
	
	if (this.byggstandard > 1997) {
		this.utak = 0.15;
		this.uvegg = 0.22;
		this.ugulv = 0.15;
		this.uvinduDør = 1.6;
		this.kuldebro = 0.05;
		this.lekkasjetall = 2;
		this.gjenvinningsgrad = 0.6;
	}
	//beregner varmetapet gjennom tak, vegg, gulv, vinduer og dører, kuldebro, infiltrasjon, ventilasjon
	varmetapet = (yttertakAreal*utak)+(ytterveggAreal*uvegg)+(bruttoAreal*ugulv)+(vinduDørAreal*uvinduDør)+(kuldebro*bruttoAreal)+(0.33*lekkasjetall*luftVolum*0.07)+(0.33*(luftVolum*0.5)*(1-gjenvinningsgrad));
	return varmetapet;
	}
	
}
