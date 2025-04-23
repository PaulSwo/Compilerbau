// Lexer
package dsl;

import java.util.HashMap;

import dsl.Token.*;

public class Lexer {

	// Eingangstext,z.B. "10 + 2"
	static public String text;

	// Aktuelle Position (Index) im Text
	static public int pos = 0;

	// Aktuelles Zeichen
	static char ch;

	// Aktuelles Zeichen
	static int ch_type;

	// Zeichentypen
	static final int eof = 0, digit = 1, letter = 2;

	// Aktuelles Lexem
	static String lexeme = "";

	static public int row = 0; // Zeile
	static public int col = 0; // Spalte

	// Hashmap fuer reservierte Schluesselwoerter,
	static HashMap<String, Token> RESERVED_KEYWORDS = new HashMap();

	// Der Wert der Variable success ist true,
	// wenn die lexikalische Analyse erfolgreich ist.
	static public boolean success = true;

	// Eintragen von reservierten Schuesselwoertern in die Schluesselworttabelle
	static public void initReservedKeywords() {
		enterKW("set", Lextype.SET);
		enterKW("temperature", Lextype.TEMPERATURE);
		enterKW("degrees", Lextype.DEGREES);
		enterKW("select", Lextype.SELECT);
		enterKW("mode", Lextype.MODE);
		enterKW("turn", Lextype.TURN);
		enterKW("on", Lextype.ON);
		enterKW("off", Lextype.OFF);
		enterKW("heating", Lextype.HEATING);
		enterKW("cooling", Lextype.COOLING);
		enterKW("fan", Lextype.FAN);

	}

	// Eintragen eines Schluesselworts in die Schluesselworttabelle
	static public void enterKW(String keyword, Lextype type) {
		Token token = new Token(type, keyword);
		RESERVED_KEYWORDS.put(keyword, token);
	}

	/**
	 * 
	 */
	public Lexer(String text) {

		init(text);
	}

	// Es erscheint eine Fehlermeldung,
	// und alle Zeichen werden bis zum Ende des Textes übersprungen
	static public void error() {
		success = false;
		System.out.println("Inkorrektes Zeichen");
		while (ch != Token.eofChar)
			nextCh();
		;
	}

	// Es erscheint eine Fehlermeldung,
	// und alle Zeichen werden bis zum Ende des Textes übersprungen
	static public void error(String message) {
		success = false;
		System.out.println("Fehler: " + message + ". Spalte: " + (pos + 1));
		while (ch != Token.eofChar)
			nextCh();
	}

	// Es erscheint eine Fehlermeldung,
	// und alle Zeichen werden bis zum Ende des Textes übersprungen
	static public void error(int type) {
		success = false;
		System.out.println("Fehler: erwarteter Zeichentyp: " + getCharTypeName(type) + ". Spalte.: " + (pos + 1));
		while (ch != Token.eofChar)
			nextCh();
	}

	// Lese das nächste Zeichen
	// Gehe zur nächsten Position
	// Wenn die Position ausserhalb des Textes ist, setze ch auf eof,
	// sonst setze ch auf das aktuelle Zeichen
	static public void nextCh() {
		pos++;
		if (pos > (text.length() - 1))
			ch = Token.eofChar;
		else {
			ch = text.charAt(pos);
			col++;
		}
		ch_type = getCharType(ch);
	}

	// Leerzeichen überspringen
	// Wenn das aktuelle Zeichen ein Leerzeichen (jedoch kein eof) ist,
	// lese das nächste Zeichen
	static public void skip_whitespaces() {
		while ((ch != Token.eofChar) && Character.isSpaceChar(ch))
			nextCh();
	}

	// Erkennung einer Ganzzahl
	static public Token integer() {

		StringBuilder lexem = new StringBuilder();

		while(!Character.isSpaceChar(ch) && ch != Token.eofChar) {
			lexem.append(ch);
			nextCh();
		}

		Token token = new Token(Lextype.INTEGER, Integer.parseInt(lexem.toString()));

		return token;
	}

	// Erkennung eines Schluesselwortes 
	static public Token keyword() {
		StringBuilder lexem = new StringBuilder();

		while(!Character.isSpaceChar(ch) && ch != Token.eofChar) {
			lexem.append(ch);
			nextCh();
		}

		Token token = RESERVED_KEYWORDS.get(lexem.toString());
		if(token == null) {
			token = new Token(Lextype.EOF);
			System.out.println("Token not recognized: " + lexem);
		}
		return token;

	}

	// Die Methode gibt einen Token zurueck
	static public Token nextToken() {
		Token token = new Token(Lextype.EOF);

		while (ch != Token.eofChar) {

			// Wenn das aktuelle Zeichen ein Leerzeichen ist,
			// überspringe Leerzeichen,
			// setze die Schleife fort
			if (Character.isSpaceChar(ch)) {
				skip_whitespaces();
				continue;
			}

			// Wenn das aktuelle Zeichen eine Ziffer ist,
			// erkenne eine Ganzzahl,
			// beende die Schleife
			if (Character.isDigit(ch) || ch == '+' || ch == '-') {
				token = integer();
				break;
			}

			// Wenn das aktuelle Zeichen ein Buchstabe ist,
			// erkenne ein Schlueeselwort,
			// beende die Schleife
			if (Character.isLetter(ch)) {
				token = keyword();
				break;
			}

			error("unbekannter Tokentyp");

		}

		return token;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	// Initialisierung des Lexers
	static public void init(String input) {
		success = true;
		initReservedKeywords();
		text = input;
		pos = 0;
		row = 1;
		col = 0;
		ch = text.charAt(pos);
		ch_type = getCharType(ch);
	}

	// Ermittlung des Typs eines Zeichens
	static int getCharType(char ch) {
		int result = -1;
		if (ch == eof)
			result = eof;

		else if (Character.isDigit(ch))
			result = digit;
		else if (Character.isLetter(ch))
			result = letter;

		return result;

	}

	// Ermittlung der Zeichennummer
	static String getCharTypeName(int char_type) {
		String result = "";
		if (char_type == eof)
			result = "eof";
		else if (char_type == digit)
			result = "digit";
		else if (char_type == letter)
			result = "letter";
		return result;

	}

	// Erkennung des vorgegebenen Zeichentyps.
	// Wenn der Typ des aktuellen Zeichens dem vorgegebenen Zeichentyp entspricht,
	// fuege das Zeichen in das Lexem ein und lese das naechste Zeichen,
	// sonst zeige eine Fehlermeldung an.
	static public void check(int t) {
		if (ch_type == t) {
			lexeme = lexeme + ch;
			nextCh();
		} else
			error(t);
	}

}
