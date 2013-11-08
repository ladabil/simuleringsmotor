package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Formatter;
import java.util.List;

import org.w3c.dom.Node;

/*
 * ForbrukLys - 07/11-2013
 */
public class Belysning extends BygningBase {

	
	int antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	int personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
	List<SimulatorBase> personliste = SimuleringsMotor.getFamilie().getPersonList();
	double lysForbruk = 0;
	
	protected double antLys = 4;
	protected double priLysType = 60;
	protected double secLysType = 14;
	protected double brenntid = 8; 
	protected double lysDiff = 0; 
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("antLys") )
		{
			this.antLys = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("priLysType") )
		{
			this.priLysType = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("secLysType") ) 
		{
			this.secLysType = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("brenntid") ) 
		{
			this.brenntid = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("lysDiff") ) 
		{
			this.lysDiff = Integer.parseInt(node.getTextContent());
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
	}

	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		double  forbrukLys = 0, lysfaktor = 0;
//		double antLys = 0, priLysType = 0, secLysType = 0, brenntid = 0, lysDiff = 0, lysfaktor = 0;
		
//		Forbruksverdier i egen set (beregnet for senere admin endring via php
		//antLys = 4;
//		priLysType = 60;
//		secLysType = 14;
//		brenntid = 8;
//		lysDiff = 0;
		
		
		Formatter tf = new Formatter();
		int time = Integer.parseInt((tf.format("%TH", startTime)).toString());

			if (time >= 0 && time <= 6 )
			{
				// opplistning av alle faktorer
				lysfaktor = 0.2;
			}
			else if (time == 7 )
			{
				// opplistning av alle faktorer
				lysfaktor = 0.5;
			}
			else if (time >= 8 && time <= 15 )
			{
				// opplistning av alle faktorer
				lysfaktor = 0.0;
			}
			else if (time >= 16 && time <= 20 )
			{
				// opplistning av alle faktorer
				lysfaktor = 0.3;
			}
			else if (time >= 21 && time <= 24 )
			{
				// opplistning av alle faktorer
				lysfaktor = 1.0;
			}
				
				forbrukLys = (
						(priLysType * (antLys * (lysDiff/100)) * lysfaktor) +
						(secLysType * (antLys * ((100 - lysDiff)/100)) * lysfaktor) 
						);
			
			
		this.energiForbruk = (forbrukLys / 1000); // omgjøring til kWh
		
//		System.out.format("AntallPersoner: %d\n", antallPersoner);
//		System.out.format("PersonAlder: %d\n", personAlder);
		System.out.format("Energiforbruk belysning denne timen: %f\n", this.energiForbruk);
//		System.out.println("diffLys " + lysDiff);
//		System.out.println("antLys " + antLys);
//		System.out.println("Tid " + time);

		return true;
	}
}
