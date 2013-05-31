package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class SimuleringsMotor extends SimulatorBase {
	static SimuleringsMotor simMotor = null;
	public Tidsrom tidsrom = null;
	public Klima klima = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		simMotor = new SimuleringsMotor();
		simMotor.doRun();
	}
	
	public SimuleringsMotor()
	{
	}
	
	public static SimuleringsMotor getSimuleringsMotor()
	{
		return simMotor;
	}
	
	public static Tidsrom getTidsrom()
	{
		return SimuleringsMotor.getSimuleringsMotor().tidsrom;
	}
	
	public static void setTidsrom(Tidsrom tidsrom)
	{
		SimuleringsMotor.getSimuleringsMotor().tidsrom = tidsrom;
	}
	
	public static Klima getKlima()
	{
		return SimuleringsMotor.getSimuleringsMotor().klima;
	}
	
	public static void setKlima(Klima klima)
	{
		SimuleringsMotor.getSimuleringsMotor().klima = klima;
	}	

	/*
	 * Setter opp alle objekter/klasser og kjører simuleringen.
	 */
	public void doRun()
	{
		System.out.println("Running");
		
		parseXMLFile();
		
		System.out.println("Died");
		
		if ( this.tidsrom == null )
		{
			System.out.println("mangler Tidsrom-objektet.. avbryter simuleringen");
			System.exit(-2);
		}
		
		setupSimuleringsResultat();
		
		double energiForbruk = 0, curEnergiForbruk = 0;
		
		for ( long i=this.tidsrom.getStartDateTime().getTime();i<this.tidsrom.getEndDateTime().getTime();i+=this.tidsrom.getOpplosningInMs())
		{
			System.out.println("Simulating - i=" + i);

			curEnergiForbruk = getEnergiForbrukForPeriode(i, this.tidsrom.getOpplosningInMs());
	
			System.out.println("Fobruket dette perioden = " + curEnergiForbruk);
			energiForbruk += curEnergiForbruk;
		}
		
		System.out.println("Energiforbruk for perioden: " + energiForbruk);
		
		printSimulatorResult();
		
		
		Klima klima = new Klima();
		String rawXML = "";
		
		try {
			Document doc = klima.getXMLFromEKlima();
			klima.parseWsKlimaXML(doc.getChildNodes(),null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * henter beregningene fra objekter for ett gitt tidsrom og returnerer forbruket som watt
	 * 
	 * lengde er i ms
	 */
	public double getEnergiForbrukForPeriode(long startTime, long lengde)
	{
		Iterator<SimulatorBase> sbIt = simulatorBaseList.iterator();
		double energiForbruk = 0;
		SimuleringsResultat tmpSimRes = null;
		
		while (sbIt.hasNext() )
		{
			SimulatorBase tmpSimBase=(SimulatorBase)sbIt.next();

			// Utfør først beregningene
			tmpSimBase.doBeregning(startTime, lengde);
			
			energiForbruk += tmpSimBase.getBeregning();
		}
		
		System.out.println("energiForbruk: " + energiForbruk + "\n");
		
		if ( (tmpSimRes=findSimuleringsResultatByTime(startTime)) != null )
		{
			// Hent så beregningen og sett den i resultatet
			tmpSimRes.setEnergiForbruk(energiForbruk);
			
			// Sett at simuleringen er fullført for dette simuleringsresultatet..
			tmpSimRes.setSimulated(true);
		}

		return energiForbruk;
	}
	
	public void parseXMLFile()
	{
	    try {
	    	 
	    	File file = new File("./testData.xml");
	     
	    	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
	                                 .newDocumentBuilder();
	     
	    	Document doc = dBuilder.parse(file);
	     
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     
	    	if ( !doc.getDocumentElement().getNodeName().equalsIgnoreCase("simulering") )
	    	{
	    		System.out.println("feil i root tag i xml-filen, forventer \"simulering\" - fikk: " + doc.getDocumentElement().getNodeName());
	    		return;
	    	}
	    	
	    	if (doc.hasChildNodes()) {
	    		Node simuleringsNode = doc.getChildNodes().item(0);
	    		
	    		if ( simuleringsNode.hasChildNodes() )
	    		{
		    		this.setXMLNodeList(simuleringsNode.getChildNodes());
		    		this.parseXML(true);
	    		}
	    	}
	    	
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	}

}
