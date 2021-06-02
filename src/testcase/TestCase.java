package testcase;

import java.util.Vector;

import circuit.Circuit;

public class TestCase {
	Vector< ConstantComponent > constants;
	public void addConstants(ConstantComponent constant){
		constants.add(constant);
	}
	
	public TestCase() {
		this.constants = new Vector<ConstantComponent>();
	}

	@Override
	public String toString() {
		return "TestCase [constants=" + constants + "]";
	}

	public Vector<ConstantComponent> getConstants() {
		return constants;
	}

	public void setConstants(Vector<ConstantComponent> constants) {
		this.constants = constants;
	}
	public void apply(Circuit circuit){
		for(ConstantComponent ccmp : constants){
			circuit.apply(ccmp);
		}
	}
	
}
