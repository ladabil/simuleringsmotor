package no.hin.student.y2013.grp2it.simuleringsmotor;

public class Soltilskudd extends BygningBase {

	public boolean doBeregning(long startTime, long lengde)
	{
		double soltilskudd = 0;
		double solareal = 0;
		double solskjermingsfaktor = 0;
		
		
		//effektivt vinduareal
		solareal = ((this.vinduDorAreal-2)*0.65*(1-0.2));
		//solskjermingsfaktor
		solskjermingsfaktor = 0.9*0.9*0.8;
		
		soltilskudd = solareal*solskjermingsfaktor;
		
		this.energiForbruk = soltilskudd;
		System.out.format("soltilskudd hvis sol: %f\n\n", this.energiForbruk);
		
		return true;
	}
	

}
