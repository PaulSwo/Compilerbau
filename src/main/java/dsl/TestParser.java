// Klasse zum Testen des Parsers
package dsl;

import dsl.Parser;

/**
 * 
 */
public class TestParser {

	// Konstruktor
	public TestParser() {
		
	}

	// Test des Parsers
	public static void main(String[] args) {
		testParser("turn off");
		testParser("set temperature 22 degrees");
		testParser("select mode heating");
		testParser("switch off");
		
	}
	
	// Methode zum Testen des Parsers
	static public void testParser(String text) {
		System.out.println("Test des Parsers startet.");
		Parser.parse(text);
		success(text);
		System.out.println("Test des Parsers endet.\n");
	}

	
	static public void success(String text) {
		if (Parser.success)
			System.out.println("Erfolg: "+text);
		else
			System.out.println("Misserfolg: "+text);

		
	}


}
