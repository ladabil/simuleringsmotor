package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Formatter;

public class Soltilskudd extends BygningBase {

	public boolean doBeregning(long startTime, long lengde)
	{
		double soltilskudd = 0;
		double solareal = 0;
		double solskjermingsfaktor = 0;
		
		if ( this.vinduDorAreal <= 2 )
		{
			return true;
		}
		
		
		Formatter tf = new Formatter();
		int time = Integer.parseInt((tf.format("%TH", startTime)).toString());

		if ( time < 9 || time > 17 )
		{
			// Kun sol mellom 09:00 og 17:00
			return false;
		}
		
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
