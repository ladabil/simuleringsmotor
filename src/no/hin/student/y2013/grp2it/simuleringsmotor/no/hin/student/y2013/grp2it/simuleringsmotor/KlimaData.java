package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.Date;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	/*
	 * 
	 */
	public boolean parseWeatherElement(NodeList nodeList)
	{
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node currentNode = nodeList.item(count);
		 
			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + currentNode.getNodeName() + " [OPEN]");
//				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				// Sjekk om vi har attributter
				if (currentNode.hasAttributes()) {
					// Hent alle attributer og verdier..
					NamedNodeMap nodeMap = currentNode.getAttributes();
					
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);

						// Hvis attributten heter type og verdien er class, last inn klassen og fortsett prosesseringen herfra
						if ( node.getNodeName().equalsIgnoreCase("xsi:type") && node.getNodeValue().equalsIgnoreCase("ns2:no_met_metdata_TimeStamp"))
						{
							if (currentNode.hasChildNodes()) {
								//
							}
							
							System.out.println("------------------------------------------------");
							
							
							System.out.println("------------------------------------------------");
							System.out.println("Laster klassen: " + currentNode.getNodeName());
//							System.out.println("fromDate      : " + fromDate);
							System.out.println("------------------------------------------------");

//							KlimaData kd = findKlimaDataByTime(fromDate.getTime());
							
							
							if (currentNode.hasChildNodes()) {
	//							parseWsKlimaXML(currentNode.getChildNodes(), fromDate);
							}
						}
						
						
//						if ( fromDate != null )
//						{
							if ( node.getNodeName().equalsIgnoreCase("xsi:type") && node.getNodeValue().equalsIgnoreCase("ns2:no_met_metdata_WeatherElement"))
							{
							
							}
							else if ( node.getNodeName().equalsIgnoreCase("xsi:type") && node.getNodeValue().equalsIgnoreCase("ns2:no_met_metdata_Location"))
							{
								System.out.println("LOCATION! --------------");
								
								if (currentNode.hasChildNodes()) {
//									parseWsKlimaXML(currentNode.getChildNodes(), fromDate);
									break;
								}
								
								/* parse weatherElement
								 * <item xsi:type="ns2:no_met_metdata_WeatherElement">
								 *		<id xsi:type="xsd:string">DD06</id>
								 *		<quality xsi:type="xsd:int">0</quality>
								 *		<value xsi:type="xsd:string">43</value>
								 * </item>
								 */
								
								
							}
						//}
					}
					
				}
				else
				{
				}
		 
			}
		}

		return true;
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
