package no.hin.student.y2013.grp2it.simuleringsmotor;


public class Varmetap extends BygningBase {

	public boolean doBeregning(long startTime, long lengde)
	{
		double temperatur = SimuleringsMotor.getKlima().getTemperatureForTime(startTime);
		double gjenvinningsgrad = 0, kuldebro = 0, uvegg = 0, utak = 0, uvinduD�r = 0, ugulv = 0, lekkasjetall = 0; //Varmetapsverdier (U-verdier)
		
		//Angir verdiene i henhold til byggstandard, og beregner varmetapet
		if (this.byggstandard < 1987) {
			utak = 0.23;
			uvegg = 0.45;
			uvinduD�r = 0;
			ugulv = 0.30;
			kuldebro = 0.05;
			lekkasjetall = 5;
			gjenvinningsgrad = 0;
			
		}
		
		if (this.byggstandard >= 1987 && byggstandard < 1997) {
			utak = 0.20;
			uvegg = 0.30;
			ugulv = 0.30;
			uvinduD�r = 2.4;
			kuldebro = 0.05;
			lekkasjetall = 4;
			gjenvinningsgrad = 0.5;
		}
		
		if (this.byggstandard > 1997) {
			utak = 0.15;
			uvegg = 0.22;
			ugulv = 0.15;
			uvinduD�r = 1.6;
			kuldebro = 0.05;
			lekkasjetall = 2;
			gjenvinningsgrad = 0.6;
		}
		
		//beregner varmetapet gjennom tak, vegg, gulv, vinduer og d�rer, kuldebro, infiltrasjon, ventilasjon
		
		//Resultatet er i W/K, som vil si antall watt tapt per time, og skal ganges med antall grader under 17C. 
		this.varmetap = (yttertakAreal*utak)+(ytterveggAreal*uvegg)+(bruttoAreal*ugulv)+(vinduD�rAreal*uvinduD�r)+(kuldebro*bruttoAreal)+(0.33*lekkasjetall*luftVolum*0.07)+(0.33*(luftVolum*0.5)*(1-gjenvinningsgrad));
		
		if (temperatur<17){
			this.varmetap = this.varmetap*(17-temperatur);
		}
		//Ved 17 grader og opp er det ikke noe varmetap
		if (temperatur>=17){
			this.varmetap = 0;
		}
		
		System.out.format("varmetap: %f\n\n", this.varmetap);
		
		return true;
	}
	

}