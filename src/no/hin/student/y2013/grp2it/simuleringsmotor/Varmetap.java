package no.hin.student.y2013.grp2it.simuleringsmotor;


public class Varmetap extends BygningBase {

	public boolean doBeregning(long startTime, long lengde)
	{
		double temperatur = SimuleringsMotor.getKlima().getTemperatureForTime(startTime);
		double gjenvinningsgrad = 0, kuldebro = 0, uvegg = 0, utak = 0, uvinduDor = 0, ugulv = 0, lekkasjetall = 0; //Varmetapsverdier (U-verdier)
		double varmetap = 0;
		double oppvarmingsbehov = 0;
		//Angir verdiene i henhold til byggstandard, og beregner varmetapet
		if (this.byggstandard < 1987) {
			utak = 0.23;
			uvegg = 0.45;
			uvinduDor = 0;
			ugulv = 0.30;
			kuldebro = 0.05;
			lekkasjetall = 5;
			gjenvinningsgrad = 0;
			
		}
		
		if (this.byggstandard >= 1987 && this.byggstandard < 1997) {
			utak = 0.20;
			uvegg = 0.30;
			ugulv = 0.30;
			uvinduDor = 2.4;
			kuldebro = 0.05;
			lekkasjetall = 4;
			gjenvinningsgrad = 0.5;
		}
		
		if (this.byggstandard > 1997) {
			utak = 0.15;
			uvegg = 0.22;
			ugulv = 0.15;
			uvinduDor = 1.6;
			kuldebro = 0.05;
			lekkasjetall = 2;
			gjenvinningsgrad = 0.6;
		}
		
		//beregner varmetapet gjennom tak, vegg, gulv, vinduer og dører, kuldebro, infiltrasjon, ventilasjon
		
		//Resultatet er i W/K, som vil si antall watt tapt per time, og skal ganges med antall grader under 17C. 
		varmetap = (this.yttertakAreal*utak)+(this.ytterveggAreal*uvegg)+(this.bruttoAreal*ugulv)+(this.vinduDorAreal*uvinduDor)+(kuldebro*this.bruttoAreal)+(0.33*lekkasjetall*this.luftVolum*0.07)+(0.33*(this.luftVolum*0.5)*(1-gjenvinningsgrad));
		if (temperatur<17){
			varmetap = varmetap*(17-temperatur);
		}
		//Ved 17 grader og opp er det ikke noe varmetap
		if (temperatur>=17){
			varmetap = 0;
		}

		System.out.format("Varmetap: %f - Varmetap/1000: %f\n\n", varmetap, (varmetap / 1000) );
		this.energiForbruk = (varmetap / 1000);
		
		//beregning for oppvamingsbehov. Standardtallet for temperatur er satt fra 17 til ønsket temperatur, og algoritmen beregner bare pRomAreal istedet for brutto.
		oppvarmingsbehov = (this.yttertakAreal*utak)+(this.ytterveggAreal*uvegg)+(this.pRomAreal*ugulv)+(this.vinduDorAreal*uvinduDor)+(kuldebro*this.pRomAreal)+(0.33*lekkasjetall*this.luftVolum*0.07)+(0.33*(this.luftVolum*0.5)*(1-gjenvinningsgrad));
		if (temperatur<this.onsketTemp){
			oppvarmingsbehov = oppvarmingsbehov*( (this.onsketTemp-17)-temperatur);
		}
		//Ved 17 grader og opp er det ikke noe varmetap
		if (temperatur>=this.onsketTemp){
			oppvarmingsbehov = 0;
		}
		
		System.out.format("Oppvarmingsbehov: %f - Oppvarmingsbehov/1000: %f - Temperatur = %f\n\n", oppvarmingsbehov, (oppvarmingsbehov/1000), temperatur);
		this.energiForbruk += (oppvarmingsbehov / 1000);
		
		return true;
	}
	

}
