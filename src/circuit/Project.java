package circuit;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xml.XmlParser;

public class Project {
	Document document;
	public Project(String fileName) throws ParserConfigurationException, SAXException, IOException
	{
		File file = new File(fileName);
		XmlParser parser = new XmlParser();
		document = parser.read(file);
	}
	public String getMainCircuitName()
	{
		return document.getElementsByTagName("main").item(0).getAttributes().getNamedItem("name").getNodeName();
	}
	public void setMainCircuit(String circuitName)
	{
		document.getElementsByTagName("main").item(0).getAttributes().getNamedItem("name").setNodeValue(circuitName);
	}
	

	public Circuit getCircuit(String circuitName)
	{
		NodeList nodeList = document.getElementsByTagName("circuit");
		Node circuit = null;
		for(int i=0;i<nodeList.getLength();i++)
		{
			if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue() == circuitName)
			{
				circuit = nodeList.item(i);
				break;
			}
		}
		if(circuit == null)
			throw new NullPointerException("Circuit with name "+circuitName + "Not found");
		return new Circuit(circuit);	
	}
}
