package no.hin.student.y2013.grp2it.simuleringsmotor;

public class IndreTilskudd extends BygningBase
{
	int antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	int personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
	String virke = SimuleringsMotor.getFamilie().getFamilieVirke();
	
	
	public boolean doBeregning(long startTime, long lengde)
	{
		float kropsvarmeVoken = 100, krobsvarmeSove = 80;
		double indreTilskud = 0;
		
		if(virke.equalsIgnoreCase("barnehage/skole"))
		{
			if (personAlder >= 0 && personAlder <= 5)
			{
				if (startTime >=16 && startTime <=24)
				{
					indreTilskud += (kropsvarmeVoken*antallPersoner);
				}
			}
			if (personAlder >= 6 /*&& personAlder <=16 */)
			{
				if (startTime >=15 && startTime <=24)
				{
					indreTilskud += (kropsvarmeVoken*antallPersoner);
				}
			}
			
			
		}
		else if (virke.equalsIgnoreCase("arbeid"))
		{
			if (startTime >=16 && startTime <=24)
			{
				indreTilskud += (kropsvarmeVoken*antallPersoner);
			}
		}
		else if (virke.equalsIgnoreCase("uføretrygdet/pensjonist"))
		{
			if (startTime >= 8 && startTime <=24)
			{
				indreTilskud += (kropsvarmeVoken*antallPersoner);
			}
		}
		
		if (startTime >= 0 && startTime <=8)
		{
			indreTilskud += (krobsvarmeSove*antallPersoner);
		}
		
		indreTilskud += (super.brunevarerForbruk + super.hvitevarerForbruk);
		
		
		this.energiForbruk = indreTilskud;
		
		System.out.format("Tilført indre vame i watt: %f", indreTilskud);
		
		return true;
	}
}
