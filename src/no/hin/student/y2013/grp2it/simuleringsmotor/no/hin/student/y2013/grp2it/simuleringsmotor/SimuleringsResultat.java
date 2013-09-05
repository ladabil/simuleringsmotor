package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Date;

public class SimuleringsResultat extends SimulatorBase {
	private boolean simulated = false; // true hvis simulering er ferdig for dette tidspunktet
	private Date startDateTime = null;
	private long length = 0;
	private double energiForbruk = 0;
	private double varmetap = 0; //***J***
	
	public SimuleringsResultat(long startTimeAsLong, long length)
	{
		this.length = length;
		this.startDateTime = new Date(startTimeAsLong);
	}
	
	// Access methods
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public boolean isSimulated() {
		return simulated;
	}
	public void setSimulated(boolean simulated) {
		this.simulated = simulated;
	}

	public double getEnergiForbruk() {
		return energiForbruk;
	}

	public void setEnergiForbruk(double energiForbruk) {
		this.energiForbruk = energiForbruk;
	}
	
	public double getVarmetap() { //***J***
		return varmetap;
	}
	
	public void setVarmetap(double varmetap) { //***J***
		this.varmetap = varmetap;
	}
}
