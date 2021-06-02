package parser;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import testcase.TestCase;

public class TestCaseJsonParser implements TestCaseParser{

	private TestCase getTestCase(JSONObject test)
	{
		
		TestCase testCase = new TestCase();
		Map<String,String> test_map = (Map<String,String>)test;
		
		for(Map.Entry<String,String> entry : test_map.entrySet()){
			testCase.addConstants(new testcase.ConstantComponent(entry.getKey(), entry.getValue()));
		}
		return testCase;
	}

	@Override
	public Vector<TestCase> read(Reader reader) throws IOException, ParseException {
		Vector<TestCase> testcases = new Vector<TestCase>();
		
		JSONParser parser = new JSONParser();
		JSONArray array = (JSONArray) parser.parse(reader);

		for(int i=0;i<array.size();i++){
			JSONObject test = (JSONObject)array.get(i);
			testcases.add(getTestCase(test));
		}

		return testcases;
	}
	
}
