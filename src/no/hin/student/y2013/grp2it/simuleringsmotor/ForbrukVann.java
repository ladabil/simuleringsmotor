package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Formatter;

import org.w3c.dom.Node;

/*
 * ForbrukVann - 08/10-2013
 */
public class ForbrukVann extends BygningBase {

	double antall = 0, alder = 0;
	double antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	double personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
//	double varmetao = SimuleringsMotor.getVarmetap().getVarmetap();
	
	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		double forbrukVarmtVann = 0, multiplier = 0 , varmeTapVann = 0, handVask = 0, dusj = 0, badekar = 0, oppvask = 0, gulvvask  = 0,
				varmeTapVannfaktor = 0, handvaksfaktor = 0, dusjfaktor = 0, badekarfaktor = 0, oppvaskfaktor = 0, gulvvaskfaktor = 0, oppvarming = 0,
				privarm = 0, secvarm = 0, el = 0, oppvarmfaktor = 0;
		double temperatur = SimuleringsMotor.getKlima().getTemperatureForTime(startTime);
		
//		System.out.println("doBeregning i ForbrukVann");
//		System.exit(-1);
		
		Formatter tf = new Formatter();
		int time = Integer.parseInt((tf.format("%TH", startTime)).toString());
		
		if (time >= 0 && time <= 6 )
		{
			// opplistning av alle faktorer
			varmeTapVannfaktor = 0.0;
			handvaksfaktor = 0.0;
			dusjfaktor = 0.0;
			badekarfaktor = 0.0;
			oppvaskfaktor = 0.0;
			gulvvaskfaktor = 0.0;
		}
		else if (time == 7 )
		{
			varmeTapVannfaktor = 0.25;
			handvaksfaktor = 0.25;
			dusjfaktor = 0.5;
			badekarfaktor = 0.0;
			oppvaskfaktor = 0.0;
			gulvvaskfaktor = 0.0;
		}
		else if (time >= 8 && time <= 15 )
		{
			varmeTapVannfaktor = 0.0;
			handvaksfaktor = 0.0;
			dusjfaktor = 0.0;
			badekarfaktor = 0.0;
			oppvaskfaktor = 0.0;
			gulvvaskfaktor = 0.0;
		}
		else if (time >= 16 && time <= 20 )
		{
			varmeTapVannfaktor = 0.5;
			handvaksfaktor = 0.5;
			dusjfaktor = 0.0;
			badekarfaktor = 0.0;
			oppvaskfaktor = 1.0;
			gulvvaskfaktor = 0.0;
		}
		else if (time >= 21 && time <= 23 )
		{
			varmeTapVannfaktor = 0.25;
			handvaksfaktor = 0.25;
			dusjfaktor = 0.5;
			badekarfaktor = 1.0;
			oppvaskfaktor = 0.0;
			gulvvaskfaktor = 1.0;
		}
		
		
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
		
		// Kalkuler verdier avhengig av størrelse varmtvannstank	
		if (this.priBoilerSize == 0) {
			//this.energiForbruk = priBoilerPower;
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
		
		// Andre faste verdier i kWh	
		 dusj = 2.5;
		 badekar = (this.bathtubeSize * 0.04);
		 oppvask = 3; // Oppvaskmaskin utføres av ForbrukBrunevarer, dette er restvasken.
		 gulvvask = (this.pRomAreal * 0.04); // 100m2 = 50l - 40*C = ca 2 kWh
	 
	 
		 //beregner forbruk til tappevann 
		 // (effekt-tap oppvarming + forholdstall familie * (4 håndvask per pers per døgn + 1 dusj per pers per døgn + 
		 // oppvask hånd hver 5. dag(forhold om oppvaskmaskin hvitevare + 1 gulvvask hver uke) 
		forbrukVarmtVann = (varmeTapVann * varmeTapVannfaktor) + (multiplier * ((handVask * handvaksfaktor) + (dusj * dusjfaktor) + ((oppvask / 5) * oppvaskfaktor) + ( (gulvvask / 7) * gulvvaskfaktor ) ) ) ;
		
		// Oppvarming
		if (temperatur >= 17)
		{ 
			oppvarmfaktor = 0.05;
		}
		else if (temperatur >=4 && temperatur < 17)
		{ 
			oppvarmfaktor = 0.4;
		}
		else if (temperatur < 4)
		{
			oppvarmfaktor = 0.9;
		}
		
		privarm = ( ( (this.priHeat /24 ) * ( (100 - this.heatDiff)/100) ) /1000  );
		secvarm = ( (this.secHeat /24 ) * ( this.heatDiff/100) /1000  );
		el = ( (floorHeatEl * 150) /1000 );
		
		oppvarming = privarm + secvarm + el;
//		oppvarming = oppvarming * this.varmetap;
		oppvarming = oppvarming * oppvarmfaktor;
		
		this.energiForbruk = forbrukVarmtVann;
		
		System.out.format("Vann multiplier: %f\n", multiplier);
//		System.out.format("AntallPersoner: %f\n", antallPersoner);
		System.out.println("Tid " + time);
//		System.out.format("Energiforbruk varmetap: %f\n", varmetap);
//		System.out.format("Energiforbruk varmetap2: %f\n", varmetao);
		System.out.format("Faktor oppvarming: %f\n", oppvarmfaktor);
		System.out.format("Energiforbruk Vann denne timen: %f\n", forbrukVarmtVann);
		System.out.format("Energiforbruk oppvarming denne timen: %f\n", oppvarming);

		return true;
	}
	
}
