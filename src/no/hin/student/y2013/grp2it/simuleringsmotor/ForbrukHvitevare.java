package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Formatter;
import java.util.List;

import org.w3c.dom.Node;

/*
 * ForbrukHvitevare - 28/9-2013
 */
public class ForbrukHvitevare extends BygningBase {

	double antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	double personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
	double hvitevarerForbruk = 0;
	List<SimulatorBase> personliste = SimuleringsMotor.getFamilie().getPersonList();
	
	protected double kjoleskap = 160;
	protected double fryseboks = 175;
	protected double stovsuger = 0; 
	protected double komfyr = 2200; 
	protected double ventilator = 75; 
	protected double brodrister = 1000; 
	protected double mikro = 2000; 
	protected double kaffetrakter = 1500;
	protected double oppvaskmaskin = 2000; 
	protected double vaskemaskin = 2500; 
	protected double torketrommel = 3000; 
	protected double hartorker = 750; 
	protected double barbermaskin = 10; 
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("kjoleskap") )
		{
			this.kjoleskap = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("fryseboks") )
		{
			this.fryseboks = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("stovsuger") ) 
		{
			this.stovsuger = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("komfyr") ) 
		{
			this.komfyr = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("ventilator") ) 
		{
			this.ventilator = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("brodrister") ) 
		{
			this.brodrister = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("mikro") ) 
		{
			this.mikro = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("kaffetrakter") ) 
		{
			this.kaffetrakter = Integer.parseInt(node.getTextContent());
		}
		if ( node.getNodeName().equalsIgnoreCase("oppvaskmaskin") )	
		{
			this.oppvaskmaskin = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("vaskemaskin") ) 
		{
			this.vaskemaskin = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("torketrommel") ) 
		{
			this.torketrommel = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("hartorker") ) 
		{
			this.hartorker = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("barbermaskin") ) 
		{
			this.barbermaskin = Integer.parseInt(node.getTextContent());
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
	}

	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		double forbrukHvitevare = 0, multiplier = 0 , forbrukkomfyr = 0, forbrukventilator = 0, forbrukkaffetrakter = 0, forbrukoppvaskmaskin = 0, forbrukkjoleskap = 0, 
				forbrukfryseboks  = 0, forbrukbrodrister = 0, forbrukvaskemaskin = 0, forbruktorketrommel = 0, forbrukhartorker = 0, forbrukbarbermaskin = 0, 
				forbrukstovsuger = 0, forbrukmikro = 0, forbrukMedMultiplier = 0, komfyrfaktor = 0, ventilatorfaktor = 0, kaffetrakterfaktor = 0, oppvaskmaskinfaktor = 0,
				kjoleskapfaktor = 0, fryseboksfaktor = 0, brodristerfaktor = 0, vaskemaskinfaktor = 0, torketrommelfaktor = 0, hartorkerfaktor = 0, barbermaskinfaktor = 0,
				stovsugerfaktor = 0, mikrofaktor = 0;
			String time5;
		
