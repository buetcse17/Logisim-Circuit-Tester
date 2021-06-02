package xml;

import java.io.File;
import java.io.IOException;

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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlParser {
	public Document read(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		return doc;
	}

	public void write(Document document, File file) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);

		StreamResult streamResult = new StreamResult(file);
		transformer.transform(domSource, streamResult);

	}

}
