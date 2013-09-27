package no.hin.student.y2013.grp2it.simuleringsmotor;

/*
 * ForbrukVann - 11/9-2013
 */
public class ForbrukVann extends BygningBase {

	double antall = 0, alder = 0;
	double antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner(antall);
	double personAlder = SimuleringsMotor.getFamilie().getFamilieAlder(alder);

	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		double forbrukVarmtVann = 0, multiplier = 0 , varmeTapVann = 0, handVask = 0, dusj = 0, badekar = 0, oppvask = 0, gulvvask  = 0;
		
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
					multiplier = (multiplier + 0.6);	
				}
				else if (personAlder >= 25 && personAlder < 65 )
				{
					multiplier = (multiplier + 0.5);
				}
				else if (personAlder > 65 )
				{
					multiplier = (multiplier + 0.35);
				}
				
				
		}
		

//		waterMultiplier = multiplier;
		
			
		if (this.priBoilerSize == 0) {
			this.energiForbruk = priBoilerPower;
			varmeTapVann = 0;
			handVask = 0.04;		
		}
	
		if (this.priBoilerSize > 0 && this.priBoilerSize < 70) {
			this.energiForbruk = priBoilerPower;
			varmeTapVann = 0.8;
			handVask = 0.25;		
		}
	
		if (this.priBoilerSize > 70 && this.priBoilerSize < 140) {
			this.energiForbruk = priBoilerPower;
			varmeTapVann = 1.2;
			handVask = 0.25;		
		}
	
		if (this.priBoilerSize > 140) {
			this.energiForbruk = priBoilerPower;
			varmeTapVann = 1.9;
			handVask = 0.25;		
		}
	
		 dusj = 2.5;
		 badekar = (this.bathtubeSize * 0.04);
		 oppvask = 3; // Oppvaskmaskin, 60l - 50*C = ca 3 kWh
		 gulvvask = (this.pRomAreal * 0.04); // 100m2 = 50l - 40*C = ca 2 kWh
	 
	 
		 //beregner forbruk til tappevann (effekt-tap oppvarming + forholdstall familie * (4 håndvask per pers per døgn + 1 dusj per pers per døgn + oppvask hånd hver 5. dag(forhold om oppvaskmaskin hvitevare + 1 gulvvask hver uke) 
		forbrukVarmtVann = (varmeTapVann) + (multiplier * ((handVask * 4) + dusj + (oppvask / 5) + ( gulvvask / 7 ) ) ) ;
		
		this.energiForbruk = forbrukVarmtVann;
		
		System.out.format("Vann multiplier: %f\n", multiplier);
		System.out.format("AntallPersoner: %f\n", antallPersoner);
		System.out.format("Energiforbruk Vann per døgn: %f\n", this.energiForbruk);

		return true;
	}
	
}
