package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Date;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KlimaData extends SimulatorBase {
	private Date startDateTime = null;
	private long length = 0;
	private double tam = -9999; // TAM = døgnmiddeltemperatur
	private double tan = -9999; // TAN = minimumstemperatur 
	private double tax = -9999; // TAX = maksimumstemperatur
	private double temperature = -9999; // Beregnet temperature for dette tidpunktet / perioden
	private int dataStatus = KlimaData.noData;

	private double degreeOffset = 0.0f;
	
	static public final int noData = 0;
	static public final int DataRead = 10;
	static public final int DataCalculated = 20;
	
	public KlimaData(long startTimeAsLong, long length)
	{
		this.length = length;
		this.startDateTime = new Date(startTimeAsLong);
	}
	
	/*
	 * Returner hviliken faktor som skal brukes for å returnere rett temperatur ut fra TAX/TAN-differansen 
	 */
	public double getTempFactor()
	{
		double retVal = 0.0f;
		
		switch ( startDateTime.getHours() )
		{
			case 1:
				retVal = 0.15f;
				break;
			case 2:
				retVal = 0.05f;
				break;
			case 3:
				retVal = 1.0f;
				break;
			case 4:
				retVal = 0.0f;
				break;
			case 5:
				retVal = 0.02f;
				break;
			case 6:
				retVal = 0.05f;
				break;
			case 7:
				retVal = 0.12f;
				break;
			case 8:
				retVal = 0.25f;
				break;
			case 9:
				retVal = 0.4f;
				break;
			case 10:
				retVal = 0.6f;
				break;
			case 11:
				retVal = 0.8f;
				break;
			case 12:
				retVal = 0.95f;
				break;
			case 13:
				retVal = 1.0f;
				break;
			case 14:
				retVal = 1.0f;
				break;
			case 15:
				retVal = 0.98f;
				break;
			case 16:
				retVal = 0.95f;
				break;
			case 17:
				retVal = 0.92f;
				break;
			case 18:
				retVal = 0.88f;
				break;
			case 19:
				retVal = 0.83f;
				break;
			case 20:
				retVal = 0.76f;
				break;
			case 21:
				retVal = 0.68f;
				break;
			case 22:
				retVal = 0.58f;
				break;
			case 23:
				retVal = 0.47f;
				break;
			default:
			case 0:
				retVal = 0.32f;
				break;
		}
		
		if ( showDebug() ) System.out.printf("Retval: %f - getHours: %d\n", retVal, startDateTime.getHours());
		
		return retVal;
	}
	
	/*
	 * Parse weatherElement
	 * 
	 * <item xsi:type="ns2:no_met_metdata_WeatherElement">
	 * 		<id xsi:type="xsd:string">TAX</id>
	 * 		<quality xsi:type="xsd:int">2</quality>
	 * 		<value xsi:type="xsd:string">6.4</value>
	 * </item>
	 * 
	 */
	public boolean parseWeatherElement(NodeList nodeList)
	{
		String id = "";
		String value = "";
		
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node currentNode = nodeList.item(count);
		 
			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				if ( showDebug() ) System.out.println("\nKlimaData::parseWeatherElement(): Node Name =" + currentNode.getNodeName() + " [OPEN]");
//				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				if ( currentNode.getNodeName().equalsIgnoreCase("item") )
				{
					if ( currentNode.getChildNodes() != null )
					{
						this.parseWeatherElement(currentNode.getChildNodes());
						continue;
					}
					else
					{
						return false;
					}
				}
				else if ( currentNode.getNodeName().equalsIgnoreCase("id") )
				{
					id = currentNode.getTextContent();
				}
				else if ( currentNode.getNodeName().equalsIgnoreCase("value") )
				{
					value = currentNode.getTextContent();
				}
			}
		}
		
		// VI har en ID, sett verdi i rett variabel ut fra denne
		if ( id.length() > 0 )
		{
			this.setDataStatus(KlimaData.DataRead);
			
			if ( id.equalsIgnoreCase("TAX") )
			{
				this.setTax(Double.parseDouble(value));
			}
			else if ( id.equalsIgnoreCase("TAN") )
			{
				this.setTan(Double.parseDouble(value));
			}
			else if ( id.equalsIgnoreCase("TAM") )
			{
				this.setTam(Double.parseDouble(value));
			}
		}

		return true;
	}		
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(startDateTime.toString());
		sb.append(" - ");
		sb.append("Length:");
		sb.append(this.getLength());
		sb.append(" - ");
		sb.append("DataStatus:");
		sb.append(this.getDataStatusAsStr());
		sb.append(" - ");
		sb.append("TAX:");
		sb.append(this.getTax());
		sb.append(" - ");
		sb.append("TAM:");
		sb.append(this.getTam());
		sb.append(" - ");
		sb.append("TAN:");
		sb.append(this.getTan());
		sb.append(" - ");
		sb.append("Beregnet temp:");
		sb.append(this.getTemperature());
		
		return sb.toString();
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

	public int getDataStatus() {
		return dataStatus;
	}

	public String getDataStatusAsStr() {
		switch ( dataStatus )
		{
			case KlimaData.DataRead:
				return "DataRead";
			case KlimaData.DataCalculated:
				return "DataCalculated";
			default:
			case KlimaData.noData:
				return "noData";
				
		}
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}

	public double getTemperature() {
		return temperature + this.getDegreeOffset();
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getTemperatureWithoutOffest()
	{
		return temperature;
	}

	public double getDegreeOffset() {
		return degreeOffset;
	}

	public void setDegreeOffset(double degreeOffset) {
		this.degreeOffset = degreeOffset;
	}
}
