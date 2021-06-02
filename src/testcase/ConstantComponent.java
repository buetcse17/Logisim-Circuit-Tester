package testcase;

public class ConstantComponent
{
	String label;
	String value;
	public ConstantComponent(String label, String value) {
		this.label = label;
		this.value = value;
	}
	@Override
	public String toString() {
		return "ConstantComponent [label=" + label + ", value=" + value + "]";
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}