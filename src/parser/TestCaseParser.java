package parser;

import java.io.IOException;
import java.io.Reader;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import testcase.TestCase;

public interface TestCaseParser {
	Vector<TestCase > read(Reader reader) throws IOException, ParseException;
}
