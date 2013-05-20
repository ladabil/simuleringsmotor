package no.hin.student.y2013.grp2it.simuleringsmotor;

import org.w3c.dom.NodeList;

/*
 * Mai 2013 - Gruppe 2 IT
 */

abstract public class SimulatorBase {
	private NodeList nodeList;

	public SimulatorBase()
	{
		this.nodeList = null;
	}
	
	abstract public double getBeregning();
	
	public void setXMLNodeList(NodeList nodeList) {
		  
	  
	}
	  
	public NodeList getXMLNodeList()
	{
		return this.nodeList;
	}
}
