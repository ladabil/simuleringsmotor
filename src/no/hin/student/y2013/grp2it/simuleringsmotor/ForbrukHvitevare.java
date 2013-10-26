package no.hin.student.y2013.grp2it.simuleringsmotor;

/*
 * ForbrukHvitevare - 28/9-2013
 */
public class ForbrukHvitevare extends BygningBase {

	double antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	double personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
	double hvitevarerForbruk = 0;

	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		double forbrukHvitevare = 0, multiplier = 0 , komfyr = 0, ventilator = 0, kaffetrakter = 0, oppvaskmaskin = 0, kjoleskap = 0, fryseboks  = 0, 
				brodrister = 0, vaskemaskin = 0, torketrommel = 0, hartorker = 0, barbermaskin = 0, stovsuger = 0, mikro = 0;
		
//		TODO: kalkuler multiplier fra array med alder
		
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
		
		
//		Verdier for utregning (effekt * brukstid per døgn) <-- egen side i GUI for input (effekt, type, antall, brukstid) ?
		
		// Uavhengie verdier
		kjoleskap = (160 * 7.7);
		fryseboks  = (175 * 10); 
		stovsuger = (1200 * 0.15);	
		komfyr = (2200 * 1);
		ventilator = (75 * 0.28);
		brodrister = (1000 * 0.028);
		mikro = (2000 * 0.25);
		kaffetrakter = (1500 * 0.5); 
		
		// Verdier som må ganges med forholdstall
		oppvaskmaskin = (2000 * 0.5); 
		vaskemaskin = (2500 * 0.57);
		torketrommel = (3000 * 0.42);
		
		// Kjønnsavhengige produkter
		hartorker = (750 * 0.20);
		barbermaskin = (10 * 0.07); 
		
	 
		 //beregner forbruk til hvitevarer
		forbrukHvitevare = ((kjoleskap + fryseboks + stovsuger + komfyr + ventilator + mikro + kaffetrakter) + (multiplier * (oppvaskmaskin + torketrommel + oppvaskmaskin) ) );
		
		this.energiForbruk = hvitevarerForbruk = (forbrukHvitevare / 1000); // omgjøring til kWh
		
		System.out.format("AntallPersoner: %f\n", antallPersoner);
		System.out.format("hvitevarer multiplier: %f\n", multiplier);
		System.out.format("Energiforbruk hvitevarer per døgn: %f\n", this.energiForbruk);

		return true;
	}
	
}
