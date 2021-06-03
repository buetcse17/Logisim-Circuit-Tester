package circuit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import testcase.ConstantComponent;

public class Circuit {
	Document document;
	Node circuit;

	public Circuit(Document document, Node circuit) {
		this.document = document;
		this.circuit = circuit;
	}

	public void apply(ConstantComponent constantComponent) {
		NodeList lst = circuit.getChildNodes();
		for (int i = 0; i < lst.getLength(); i++) {
			Node item = lst.item(i);

			if (item.getNodeName().equals("comp")) {
				if (item.hasAttributes()) {
					if (item.getAttributes().getNamedItem("name").getNodeValue().equals("Pin")) {
						
						boolean etai = false;
						NodeList attrs_comp = item.getChildNodes();
						for (int j = 0; j < attrs_comp.getLength(); j++) {
							Node attr_comp = attrs_comp.item(j);
							if (attr_comp.hasAttributes()) {
								if (attr_comp.getAttributes().getNamedItem("name").getNodeValue().equals("label")
										&& attr_comp.getAttributes().getNamedItem("val").getNodeValue()
												.equals(constantComponent.getLabel())) {
									etai = true;
								}
							}

						}
						if (etai) {
							System.out.println("Pin found with label "+ constantComponent.getLabel());
							item.getAttributes().getNamedItem("name").setNodeValue("Constant");
							Element node = document.createElement("a");
							node.setAttribute("name", "value");
							node.setAttribute("val", constantComponent.getValue());
							item.appendChild(node);
						}
					}
				}
			}
		}
	}
}
