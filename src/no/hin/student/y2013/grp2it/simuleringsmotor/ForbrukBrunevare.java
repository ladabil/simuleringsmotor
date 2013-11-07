package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Formatter;
import java.util.List;

import org.w3c.dom.Node;

/*
 * ForbrukBrunevare - 08/10-2013
 */
public class ForbrukBrunevare extends BygningBase {

	
	int antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	int personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
	List<SimulatorBase> personliste = SimuleringsMotor.getFamilie().getPersonList();
	double brunevarerForbruk = 0;
	
	protected double tv = 100;
	protected double tvst = 1;
	protected double dvd = 11; 
	protected double dvdst = 1; 
	protected double radio = 9; 
	protected double radiost = 1; 
	protected double stereo = 25; 
	protected double stereost = 1;
	protected double pc = 40; 
	protected double mobil = 30; 
	protected double internett = 9; 
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("tv") )
		{
			this.tv = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("tvst") )
		{
			this.tvst = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("dvd") ) 
		{
			this.dvd = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("dvdst") ) 
		{
			this.dvdst = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("radio") ) 
		{
			this.radio = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("radiost") ) 
		{
			this.radiost = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("stereo") ) 
		{
			this.stereo = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("stereost") ) 
		{
			this.stereost = Integer.parseInt(node.getTextContent());
		}
		if ( node.getNodeName().equalsIgnoreCase("pc") )	
		{
			this.pc = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("mobil") ) 
		{
			this.mobil = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("internett") ) 
		{
			this.internett = Integer.parseInt(node.getTextContent());
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
	}

	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		double  forbrukBrunevare = 0, dognbruk = 0;
		double tv = 0, tvst = 0, dvd = 0, dvdst = 0, radio = 0, radiost = 0, stereo = 0, stereost = 0, pc = 0, mobil = 0, internett = 0,
				tvfaktor = 0, tvstfaktor = 0, dvdfaktor = 0, dvdstfaktor = 0, radiofaktor = 0, radiostfaktor = 0, stereofaktor = 0, stereostfaktor = 0, 
				pcfaktor = 0, mobilfaktor = 0, internettfaktor = 0;
		
//		Forbruksverdier i egen set (beregnet for senere admin endring via php
		tv = 100; // LED 58, PLASMA 85
		tvst = 0.7; // LED 0.3, PLASMA 0.3
		dvd = 11;
		dvdst = 1;
		radio = 9;
		radiost = 0.7;
		stereo = 25;
		stereost = 0.7;
		pc = 40;
		mobil = 30;
		internett = 9;
		
		Formatter tf = new Formatter();
		int time = Integer.parseInt((tf.format("%TH", startTime)).toString());

		
