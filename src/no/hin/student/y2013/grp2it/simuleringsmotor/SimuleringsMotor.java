package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.File;
import java.nio.file.Path;
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
	public Familie familie = null;
	private File xmlFile = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FilMonitor filMonitor = new FilMonitor();
		filMonitor.doRun();
		
	}
	
	/*
	 * Commons Daemon - metoder
	 */	
	public void start() {
		MyLog.log("starting SimuleringsMotor\n");

		FilMonitor filMonitor = new FilMonitor();
		filMonitor.doRun();
	}	

	public void stop()
	{
		MyLog.log("stopping zhoneProv\n");
	}
	
	public void destroy()
	{
		MyLog.log("Destroying zhoneProv\n");
	}
	
	
	public SimuleringsMotor()
	{
		
	}
	
	public void setFile(File file)
	{
		xmlFile = file;
	}
	
	public File getFile()
	{
		return xmlFile;
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
	
	public static Familie getFamilie()
	{
		return SimuleringsMotor.getSimuleringsMotor().familie;
	}
	
	public static void setFamilie(Familie familie)
	{
		SimuleringsMotor.getSimuleringsMotor().familie = familie;
	}	
	
	/*
	 * Setter opp alle objekter/klasser og kjører simuleringen.
	 */
	public void doRun()
	{
		System.out.println("Running");
		
		if ( getFile() == null ) 
		{
			System.err.println("xmlFile er ikke satt!");
			return;
		}
		
		parseXMLFile();
		
		System.out.println("Died");
		
		if ( this.tidsrom == null )
		{
			System.out.println("mangler Tidsrom-objektet.. avbryter simuleringen");
			System.exit(-2);
		}
		
		// Last inn klimadata
		SimuleringsMotor.getKlima().getAndParseEKlima();
		
		// Sett opp simuleringsresulatet

		setupSimuleringsResultat();
		
		double energiForbruk = 0, curEnergiForbruk = 0;
		
		for ( long i=this.tidsrom.getStartDateTime().getTime();i<this.tidsrom.getEndDateTime().getTime();i+=this.tidsrom.getOpplosningInMs())
		{
			System.out.println("Simulating - i=" + i);

			curEnergiForbruk = getEnergiForbrukForPeriode(i, this.tidsrom.getOpplosningInMs());
	
			System.out.println("Forbruket denne perioden = " + curEnergiForbruk);
			energiForbruk += curEnergiForbruk;
		}
		
		System.out.println("Energiforbruk for perioden: " + energiForbruk);
		
		printSimulatorResult();
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
	    	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
	                                 .newDocumentBuilder();
	     
	    	Document doc = dBuilder.parse(getFile());
	     
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
