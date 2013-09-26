package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * 11/09/2013 - Gruppe 2 IT
 */

public class SimulatorBase {
	public static final String packageName = "no.hin.student.y2013.grp2it.simuleringsmotor";
	
	protected NodeList nodeList;
	protected List<SimulatorBase> simulatorBaseList = new ArrayList<SimulatorBase>();
	protected List<SimuleringsResultat> simulatorResultList = new ArrayList<SimuleringsResultat>();
	
	private boolean showDebug = false;
	
	protected double energiForbruk = 0;
	protected double varmetap = 0; //***J***
	
	public SimulatorBase()
	{
		this.nodeList = null;
		this.energiForbruk = 0;
		this.varmetap = 0; //***J***
	}
	
	public double getBeregning()
	{
		return this.energiForbruk;
	}
	
	public double getVarmetap() //***J***
	{
		return this.varmetap;
	
	}
	
	// Setter nodelisten (noder i XML-filen som tilhører meg..)
	public void setXMLNodeList(NodeList nodeList) 
	{
		this.nodeList = nodeList;
	}
	  
	// Hent nodelisten
	public NodeList getXMLNodeList()
	{
		return this.nodeList;
	}
	
	// parse XML-nodelisten..
	public boolean parseXML()
	{
		return parseXML(false);
	}
	
	public boolean parseXML(boolean isRoot)
	{
		SimulatorBase tmpSimBase;
		
		for (int count = 0; count < this.nodeList.getLength(); count++) {
			  
			Node currentNode = this.nodeList.item(count);
		 
			// Sjekk at nodetypen er ett element..
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + currentNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + currentNode.getTextContent());
		 
				this.parseXMLNodeElement(currentNode);

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
								else if ( currentNode.getNodeName().equalsIgnoreCase("familie") )
								{
									System.out.println("Fant Familie-tag (rett under simulering): " + currentNode.getNodeName());
									SimuleringsMotor.setFamilie((Familie) tmpSimBase);
								}
							}
							
							System.out.println(tmpSimBase);
						}
		 
//						System.out.println("attr name : " + node.getNodeName());
					}
				}
		 
				System.out.println("Node Name =" + currentNode.getNodeName() + " [CLOSE]");		
			}
		}

		return true;
	}
	
	public void parseXMLNodeElement(Node node)
	{
		System.out.println("-- parseXMLNodeElement(Node node): Unhandled tag: " + node.getNodeName());
	}
	
	
	// Laster inn en ny klasse
	public SimulatorBase loadClass(String className)
	{
		String fullClassName = packageName + "." + className;
		
		Class loadedClass = null;
		SimulatorBase baseClass = null;
		
		try {
			loadedClass = Class.forName(fullClassName);
			baseClass = (SimulatorBase) loadedClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baseClass;
	}	
	
	/*
	 * Utfører beregninger på ett gitt tidspunkt
	 */
	public boolean doBeregning(long startTime, long lengde)
	{
		this.energiForbruk = 0;
		this.energiForbruk = (long) (1 + (Math.random() * 3000));

		return true;
	}
	
	/*
	 * Oppretter simuleringsresultat-objekter for alle tidspunkter i simuleringen..
	 */
	public void setupSimuleringsResultat()
	{
		if ( !this.simulatorResultList.isEmpty() )
		{
			this.simulatorResultList.clear();
		}
		
		for ( long i=SimuleringsMotor.getTidsrom().getStartDateTime().getTime();i<SimuleringsMotor.getTidsrom().getEndDateTime().getTime();i+=SimuleringsMotor.getTidsrom().getOpplosningInMs())
		{
			System.out.println("setter opp for i=" + i);
			
			this.simulatorResultList.add(new SimuleringsResultat(i, SimuleringsMotor.getTidsrom().getOpplosningInMs()));
		}
	}
	
	/*
	 * henter fram simuleringsresultatet vi ønsker
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
	 * Printer simulatorResult - for debugging 
	 * 
	 */
	public void printSimulatorResult()
	{
		Iterator<SimuleringsResultat> srIt = simulatorResultList.iterator();
		
		System.out.format("/----------------------------- printSimulatorResult() ---------------------------------\\\n", "StartTime", "opplos.","sim", "Verdi");
		System.out.format("| %-84s |\n", getClass().getName());
		System.out.format("+--------------------------------------------------------------------------------------+\n", "StartTime", "opplos.","sim", "Verdi");
		System.out.format("| %30s | %9s | %6s | %30s |\n", "StartTime", "opplos.","sim", "Energiforbuk i perioden", "Varmetap i perioden");
		System.out.format("+--------------------------------------------------------------------------------------+\n", "StartTime", "opplos.","sim", "Verdi");
		
		while (srIt.hasNext() )
		{
			SimuleringsResultat tmpSimRes=(SimuleringsResultat)srIt.next();

			System.out.format("| %30s | %9d | %6b | %30.1f |\n", tmpSimRes.getStartDateTime(), tmpSimRes.getLength(), tmpSimRes.isSimulated(), tmpSimRes.getEnergiForbruk(), tmpSimRes.getVarmetap() /* ***J*** */);
		}
		
		System.out.format("\\--------------------------------------------------------------------------------------/\n", "StartTime", "opplos.","sim", "Verdi");
	}	
	

	public boolean showDebug() {
		return showDebug;
	}

	public void setShowDebug(boolean showDebug) {
		this.showDebug = showDebug;
	}
}