//		TODO: foreach løkke. Tilpassing for sammensatt familie (1 pers = familie med 1) og utregning
		//Utregning: parset effekt apparat * brukstid i døgnet * faktor for forbruk aktuelle time
		if (this.antallPersoner == 1 && this.personAlder < 65) 
		{ 
			if (time >= 0 && time <= 6 )
			{
				// opplistning av alle faktorer
				tvfaktor = 0.0;
				tvstfaktor = 1.0;
				dvdfaktor = 0.0;
				dvdstfaktor = 1.0;
				radiofaktor = 0.0;
				radiostfaktor = 0.0;
				stereofaktor = 0.0;
				stereostfaktor = 1.0;
				pcfaktor = 0.0;
				mobilfaktor = 0.0;
				internettfaktor = 1.0;
			}
			else if (time == 7 )
			{
				// opplistning av alle faktorer
				tvfaktor = 1.0;
				tvstfaktor = 0.0;
				dvdfaktor = 0.0;
				dvdstfaktor = 1.0;
				radiofaktor = 0.0;
				radiostfaktor = 0.0;
				stereofaktor = 0.0;
				stereostfaktor = 1.0;
				pcfaktor = 1.0;
				mobilfaktor = 0.0;
				internettfaktor = 1.0;
			}
			else if (time >= 8 && time <= 15 )
			{
				// opplistning av alle faktorer
				tvfaktor = 0.0;
				tvstfaktor = 1.0;
				dvdfaktor = 0.0;
				dvdstfaktor = 1.0;
				radiofaktor = 0.0;
				radiostfaktor = 0.0;
				stereofaktor = 0.0;
				stereostfaktor = 1.0;
				pcfaktor = 0.0;
				mobilfaktor = 0.0;
				internettfaktor = 1.0;
			}
			else if (time >= 16 && time <= 20 )
			{
				// opplistning av alle faktorer
				tvfaktor = 1.0;
				tvstfaktor = 0.0;
				dvdfaktor = 0.0;
				dvdstfaktor = 1.0;
				radiofaktor = 0.0;
				radiostfaktor = 0.0;
				stereofaktor = 0.6;
				stereostfaktor = 0.4;
				pcfaktor = 1.0;
				mobilfaktor = 0.0;
				internettfaktor = 1.0;
			}
			else if (time >= 21 && time <= 23 )
			{
				// opplistning av alle faktorer
				tvfaktor = 1.0;
				tvstfaktor = 0.0;
				dvdfaktor = 1.0;
				dvdstfaktor = 0.0;
				radiofaktor = 0.0;
				radiostfaktor = 0.0;
				stereofaktor = 0.0;
				stereostfaktor = 1.0;
				pcfaktor = 1.0;
				mobilfaktor = 1.0;
				internettfaktor = 1.0;
			}
				
				forbrukBrunevare = (
						(tv * tvfaktor)+
						(tvst * tvfaktor)+ // st = 24t - brukstid
						(dvd * dvdfaktor)+
						(dvdst  * dvdstfaktor)+
						(radio * radiofaktor)+
						(radiost * radiostfaktor)+
						(stereo * stereofaktor)+
						(stereost * stereostfaktor)+
						(pc * pcfaktor)+
						(mobil * mobilfaktor)+
						(internett * internettfaktor)
						);
			}
			else if (this.antallPersoner == 1 && this.personAlder > 65) 
			{ 
				if (time >= 0 && time <= 6 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 1.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 7 && time <= 9 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 1.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 10 && time <= 13 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 1.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 14 && time <= 15 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.5;
					radiostfaktor = 0.5;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 1.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 16 && time <= 18 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.5;
					radiostfaktor = 0.5;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 1.0;
					internettfaktor = 1.0;
				}
				else if (time >= 19 && time <= 22 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 0.3;
					dvdstfaktor = 0.7;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 1.0;
					internettfaktor = 1.0;
				}
				
				forbrukBrunevare = (
						(tv * tvfaktor)+
						(tvst * tvfaktor)+ // st = 24t - brukstid
						(dvd * dvdfaktor)+
						(dvdst  * dvdstfaktor)+
						(radio * radiofaktor)+
						(radiost * radiostfaktor)+
						(stereo * stereofaktor)+
						(stereost * stereostfaktor)+
						(pc * pcfaktor)+
						(mobil * mobilfaktor)+
						(internett * internettfaktor)
						);
		}
		else if (this.antallPersoner >= 2 && this.personAlder >= 13 && this.personAlder <= 25)
		{
				
				if (time >= 0 && time <= 6 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 1.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time == 7 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 1.0;
					pcfaktor = 1.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 8 && time <= 15 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 1.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 16 && time <= 20 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.6;
					stereostfaktor = 0.4;
					pcfaktor = 1.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 21 && time <= 23 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 1.0;
					dvdstfaktor = 0.0;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 1.0;
					pcfaktor = 1.0;
					mobilfaktor = 1.0;
					internettfaktor = 1.0;
				}
				forbrukBrunevare = (
						(tv * tvfaktor)+
						(tvst * tvfaktor)+ // st = 24t - brukstid
						(dvd * dvdfaktor)+
						(dvdst  * dvdstfaktor)+
						(radio * radiofaktor)+
						(radiost * radiostfaktor)+
						(stereo * stereofaktor)+
						(stereost * stereostfaktor)+
						(pc * pcfaktor)+
						(mobil * mobilfaktor)+
						(internett * internettfaktor)
						);
			}
		else if (this.antallPersoner >= 2 && this.personAlder > 25 && this.personAlder <= 65)
		{
				if (time >= 0 && time <= 6 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 1.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 7 && time <= 9 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 1.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 10 && time <= 13 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.0;
					radiostfaktor = 1.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 14 && time <= 15 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.5;
					radiostfaktor = 0.5;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 1.0;
					mobilfaktor = 0.0;
					internettfaktor = 1.0;
				}
				else if (time >= 16 && time <= 18 )
				{
					// opplistning av alle faktorer
					tvfaktor = 0.0;
					tvstfaktor = 1.0;
					dvdfaktor = 0.0;
					dvdstfaktor = 1.0;
					radiofaktor = 0.5;
					radiostfaktor = 0.5;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 1.0;
					internettfaktor = 1.0;
				}
				else if (time >= 19 && time <= 22 )
				{
					// opplistning av alle faktorer
					tvfaktor = 1.0;
					tvstfaktor = 0.0;
					dvdfaktor = 0.3;
					dvdstfaktor = 0.7;
					radiofaktor = 0.0;
					radiostfaktor = 0.0;
					stereofaktor = 0.0;
					stereostfaktor = 0.0;
					pcfaktor = 0.0;
					mobilfaktor = 1.0;
					internettfaktor = 1.0;
				}
				forbrukBrunevare = (
						(tv * tvfaktor)+
						(tvst * tvfaktor)+ // st = 24t - brukstid
						(dvd * dvdfaktor)+
						(dvdst  * dvdstfaktor)+
						(radio * radiofaktor)+
						(radiost * radiostfaktor)+
						(stereo * stereofaktor)+
						(stereost * stereostfaktor)+
						(pc * pcfaktor)+
						(mobil * mobilfaktor)+
						(internett * internettfaktor)
						);
				}
				

		
		
		this.energiForbruk = (forbrukBrunevare / 1000); // omgjøring til kWh
		
//		System.out.format("AntallPersoner: %d\n", antallPersoner);
//		System.out.format("PersonAlder: %d\n", personAlder);
		System.out.format("Energiforbruk brunevarer denne timen: %f\n", this.energiForbruk);
//		System.out.println("tv " + tv);
//		System.out.println("tvfaktor " + tvfaktor);
//		System.out.println("Tid " + time);

		return true;
	}
}
