package no.hin.student.y2013.grp2it.simuleringsmotor;

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
	private double degreeOffset = 0.0f;
	private List<KlimaData> klimaDataList = new ArrayList<KlimaData>();
	
	public void parseXMLNodeElement(Node node)
	{
		if ( node.getNodeName().equalsIgnoreCase("sone") )
		{
			this.setSone(Integer.parseInt(node.getTextContent()));
			if ( showDebug() ) 
				System.out.printf("Klima.Sone: %d\n", this.getSone());
		}
		else if ( node.getNodeName().equalsIgnoreCase("maalestasjon") )
		{
			this.setMalestasjonsnr(Integer.parseInt(node.getTextContent()));
			if ( showDebug() ) 
				System.out.printf("Klima.Målestasjon: %d\n", this.getMalestasjonsnr());
		}
		else if ( node.getNodeName().equalsIgnoreCase("temperatureoffset") )
		{
			this.setDegreeOffset(Double.parseDouble(node.getTextContent()));
			if ( showDebug() ) 
				System.out.printf("Klima.DegreeOffset: %d\n", this.getDegreeOffset());
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
		KlimaData kd = findKlimaDataByTime(time);
		
		if ( kd == null ) 
		{
			System.out.println("Fant ikke klimadata for " + time);
			return 0.0f;
		}
		
		return kd.getTemperature() + this.getDegreeOffset();
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
	 * metode som leser inn en XML-fil fra wsKlima (http://eklima.met.no/wsKlima/start/start_no.html)
	 */
	
	public String getEKlimaURL()
	{
		StringBuffer retStrBuf = new StringBuffer();
//		String retStr = "";
		
    	Date ds = new Date();
    	ds.setTime(SimuleringsMotor.getTidsrom().getStartDateTime().getTime() - (3600 * 1000 * 36));

    	Date de = new Date();
    	de.setTime(SimuleringsMotor.getTidsrom().getEndDateTime().getTime() + (3600 * 1000 * 36));
    	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		retStrBuf.append("http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=0&format=&from=");
		
		retStrBuf.append(String.format("%s&to=%s&stations=%d", sdf.format(ds), sdf.format(de), this.getMalestasjonsnr()));
		
		System.out.printf("Måle: %s\n", this.getMalestasjonsnr());
		
		retStrBuf.append("&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=&months=&username=");

		if ( showDebug() ) 
			System.out.printf("URL: %s\n", retStrBuf.toString());
		
    	return retStrBuf.toString();
		
	}
	
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
//	    	Document doc = dBuilder.parse("http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=0&format=&from=2012-05-19&to=2012-06-23&stations=86600&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=&months=&username=");
	    	
	    	Document doc = dBuilder.parse(this.getEKlimaURL());

	    	// Observasjoner
	    	// Document doc = dBuilder.parse("http://eklima.met.no/metdata/MetDataService?invoke=getMetData&timeserietypeID=2&format=&from=2012-12-30&to=2012-12-31&stations=86600&elements=DD06%2CDD12%2CDD18%2CFFM%2CFFN%2CFFX%2CPOM%2CPON%2CPOX%2CPRM%2CPRN%2CPRX%2CRR%2CSA%2CSD%2CSLAG%2CTAM%2CTAN%2CTAX&hours=0%2C6%2C12%2C18&months=&username=");
	     
	    	if ( showDebug() ) System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     
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
	
	public boolean getAndParseEKlima()
	{
		try {
			Document doc = this.getXMLFromEKlima();
			this.parseWsKlimaXML(doc.getChildNodes(),null);
			this.calcMissingValues();
			this.debugPrintKlimaData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean parseWsKlimaXML(NodeList nodeList, Date fromDate)
	{
		KlimaData kd = null;
		
		for (int count = 0; count < nodeList.getLength(); count++) {

			Node currentNode = nodeList.item(count);

			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				if ( showDebug() ) System.out.println("\nNode Name =" + currentNode.getNodeName() + " [OPEN]");
//				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				if ( fromDate != null ) 
				{
					if ( currentNode.getNodeName().equalsIgnoreCase("location") )
					{
						parseWsKlimaXML(currentNode.getChildNodes(), fromDate);
						continue;
					}
				}
				else if ( currentNode.getNodeName().equalsIgnoreCase("location") && fromDate == null )
				{
					if ( showDebug() ) System.out.println("\nLocation found without fromDate! returing false");
					return false;
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
							
							if ( showDebug() ) System.out.println("------------------------------------------------");
							
							
							if ( fromDate != null )
							{
								if ( showDebug() ) System.out.println("------------------------------------------------");
								if ( showDebug() ) System.out.println("Laster klassen: " + currentNode.getNodeName());
								if ( showDebug() ) System.out.println("fromDate      : " + fromDate);
								if ( showDebug() ) System.out.println("------------------------------------------------");
	
								kd = findKlimaDataByTime(fromDate.getTime());
								
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
								kd.parseWeatherElement(currentNode.getChildNodes());

//								parseWsKlimaXMLWeatherElement(currentNode.getChildNodes(), fromDate);
							}
							else if ( node.getNodeName().equalsIgnoreCase("xsi:type") && node.getNodeValue().equalsIgnoreCase("ns2:no_met_metdata_Location"))
							{
								if ( showDebug() ) System.out.println("LOCATION! --------------: ");
								
								if (currentNode.hasChildNodes()) {
									parseWsKlimaXMLLocation(currentNode.getChildNodes(), fromDate);
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
		 
//				if ( fromDate != null )
//				{
				if ( showDebug() ) System.out.println("Node Name =" + currentNode.getNodeName() + " [CLOSE]");		
//				}
			}
		}

		return true;
	}	
	
	/*
	 * Parse location (med dens underdata)
	 */
	public boolean parseWsKlimaXMLLocation(NodeList nodeList, Date fromDate)
	{
		KlimaData kd = findKlimaDataByTime(fromDate.getTime());
		
		if ( kd == null )
		{
			if ( showDebug() ) System.out.println("Fant ikk KlimaData: " + fromDate);		
			return false;
		}
		
		for (int count = 0; count < nodeList.getLength(); count++) {

			Node currentNode = nodeList.item(count);

			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

				if ( currentNode.getNodeName().equalsIgnoreCase("weatherElement") )
				{
					kd.parseWeatherElement(currentNode.getChildNodes());
					continue;
				}
				else
				{
					// Sjekk om vi har attributter
					if (currentNode.hasAttributes()) {
						// Hent alle attributer og verdier..
						NamedNodeMap nodeMap = currentNode.getAttributes();
						
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							
							// Location ID
							if ( kd != null && currentNode.getNodeName().equalsIgnoreCase("id") && node.getNodeValue().equalsIgnoreCase("xsd:int") )
							{
								if ( Integer.parseInt(currentNode.getTextContent()) != this.getMalestasjonsnr() )
								{
									if ( showDebug() ) System.out.println("\nWrong Malestasjonsnr " + currentNode.getTextContent() + " != " + this.getMalestasjonsnr());
//									return false;
								}
								else
								{
									if ( showDebug() ) System.out.println("\nCorrect Malestasjonsnr " + currentNode.getTextContent() + " == " + this.getMalestasjonsnr());
								}
							}
	
						}
					}
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
	
	public void debugPrintKlimaData()
	{
		for ( int i=0; i<this.klimaDataList.size();i++)
		{
			System.out.println(this.klimaDataList.get(i));
		}
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
		
		for ( long i=(SimuleringsMotor.getTidsrom().getStartDateTime().getTime() - (3600 * 1000 * 60));i<(SimuleringsMotor.getTidsrom().getEndDateTime().getTime() + (3600 * 1000 * 60));i+=(3600 * 1000))
		{
			if ( showDebug() ) System.out.println("setter opp for i=" + i);
			
			this.klimaDataList.add(new KlimaData(i, (3600 * 1000)));
		}
	}
	
	/*
	 * Gå igjennom alle klimadata og kalkuler inn verdier der det mangler
	 */
	public void calcMissingValues()
	{
		KlimaData kd = null, kdLast = null, kdTmp = null;
		int lastKDidx = -1;
		
		for ( int i=0; i<this.klimaDataList.size();i++)
		{
			kd = this.klimaDataList.get(i);
			
			if ( kd.getDataStatus() == KlimaData.DataRead )
			{
				if ( lastKDidx != -1 )
				{
					kdLast = this.klimaDataList.get(lastKDidx);
					
					// Hvis det ikke finnes kl 00->03 etter kd.
					if ( this.klimaDataList.size() < (i + 2) )
					{
						return;
					}
					
					// lastKDidx + 4 = idx for lastKDidx 
					for ( int y=(lastKDidx + 3); y<=(i + 2); y++ )
					{
						kdTmp = this.klimaDataList.get(y);
						
						//
						// Beregn verdier mellom siste data lest og denne
						//
						if ( (y - lastKDidx) == 3 )
						{
							if ( showDebug() ) System.out.println(" == 3");
							kdTmp.setTemperature(kdLast.getTan());
						}
						else if ( (y - lastKDidx) < 13 )
						{
							if ( showDebug() ) System.out.println(" < 13");
//							kdTmp.setTemperature(( kdLast.getTax() - kdLast.getTan() ) * kdTmp.getTempFactor());
							kdTmp.setTemperature( kdLast.getTan() + ( ( kdLast.getTax() - kdLast.getTan() ) * kdTmp.getTempFactor() ) );

						}
						else if ( (y - lastKDidx) == 13 )
						{
							if ( showDebug() ) System.out.println(" == 13");
							kdTmp.setTemperature(kdLast.getTax());
						}
						else if ( (y - lastKDidx) < 27 )
						{
							if ( showDebug() ) System.out.println(" < 24");
//							kdTmp.setTemperature( ( kd.getTan() - kdLast.getTax() ) * kdTmp.getTempFactor() );
							kdTmp.setTemperature( kd.getTan() + ( ( kdLast.getTax() - kd.getTan() ) * kdTmp.getTempFactor() ) );

						}

						if ( showDebug() ) System.out.printf("kdLast.getTax()=%f, kdLast.getTan()=%f, TempFactory: %f - Temp: %f\n", kdLast.getTax(), kdLast.getTan(), kdTmp.getTempFactor(), kdTmp.getTemperature());
					}
				}
				
				lastKDidx = i;
			}
			else if ( kd.getDataStatus() == KlimaData.noData )
			{
//				System.out.println(this.klimaDataList.get(i));
			}
		
		}
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
	public int getMalestasjonsnrFromSone()
	{
		switch ( this.getSone() )
		{
			case 1: // 1 = Sør-norge, kyst =  KRISTIANSAND S. (E. W.)
				return 39190;
			case 2: // 2 = Sør-norge, innland = 66620 - RENNEBU - RAMSTAD
				return 66620;
			case 3: // 3 = Sør-norge, høyfjellet = 31411 -> RJUKA
				return 31411;
			case 4: // 4 = Midt-Norge, kyst = 68860 -> TRONDHEIM - VOLL
				return 68860;
			case 5: // 5 = Midt-Norge, innland = 63705 -> OPPDAL - SÆTER
				return 63705;
			case 6: // 6 = Nord-norge - kyst = 86600 = Stokmarknes
				return 86600;
			case 7: // 7 = Finnmark og innland Troms = Bardu: 88100 - BONES I BARDU
			default:
				return 88100;
		}
	}
	
	/*
	 * get og set-metoder for diverse variabler
	 */
	public int getMalestasjonsnr() {
		if ( malestasjonsnr <= 0 && this.getSone() > 0 )
		{
			// Hvis målestasjonsnr er mindre eller lik 0, bruk sone, hvis satt
			return this.getMalestasjonsnrFromSone();
		}
		
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

	public double getDegreeOffset() {
		return degreeOffset;
	}

	public void setDegreeOffset(double degreeOffset) {
		this.degreeOffset = degreeOffset;
	}
}