//		TODO: kalkuler multiplier fra array med alder
		/*
		if (antallPersoner < 1 )
		{ 
			multiplier = 0;
		}
		else if (antallPersoner == 1)
		{
			multiplier = 1;
		}
		
		else if (antallPersoner > 1 )
		{
			multiplier = 1;

				if (personAlder < 13) 
				{
					multiplier = (multiplier + 0.4);	
				}
				else if (personAlder >= 13 && personAlder < 25 ) 
				{
					multiplier = (multiplier + 0.5);	
				}
				else if (personAlder >= 25 && personAlder < 65 )
				{
					multiplier = (multiplier + 0.6);
				}
				else if (personAlder > 65 )
				{
					multiplier = (multiplier + 0.35);
				}
				
				
		}
		
		*/
		multiplier = 1;
		double totalMultiplier = 0;
		
		//loop for å gi hver person en egen multiplier
		for(int i =0; i<personliste.size(); i++){  
            Person p = (Person)personliste.get(i);
			if (p.getAlder() < 13) 
			{
				p.setMultiplier(0.4);	
			}
			else if (p.getAlder() >= 13 && p.getAlder() < 25 ) 
			{
				p.setMultiplier(0.5);	
			}
			else if (p.getAlder() >= 25 && p.getAlder() < 65 )
			{
				p.setMultiplier(0.6);
			}
			else if (p.getAlder() > 65 )
			{
				p.setMultiplier(0.35);
			}
            totalMultiplier += p.getMultiplier();
		}
		
		// Beregning av tidsfaktor for forbruk (% bruk per time)
		Formatter tf = new Formatter();
		int time = Integer.parseInt((tf.format("%TH", startTime)).toString());
		
		//Utgangspunkt i familie i arbeid/skole
		if (time == 0)
		{
			// opplistning av alle faktorer
			stovsugerfaktor = 0.0;
			komfyrfaktor = 0.0;
			ventilatorfaktor = 0.0;
			brodristerfaktor = 0.0;
			mikrofaktor = 0.0;
			kaffetrakterfaktor = 0.0;
			oppvaskmaskinfaktor = 0.0;
			vaskemaskinfaktor = 0.0;
			torketrommelfaktor = 0.0;
			hartorkerfaktor = 0.0;
			barbermaskinfaktor = 0.0;
			
			
		}
		else if (time == 7)
		{
			hartorkerfaktor = 0.6;
			barbermaskinfaktor = 1.0;
			brodristerfaktor = 1.0;
			kaffetrakterfaktor = 0.5;
		}
		else if (time == 16)
		{
			komfyrfaktor = 0.8;
			ventilatorfaktor = 1.0;
			vaskemaskinfaktor = 0.6;
		}
		else if (time == 17)
		{
			oppvaskmaskinfaktor = 0.7;
		}
		else if (time == 18)
		{
			vaskemaskinfaktor = 0.4;
			oppvaskmaskinfaktor = 0.3;
			kaffetrakterfaktor = 0.5;
		}
		else if (time == 19)
		{
			torketrommelfaktor = 0.2;
			torketrommelfaktor = 0.8;
			stovsugerfaktor = 0.6;
		}
		else if (time == 20)
		{
			stovsugerfaktor = 0.4;
		}
		else if (time == 21)
		{
			hartorkerfaktor = 0.4;
			komfyrfaktor = 0.2;
		}
		
		

//		Verdier for utregning (effekt (parset) * brukstid per døgn * faktor (% forbruk for time)) <-- egen side i GUI for input (effekt, type, antall, brukstid) ?
		
		// Uavhengie verdier
		forbrukkjoleskap = (kjoleskap * 7.7 * kjoleskapfaktor);
		forbrukfryseboks  = (fryseboks * 10 * fryseboksfaktor); 
		forbrukstovsuger = (stovsuger * 0.15 * stovsugerfaktor);	
		forbrukkomfyr = (komfyr * 1 * komfyrfaktor);
		forbrukventilator = (ventilator * 0.28 * ventilatorfaktor);
		forbrukbrodrister = (brodrister * 0.028 * brodristerfaktor);
		forbrukmikro = (mikro * 0.25 * mikrofaktor);
		forbrukkaffetrakter = (kaffetrakter * 0.5 * kaffetrakterfaktor); 
		
		// Verdier som må ganges med forholdstall
		forbrukoppvaskmaskin = (oppvaskmaskin * 0.5 * oppvaskmaskinfaktor); 
		forbrukvaskemaskin = (vaskemaskin * 0.57 * vaskemaskinfaktor);
		forbruktorketrommel = (torketrommel * 0.42 * torketrommelfaktor);
		
		// Kjønnsavhengige produkter
		forbrukhartorker = (hartorker * 0.20 * hartorkerfaktor);
		forbrukbarbermaskin = (barbermaskin * 0.07 * barbermaskinfaktor); 
		
	 
		 //beregner forbruk til hvitevarer
		forbrukHvitevare = (forbrukkjoleskap + forbrukfryseboks + forbrukstovsuger + forbrukkomfyr + forbrukventilator + forbrukkaffetrakter) ;
		forbrukMedMultiplier = multiplier * (forbrukoppvaskmaskin + forbruktorketrommel + forbrukoppvaskmaskin + forbrukmikro);
		
		//beregning av personlig forbruk
				for(int i =0; i<personliste.size(); i++){ 
			Person p = (Person)personliste.get(i);
			double forbruk = 0;
			
			forbruk = (forbrukHvitevare/personliste.size()) + (forbrukMedMultiplier*(multiplier+totalMultiplier)*(p.getMultiplier()/totalMultiplier));
			 System.out.printf("Person: ", personliste.get(i), " \n Forbruk i kwh: " + forbruk/1000 );
			 
		}
		
		this.energiForbruk = hvitevarerForbruk = ((forbrukHvitevare+forbrukMedMultiplier) / 1000); // omgjøring til kWh
		
		System.out.format("AntallPersoner: %d\n", personliste.size());
	//	System.out.format("hvitevarer multiplier: %f\n", multiplier);
		System.out.format("Energiforbruk hvitevarer denne timen: %f\n", this.energiForbruk);
		System.out.println("Tid " + time);
		

		return true;
	}
	
}
