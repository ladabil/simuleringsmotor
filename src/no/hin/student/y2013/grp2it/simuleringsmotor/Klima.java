package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
	     
	    	Document doc = dBuilder.parse("http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=0&format=&from=2012-12-30&to=2012-12-31&stations=86600&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=&months=&username=");
	     
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
	
	public boolean parseWsKlimaXML(NodeList nodeList)
	{
		for (int count = 0; count < nodeList.getLength(); count++) {
			  
			Node currentNode = nodeList.item(count);
		 
			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + currentNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				if (currentNode.hasChildNodes()) {
					parseWsKlimaXML(currentNode.getChildNodes());
				}

				// Sjekk om vi har attributter
				if (currentNode.hasAttributes()) {

					// Hent alle attributer og verdier..
					NamedNodeMap nodeMap = currentNode.getAttributes();
					
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);

						// Hvis attributten heter type og verdien er class, last inn klassen og fortsett prosesseringen herfra
						if ( node.getNodeName().equalsIgnoreCase("type") && node.getNodeValue().equalsIgnoreCase("class"))
						{
							// Last klasse
							System.out.println("------------------------------------------------");
							System.out.println("Laster klassen: " + currentNode.getNodeName());
							System.out.println("------------------------------------------------");
							
/*
							tmpSimBase = this.loadClass(currentNode.getNodeName());
							
							if ( tmpSimBase == null )
							{
								continue;
							}
								
							if (currentNode.hasChildNodes()) {
								tmpSimBase.setXMLNodeList(currentNode.getChildNodes());
							}
							
							simulatorBaseList.add(tmpSimBase);
							
							tmpSimBase.parseXML();
							
							// Tidsrom:
							// Sjekk om vi er ett "root+1"-element (dvs rett under <simulering>)
							if ( isRoot = true )
							{
								// Spesialhåndtering av tidsrom - legge denne til på root av simuleringsmotoret
								if ( currentNode.getNodeName().equalsIgnoreCase("tidsrom") )
								{
									System.out.println("Fant Tidsrom-tag (rett under simulering): " + currentNode.getNodeName());
									SimuleringsMotor.setTidsrom((Tidsrom) tmpSimBase);
								}
								else if ( currentNode.getNodeName().equalsIgnoreCase("klima") )
								{
									System.out.println("Fant Klima-tag (rett under simulering): " + currentNode.getNodeName());
									SimuleringsMotor.setKlima((Klima) tmpSimBase);
								}
							}
							
							System.out.println(tmpSimBase);
							*/
						}
		 
//						System.out.println("attr name : " + node.getNodeName());
					}
				}
		 
				System.out.println("Node Name =" + currentNode.getNodeName() + " [CLOSE]");		
			}
		}

		return true;
	}	
	
	/*
	 * henter fram klimadata vi ønsker
	 * ut fra time (starttid)
	 */
	public SimuleringsResultat findSimuleringsResultatByTime(long timeAsLong)
	{
		Iterator<SimuleringsResultat> srIt = simulatorResultList.iterator();
		
		while (srIt.hasNext() )
		{
			SimuleringsResultat tmpSimRes=(SimuleringsResultat)srIt.next();
			
			if ( tmpSimRes.getStartDateTime().getTime() == timeAsLong )
			{
				return tmpSimRes;
			}
		}
		
		return null;
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
