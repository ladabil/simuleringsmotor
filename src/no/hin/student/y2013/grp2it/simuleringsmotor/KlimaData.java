package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Date;

public class KlimaData {
	private Date startDateTime = null;
	private long length = 0;
	private double tam = -9999; // TAM = døgnmiddeltemperatur
	private double tan = -9999; // TAN = minimumstemperatur 
	private double tax = -9999; // TAX = maksimumstemperatur
	
	public KlimaData(long startTimeAsLong, long length)
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

	public double getTam() {
		return tam;
	}

	public void setTam(double tam) {
		this.tam = tam;
	}

	public double getTan() {
		return tan;
	}

	public void setTan(double tan) {
		this.tan = tan;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
}
