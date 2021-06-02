package testrunner;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.transform.TransformerException;

import circuit.Project;
import testcase.TestCase;

public class TestRunner{
	TestCase test;
	Project proj;
	String circuitName;


	
	public TestRunner(TestCase test, Project proj, String circuitName) {
		this.test = test;
		this.proj = proj;
		this.circuitName = circuitName;
	}

	public void run()
	{
		if (this.circuitName == null)
			this.circuitName 
			= this.proj.getMainCircuitName();
		this.proj.setMainCircuit(this.circuitName);

		test.apply(this.proj.getCircuit(this.circuitName));

		try {
			File circuitFile = this.proj.getTempXMLFile();
			System.out.println("modified circuit file :" + circuitFile.getAbsolutePath());

			ArrayList<String> args = new ArrayList<String>();
			args.add(circuitFile.getAbsolutePath());

			JarExecutor je = new JarExecutor();

			je.executeJar("logisim-generic-2.7.1.jar",args);

		} catch (TransformerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}	

}