package no.hin.student.y2013.grp2it.simuleringsmotor;

public class Enebolig extends BygningBase {
	/*
	 * Utf�rer beregninger p� ett gitt tidspunkt
	 */
	public boolean doBeregning(long startTime, long lengde)
	{
		this.energiForbruk = (long) (((1 + (Math.random() * 5)) * this.bruttoAreal));
		this.energiForbruk += (long) (((1 + (Math.random() * 5)) * this.pRomAreal));
		
		return true;
	}
}
