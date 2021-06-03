package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import circuit.Project;
import parser.TestCaseJsonParser;
import parser.TestCaseParser;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import testcase.TestCase;
import testrunner.TestRunner;

@Command(
	name = "logisim-circuit-tester",
	mixinStandardHelpOptions = true, 
	version = "logisim-circuit-tester 1.0 pre-alpha", 
	description = "From given logisim *.circ project and input file, "
 				+"this program modified circ file with pin component to constant component with appriate value and loads it with logisim graphically",
	sortOptions = false 
				 )
public class Main implements Callable<Integer> {
	@Option(
		names = {"-l","--logisim"},
		description = "logisim-generic-2.7.1.jar which will be loaded with modified circuit in every test",
		required = false ,
		defaultValue = "logisim-generic-2.7.1.jar"
	)
	private File logisimFile;
	
	@Option(
		names = {"-if","--input_format"},
		description = "input format in testfile, supported options : JSON, default = JSON",
		defaultValue = "JSON",
		required = false
		
		)
	private String iFormat = "JSON";

	@Option(
		names = { "-t", "--testfile" },
		description = "file containing testcases",
		required = true
		)
	private File testFile;


	@Option(
		names = {"-c" ,"--circuit"},
		description = "circuit name in project",
		defaultValue = Parameters.NULL_VALUE,
		required = false
	)
	private String circuitName;

	@Parameters(
		index = "0",
		description = "project file of logisim (.circ)"
	)
	private File projectFile;

	

	public static void main(String[] args) {
		int exitCode = new CommandLine(new Main()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public Integer call() throws Exception {

		TestCaseParser inputParser = null;
		if(iFormat.equalsIgnoreCase("json"))
			inputParser =  new TestCaseJsonParser();
		else {
			throw new Exception("input format "+ " not supported");
		}
		Vector<TestCase> tests = null;
		try {
		tests = inputParser.read(new FileReader(testFile));
		System.out.println("load testcases : success");
		} catch (IOException | ParseException e) {
		e.printStackTrace();
		}
		
		Project proj = null;

		for (TestCase test : tests) {

		try {

		proj = new Project(projectFile);
		System.out.println("load project : success");
		} catch (ParserConfigurationException | SAXException | IOException e) {
		e.printStackTrace();
		}

		TestRunner runner = new TestRunner(test, proj, circuitName , logisimFile);
		runner.run();

		}

		return 0;
	}
}
