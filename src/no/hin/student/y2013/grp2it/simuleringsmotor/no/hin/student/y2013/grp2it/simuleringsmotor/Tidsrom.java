package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/*
 * Tidsrom - 20/5-2013
 */
public class Tidsrom extends SimulatorBase {
	private Date startDateTime = null;
	private Date endDateTime = null;
	private int opplosning = 3600; // Standard oppløsning på simuleringen i sekunder. Standardverdi 3600 (en time).
	
	public Tidsrom()
	{
		this.opplosning = 3600;
		this.startDateTime = new Date();
		this.endDateTime = new Date();
	}
	
	public void parseXMLNodeElement(Node node)
	{
		SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
		
		if ( node.getNodeName().equalsIgnoreCase("opplosning") )
		{
			this.opplosning = Integer.parseInt(node.getTextContent());
		}
		else if ( node.getNodeName().equalsIgnoreCase("startDateTime") )
		{
			try {
				this.startDateTime = parserSDF.parse(node.getTextContent());
			} catch (DOMException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		else if ( node.getNodeName().equalsIgnoreCase("endDateTime") )
		{
			try {
				this.endDateTime = parserSDF.parse(node.getTextContent());
			} catch (DOMException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		
		else
		{
			super.parseXMLNodeElement(node);
		}
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(String.format("+++++ Type: %s\n", this.getClass().getName()));
		str.append(String.format("startDateTime      = %s\n", this.startDateTime));
		str.append(String.format("stopDateTime       = %s\n", this.endDateTime));
		str.append(String.format("Oppløsning (i sek) = %d\n", this.opplosning));
		str.append(String.format("----- Type Slutt\n"));
		
		return str.toString();
	}
	
	public Date getStartDateTime()
	{
		return this.startDateTime;
	}

	public Date getEndDateTime()
	{
		return this.endDateTime;
	}

	public int getOpplosning()
	{
		return this.opplosning;
	}
	
	public long getOpplosningInMs()
	{
		return this.opplosning * 1000;
	}
}

