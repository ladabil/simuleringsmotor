package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Klima - 20/5-2013
 */
public class Klima extends SimulatorBase {
	private int sone = 0;
	private int malestasjonsnr = 0;
	private List<KlimaData> klimaDataList = new ArrayList<KlimaData>();
	

	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("sone") )
		{
			this.setSone(Integer.parseInt(node.getTextContent()));
		}
		else if ( node.getNodeName().equalsIgnoreCase("malestasjonsnr") )
		{
			this.setMalestasjonsnr(Integer.parseInt(node.getTextContent()));
		}
		else
		{
			super.parseXMLNodeElement(node);
		}
	}
	
	/*
	 * Returnerer temperaturen for en gitt tid.
	 * 
	 * Benytter "live"-data for en målestasjon på wsKlima (http://eklima.met.no/wsKlima/start/start_no.html) (//TODO: implementer)
	 * Eller sonen valgt (predefinert klima).
	 * 
	 * Hvis både sone og målestasjonsnr. er spesifisert, bruk målestasjonsdata hvis data eksisterer,
	 * og fyll inn evt. manglende data med data fra sonen valgt.
	 */
	public double getTemperatureForTime(long time)
	{
		double temp = (-15 + (Math.random() * 40));
		
		System.out.println("Temperatur: " + temp);
		
		return temp;
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(String.format("+++++ Type: %s\n", this.getClass().getName()));
		str.append(String.format("Klimasone          = %d\n", this.getSone()));
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}

	
	/*
	 * Predefinerte klimasoner:
	 * 
	 * 1 = Sør-Norge, innland
	 * 2 = Sør-Norge, kyst
	 * 3 = Sør-Norge, høyfjell
	 * 4 = Midt-Norge, kyst
	 * 5 = Midt-Norge, innland
	 * 6 = Nord-Norge, kyst
	 * 7 = Finnmark og indre Troms
	 */

	/*
	 * metode som leser inn en XML-fil fra wsKlima (http://eklima.met.no/wsKlima/start/start_no.html)
	 */
	
	/*
	 * Last ned en klimafil fra eklima.met.no (wsKlima)
	 * 	http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=0&format=&from=2012-01-01&to=2012-12-31&stations=18700%2C50540%2C86600&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=&months=8%2C9&username=
	 */
	public Document getXMLFromEKlima()
	{
	    try {
	    	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
	                                 .newDocumentBuilder();
	     
	    	// Døgnverdier
	    	Document doc = dBuilder.parse("http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=0&format=&from=2012-05-19&to=2012-05-23&stations=86600&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=&months=&username=");
	    	
	    	// Observasjoner
	    	// Document doc = dBuilder.parse("http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=2&format=&from=2012-12-30&to=2012-12-31&stations=86600&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=0%2C6%2C12%2C18&months=&username=");
	     
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     
/*	    	if ( !doc.getDocumentElement().getNodeName().equalsIgnoreCase("simulering") )
	    	{
	    		System.out.println("feil i root tag i xml-filen, forventer \"simulering\" - fikk: " + doc.getDocumentElement().getNodeName());
	    		return;
	    	}
*/	    	

	    	if (doc.hasChildNodes()) {
	    		return doc;
	    	}
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }

	    return null;
	}
	
	public boolean parseWsKlimaXML(NodeList nodeList, Date fromDate)
	{
		for (int count = 0; count < nodeList.getLength(); count++) {
			  
			Node currentNode = nodeList.item(count);
		 
			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + currentNode.getNodeName() + " [OPEN]");
//				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				if ( fromDate != null ) 
				{
					if ( currentNode.getNodeName().equalsIgnoreCase("location") )
					{
						parseWsKlimaXML(currentNode.getChildNodes(), fromDate);
					}
				}
				
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
								fromDate = getFromTime(currentNode);
							}
							
							System.out.println("------------------------------------------------");
							
							
							if ( fromDate != null )
							{
								System.out.println("------------------------------------------------");
								System.out.println("Laster klassen: " + currentNode.getNodeName());
								System.out.println("fromDate      : " + fromDate);
								System.out.println("------------------------------------------------");
	
								KlimaData kd = findKlimaDataByTime(fromDate.getTime());
								
								if ( kd == null )
								{
									System.out.println("Klimadata not found for " + fromDate.getTime() + " - string version: " + fromDate);
									System.exit(300);
								}
								
								if (currentNode.hasChildNodes()) {
									parseWsKlimaXML(currentNode.getChildNodes(), fromDate);
								}
								
								fromDate = null;
								continue;
							}
						}
						
						
						if ( fromDate != null )
						{
							if ( node.getNodeName().equalsIgnoreCase("xsi:type") && node.getNodeValue().equalsIgnoreCase("ns2:no_met_metdata_WeatherElement"))
							{
							
							}
							else if ( node.getNodeName().equalsIgnoreCase("xsi:type") && node.getNodeValue().equalsIgnoreCase("ns2:no_met_metdata_Location"))
							{
								System.out.println("LOCATION! --------------");
								
								if (currentNode.hasChildNodes()) {
									parseWsKlimaXML(currentNode.getChildNodes(), fromDate);
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
						}
					}
					
					if ( fromDate == null && currentNode.hasChildNodes()) {
						parseWsKlimaXML(currentNode.getChildNodes(), null);
					}
					
					
				}
				else
				{
					if ( fromDate == null && currentNode.hasChildNodes()) {
						parseWsKlimaXML(currentNode.getChildNodes(), null);
					}
				}
		 
				if ( fromDate != null )
				{
					System.out.println("Node Name =" + currentNode.getNodeName() + " [CLOSE]");		
				}
			}
		}

		return true;
	}	
	
	public Date getFromTime(Node timeStampNode)
	{
		Date retDate = null;
		
		for (int count = 0; count < timeStampNode.getChildNodes().getLength(); count++) {
			Node currentNode = timeStampNode.getChildNodes().item(count);
			
			if ( currentNode.getNodeName().equalsIgnoreCase("from") )
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				// Eksempel på format: 2012-09-21T00:00:00.000Z
				
				try {
					retDate = sdf.parse(currentNode.getTextContent());
				} catch (DOMException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			}
		}
		
		
		return retDate;
	}
	
