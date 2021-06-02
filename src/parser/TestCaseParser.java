package parser;

import java.io.Reader;
import java.util.Vector;

import testcase.TestCase;

public interface TestCaseParser {
	Vector<TestCase > read(Reader reader);
}
