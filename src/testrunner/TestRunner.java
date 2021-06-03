package testrunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import circuit.Project;
import testcase.TestCase;

public class TestRunner {
	TestCase test;
	Project proj;
	String circuitName;
	File logisimFile;

	public TestRunner(TestCase test, Project proj, String circuitName, File logisimFile) {
		this.test = test;
		this.proj = proj;
		this.circuitName = circuitName;
		this.logisimFile = logisimFile;
	}

	public void run() {
		if (this.circuitName == null)
			this.circuitName = this.proj.getMainCircuitName();
		this.proj.setMainCircuit(this.circuitName);

		test.apply(this.proj.getCircuit(this.circuitName));

		try {
			File circuitFile = this.proj.getTempXMLFile();
			System.out.println("modified circuit file :" + circuitFile.getAbsolutePath());

			ArrayList<String> args = new ArrayList<String>();
			args.add(circuitFile.getAbsolutePath());

			JarExecutor je = new JarExecutor();

			je.executeJar(logisimFile.getAbsolutePath(), args);

		} catch (TransformerException | IOException e) {
			e.printStackTrace();
		}

	}

}