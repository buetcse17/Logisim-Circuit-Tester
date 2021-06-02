package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import circuit.Project;
import parser.TestCaseJsonParser;
import parser.TestCaseParser;
import testcase.TestCase;
import testrunner.TestRunner;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static void runTest(Map<String, String> test, String circFileName, String circuitName) {
		for (Map.Entry<String, String> data : test.entrySet()) {
			System.out.println(data.getKey() + data.getValue());
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(circFileName));
			// doc.getDocumentElement().normalize();

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			NodeList circuits = doc.getElementsByTagName("circuit");

			for (int i = 0; i < circuits.getLength(); i++) {
				Node circuit = circuits.item(i);
				if (circuit.hasChildNodes()) {
					System.out.println(circuit.getAttributes().getNamedItem("name") + " has children ");

					NodeList lst = circuit.getChildNodes();
					for (int j = 0; j < lst.getLength(); j++) {
						Node item = lst.item(j);
						
						if (item.getNodeName() == "comp" ){
							if(item.hasAttributes())
							{
								if(item.getAttributes().getNamedItem("name").getNodeValue() == "Pin")
								System.out.println("item name " + item.getNodeName());
								item.getAttributes().getNamedItem("name").setNodeValue("Constant");
								System.out.println("item is" + item);
							}
						}
					}
				}

				// System.out.println(circuit.getNodeName());
				// System.out.println(circuit.getAttributes().getNamedItem("name").getNodeValue());
				// NamedNodeMap attr = circuit.getAttributes();
				// for (int j = 0; j < attr.getLength(); j++) {
				// 	System.out.println(attr.item(j));
				// 	Node attrnode = attr.item(j);
				// 	System.out.println(attrnode);
				// }
			}

			writeXml(doc, "modified.circ");

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeXml(Document doc, String fileName) {

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(fileName));
			transformer.transform(domSource, streamResult);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main2(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		String filename = "tests.json";
		String circFileName = "two-input-and-or.circ";
		File file = new File(filename);
		Reader reader = new FileReader(file);
		Object obj = parser.parse(reader);
		List<Map<String, String>> tests = (JSONArray) obj;
		System.out.println(tests);
		for (Map<String, String> test : tests) {
			// System.out.println(test);
			runTest(test, circFileName, null);
		}
		reader.close();
	}

	public static void main(String[] args) {
		String testFileName = "tests.json";
		String circuitFileName = "two-bit-two-input-and.circ";
		String circuitName = "main";
		TestCaseParser inputParser = new TestCaseJsonParser();
		Vector<TestCase> tests = null;
		try {
			tests = inputParser.read(new FileReader(new File(testFileName)));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(tests);
		Project proj = null;

		
		
		for(TestCase test : tests){
			
			try {
				proj = new Project(circuitFileName);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			TestRunner runner = new TestRunner(test, proj, circuitName);
			runner.run();
		}


	}
}
