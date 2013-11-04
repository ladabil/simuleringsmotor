package no.hin.student.y2013.grp2it.simuleringsmotor;

/*
 * ForbrukBrunevare - 08/10-2013
 */
public class ForbrukBrunevare extends BygningBase {

	
	int antallPersoner = SimuleringsMotor.getFamilie().getFamilieAntallPersoner();
	int personAlder = SimuleringsMotor.getFamilie().getFamilieAlder();
	double brunevarerForbruk = 0;

	//Utfører beregningene
	public boolean doBeregning(long startTime, long lengde)	
	{
		if ( this.antallPersoner <= 0 )
		{
			this.energiForbruk = 0;
			return true;
		}
		
		double  forbrukBrunevare = 0, dognbruk = 0;
		double tv, tvst, dvd, dvdst, radio, radiost, stereo, stereost, pc, mobil, internett;
		
//		Forbruksverdier i egen set (beregnet for senere admin endring via php
		tv = 100; // LED 58, PLASMA 85
		tvst = 0.7; // LED 0.3, PLASMA 0.3
		dvd = 11;
		dvdst = 1;
		radio = 9;
		radiost = 0.7;
		stereo = 25;
		stereost = 0.7;
		pc = 40;
		mobil = 30;
		internett = 9;
		
		
//		TODO: foreach løkke. Tilpassing for sammensatt familie (1 pers = familie med 1) og utregning
		if (this.antallPersoner > 1){
			if (this.personAlder < 65) { // Mer enn en person tilsier familie
				forbrukBrunevare = (
						(tv * 8)+
						(tvst * 16)+ // st = 24t - brukstid
						(dvd * 0.3)+
						(dvdst * 23.7)+
						(radio * 2)+
						(radiost * 22)+
						(stereo * 4)+
						(stereost * 20)+
						(pc * 3)+
						(mobil * 3)+
						(internett * 24)
						);
			}
			else if (this.personAlder > 65) { // Mer enn en person tilsier familie
				forbrukBrunevare = (
						(tv * 10)+
						(tvst * 14)+ // st = 24t - brukstid
						(dvd * 0)+
						(dvdst * 0)+
						(radio * 8)+
						(radiost * 16)+
						(stereo * 0)+
						(stereost * 0)+
						(pc * 2)+
						(mobil * 1)+
						(internett * 24)
						);
		}
		else {
			if (this.personAlder >= 13 && this.personAlder <= 25){
							forbrukBrunevare = (
										(tv * 8)+
										(tvst * 16)+ // st = 24t - brukstid
										(dvd * 1)+
										(dvdst * 19)+
										(radio * 0)+
										(radiost * 0)+
										(stereo * 4)+
										(stereost * 20)+
										(pc * 5)+
										(mobil * 3)+
										(internett * 24)
										);
			}
		else {
			if (this.personAlder > 25 && this.personAlder <= 65){
							forbrukBrunevare = (
										(tv * 10)+
										(tvst * 14)+ // st = 24t - brukstid
										(dvd * 1)+
										(dvdst * 19)+
										(radio * 10)+
										(radiost * 14)+
										(stereo * 0)+
										(stereost * 0)+
										(pc * 4)+
										(mobil * 2)+
										(internett * 24)
										);
				}
			}		
		}
		}
		
		this.energiForbruk = brunevarerForbruk = (forbrukBrunevare / 1000); // omgjøring til kWh
		
		System.out.format("AntallPersoner: %f\n", antallPersoner);
		System.out.format("Energiforbruk brunevarer per døgn: %f\n", this.energiForbruk);

		return true;
	
	
	}
}
