package circuit;

import java.util.Vector;

import org.w3c.dom.Node;

import testcase.ConstantComponent;
import testcase.TestCase;

public class Circuit {
	Node circuit;
	
	public Circuit(Node circuit) {
		this.circuit = circuit;
	}
	void apply(ConstantComponent constantComponent){
		circuit.getAttributes().getNamedItem(constantComponent.getLabel()).setNodeValue(constantComponent.getValue());
	}
}
