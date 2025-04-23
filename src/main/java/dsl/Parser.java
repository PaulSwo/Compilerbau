// Parser
package dsl;

import java.util.ArrayList;

import dsl.Token.Lextype;

public class Parser {

	// Der aktuelle Token
	static Token sym = null;

	// Anfangsterminale für das Startsymbol Command
	static ArrayList<Lextype> firstCommand = new ArrayList();

	// Die Variable wird auf true gesetzt,
	// wenn die Syntaxanalyse erfolgreich war.
	static boolean success = true;

	// Konstruktor
	public Parser(Lexer lexer) {
		super();

	}

	// Fehlermeldung
	static public void error(Lextype type) {

		success = false;
		System.out.println("Fehler: erwarteter Tokentyp: " + Token.getLextype(type) 
				+ ". Spalte: " + (Lexer.col));

	}

	// Fehlermeldung
	static public void error(String message) {

		success = false;

		System.out.println("Fehler: " + message);

	}

	// Wenn der Typ des aktuellen Tokens dem vorgegebenen Tokentyp entspricht,
	// wird der naechste Token gelesen,
	// sonst erscheint eine Fehlermeldung.
	static public void check(Lextype token_type) {

		if (sym.type == token_type)
			sym = Lexer.nextToken();
		else
			error(token_type);

	}

	// Wenn das Lexem des aktuellen Tokens dem vorgegebenen Lexem entspricht,
	// wird der naechste Token gelesen,
	// sonst erscheint eine Fehlermeldung.
	static public void check(String lexeme) {

		if (sym.lexeme.equals(lexeme))
			sym = Lexer.nextToken();
		else
			error("expected token: " + lexeme + " ");

	}

	// Eintragung von Anfangsterminalen
	// fuer das Startsymbol Command
	static public void initFirstCommand() {
		firstCommand.clear();
		firstCommand.
		// firstCommand.add(Lextype.anfang);

	}

	// Pruefung von Anfangsterminalen fuer das Startsymbol Command.
	// Wenn der aktuelle Tokentyp nicht zum First(Command) gehoert,
	// erscheint eine Fehlermeldung, 
	// und alle Token werden bis zum Ende des Textes übersprungen werden.
	static public void checkFirstCommand() {
		if (!firstCommand.contains(sym.type)) {
			error("Inkorrekter Anfang des Befehls");
			while (sym.type != Lextype.EOF)
				sym = Lexer.nextToken();
		}
	}

	// Syntaxanalyse, Erzeigung eines abstrakten Syntaxbaums
	static public void parse(String input) {
		init(input);
		// Erzeugung eines AST fuer das Startsymbol
		Command(); //
		check(Lextype.EOF);

	}

	// Initialisierung des Parsers
	static public void init(String input) {
		success = true;
		Lexer.init(input);
		firstCommand.clear();
		initFirstCommand();
		sym = Lexer.nextToken();
	}

	// Das Startsymbol
	static public void Command() {
		checkFirstCommand();

	}

}
