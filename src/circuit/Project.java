package circuit;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xml.XmlParser;

public class Project {
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

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
			System.out.println(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue());
			if(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(circuitName))
			{
				circuit = nodeList.item(i);
				break;
			}
		}
		if(circuit == null)
			throw new NullPointerException("Circuit with name "+circuitName + " Not found");
		return new Circuit(document,circuit);	
	}

	public File getTempXMLFile() throws TransformerException, IOException
	{
		File tmpFile = File.createTempFile("tmp", ".circ");
		tmpFile.deleteOnExit();

		XmlParser parser = new XmlParser();
		parser.write(document , tmpFile);
		
		return tmpFile;
	}

}
