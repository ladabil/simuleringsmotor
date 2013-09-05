package no.hin.student.y2013.grp2it.simuleringsmotor;

public class Enebolig extends BygningBase {
	/*
	 * Utfører beregninger på ett gitt tidspunkt
	 */
	public boolean doBeregning(long startTime, long lengde)
	{
		double temperatur = SimuleringsMotor.getKlima().getTemperatureForTime(startTime);

		System.out.format("energiforbruk 1: %f\n", this.energiForbruk);
		
		this.energiForbruk = (long) ((((1 + (Math.random() * 5)) * this.bruttoAreal)) * (temperatur / 10));
		this.energiForbruk += (long) ((((1 + (Math.random() * 5)) * this.pRomAreal)) * (temperatur / 10));
		
		System.out.format("energiforbruk 2: %f\n\n", this.energiForbruk);

		return true;
	}
}