//	public NodeList getWeatherElements
	
	/*
	 * henter fram klimadata vi ønsker
	 * ut fra time (starttid)
	 */
	public KlimaData findKlimaDataByTime(long timeAsLong)
	{
		if ( klimaDataList.isEmpty() )
		{
			setupklimaDataList();
		}
		
		
		Iterator<KlimaData> kdIt = klimaDataList.iterator();
		
		while (kdIt.hasNext() )
		{
			KlimaData tmpKlimaData=(KlimaData)kdIt.next();
			
			if ( tmpKlimaData.getStartDateTime().getTime() == timeAsLong )
			{
				return tmpKlimaData;
			}
		}
		
		return null;
	}	
	
	/*
	 * Oppretter simuleringsresultat-objekter for alle tidspunkter i simuleringen..
	 */
	public void setupklimaDataList()
	{
		if ( !this.klimaDataList.isEmpty() )
		{
			this.klimaDataList.clear();
		}
		
		for ( long i=(SimuleringsMotor.getTidsrom().getStartDateTime().getTime() - (3600 * 1000 * 96));i<(SimuleringsMotor.getTidsrom().getEndDateTime().getTime() + (3600 * 1000 * 96));i+=SimuleringsMotor.getTidsrom().getOpplosningInMs())
		{
			System.out.println("setter opp for i=" + i);
			
			this.klimaDataList.add(new KlimaData(i, SimuleringsMotor.getTidsrom().getOpplosningInMs()));
		}
	}
	
	/*
	 * get og set-metoder for diverse variabler
	 */
	public int getMalestasjonsnr() {
		return malestasjonsnr;
	}

	public void setMalestasjonsnr(int malestasjonsnr) {
		this.malestasjonsnr = malestasjonsnr;
	}

	public int getSone() {
		return sone;
	}

	public void setSone(int sone) {
		this.sone = sone;
	}

	public List<KlimaData> getKlimaDataList() {
		return klimaDataList;
	}

	public void setKlimaDataList(List<KlimaData> klimaDataList) {
		this.klimaDataList = klimaDataList;
	}
}
