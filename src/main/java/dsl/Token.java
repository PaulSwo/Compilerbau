// Token
package dsl;

import java.util.HashMap;


public class Token {

	// Aufzaehlung von Tokentypen
	static enum Lextype {
		INTEGER,
		IDENT,
		EOF,
		SET,
		TEMPERATURE,
		DEGREES,
		SELECT,
		MODE,
		TURN,
		ON,
		OFF,
		HEATING,
		COOLING,
		FAN,
		WHITESPACE
	};

	// End-Of-File-Zeichen
	final public static char eofChar = '\u0000';

	// Typ des Tokens
	public Lextype type;

	// Wert des Tokens
	public int value = -1;

	// Lexem
	public String lexeme = null;

	// Der Wert der Variable ist true,
	// wenn der Token aus einem einzigen Zeichen, z.B. Plus-Zeichen,
	// gebildet wird
	private boolean singleCharLexeme = false;

	// Tabelle von Tokentypen.
	// Der Schluessel ist ein Tokentyp
	// Das Element ist die Benennung des Tokentyps
	static private HashMap<Lextype, String> lextypes = new HashMap();

	// Konstruktor
	public Token() {
		initLextypes();

	}

	// Konstruktor
	public Token(Lextype type) {
		super();

		initLextypes();

		this.type = type;

		singleCharLexeme = false;

	}

	// Konstruktor
	public Token(Lextype type, int value) {
		super();
		this.type = type;
		this.value = value;
		this.lexeme=value+"";

		singleCharLexeme = false;
	}

	// Konstruktor
	public Token(Lextype type, char lexeme) {
		super();
		this.type = type;
		this.lexeme = lexeme + "";
		singleCharLexeme = true;
	}

	// Konstruktor
	public Token(Lextype type, String lexeme) {
		super();
		this.type = type;
		this.lexeme = lexeme;
		singleCharLexeme = false;
	}

	// Die Methode gibt ein String zum Anzeigen eines Tokens zurueck
	public String toString() {
		String result = repr();

		return result;

	}

	// Die Methode gibt ein String zum Anzeigen eines Tokens zurueck
	public String repr() {
		String result = "TOKEN(";
		result = result + lextypes.get(type);
		if (type == Lextype.EOF) {
			result = result + ")";
			return result;
		}
		result = result + ",";
		if (this.singleCharLexeme)
			result = result + "\"";
		
		else
			result = result + lexeme;
		if (this.singleCharLexeme)
			result = result + "\"";
		result = result + ")";
		return result;
	}

	

	public Lextype getType() {
		return type;
	}

	public void setType(Lextype type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	// Die Methode fuellt die Tabelle lextypes aus
	static private void initLextypes() {

		for (Lextype lex : Lextype.values()) {
			lextypes.put(lex, lex.toString());

		}

	}

	static String getLextype(Lextype type) {
		String lexeme = lextypes.get(type);
		return lexeme;
	}

}
