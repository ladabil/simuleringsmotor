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
		double powerUsageWater = 0, multiplier = 0 , heatLoss = 0, handWash = 0, shower = 0, bath = 0, dishWash = 0, floorWash  = 0;
		
		
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
					multiplier = (multiplier + 0.4);
				}
				else if (personAlder > 65 )
				{
					multiplier = (multiplier + 0.35);
				}
				
				
		}
		

//		waterMultiplier = multiplier;
		
			
		if (this.priBoilerSize == 0) {
			this.energiForbruk = priBoilerPower;
			heatLoss = 0;
			handWash = 0.04;		
		}
	
		if (this.priBoilerSize > 0 && this.priBoilerSize < 70) {
			this.energiForbruk = priBoilerPower;
			heatLoss = 0.8;
			handWash = 0.25;		
		}
	
		if (this.priBoilerSize > 70 && this.priBoilerSize < 140) {
			this.energiForbruk = priBoilerPower;
			heatLoss = 1.2;
			handWash = 0.25;		
		}
	
		if (this.priBoilerSize > 140) {
			this.energiForbruk = priBoilerPower;
			heatLoss = 1.9;
			handWash = 0.25;		
		}
	
		 shower = 2.5;
		 bath = (this.bathtubeSize * 0.04);
		 dishWash = 3; // Oppvaskmaskin, 60l - 50*C = ca 3 kWh
		 floorWash = (this.pRomAreal * 0.04); // 100m2 = 50l - 40*C = ca 2 kWh
	 
	 
		 //beregner forbruk til tappevann 
		powerUsageWater = (multiplier ) * ( heatLoss ) + ( ((handWash * 4) + shower + (dishWash / 5) + ( floorWash / 7 ) ) ) ;
		
		this.energiForbruk = powerUsageWater;
		
		System.out.format("Vann multiplier: %f\n", multiplier);
		System.out.format("AntallPersoner: %f\n ", antallPersoner);
		System.out.format("Energiforbruk Vann per døgn: %f\n", this.energiForbruk);

		return true;
	}
	
}
