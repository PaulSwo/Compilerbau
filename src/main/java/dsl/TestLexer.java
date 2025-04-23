// Klasse zum Testen des Lexers
package dsl;

import dsl.Token.Lextype;

import javax.swing.*;

/**
 * 
 */
public class TestLexer {

	// Konstruktor
	public TestLexer() {
	}

	// Test des Lexers
	public static void main(String[] args) {
		// Test des Lexers mit einem Befehl
		testLexer("set temperature 22 degrees");
		// Test des Lexers mit einem inkorrekten Befehl
		testLexer("set temperature +22 degrees");
		

	}

	// Methode zum Testen des Lexers
	static public void testLexer(String text) {
		System.out.println("Test des Lexers startet.");
		Lexer.init(text);
		boolean flag = true;
		while (flag) {
			Token token = Lexer.nextToken();
			System.out.println(token);
			if (token.type == Lextype.EOF)
				flag = false;
		}
		success(text);
		System.out.println("Test des Lexers endet.\n");
	}
	
	static public void success(String text) {
		if (Lexer.success)
			System.out.println("Erfolg: "+text);
		else
			System.out.println("Misserfolg: "+text);	
	} 

}
