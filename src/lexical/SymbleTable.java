package lexical;

import java.util.Map;
import java.util.HashMap;


public class SymbolTable
{

    private Map<String, TokenType> st;

    public SymbolTable()
    {
        st = new HashMap<String, TokenType>();

		//FUNCTIONS
		add("start", TokenType.START);
		add("exit",  TokenType.EXIT);
		add("if", 	 TokenType.IF);
		add("then",  TokenType.THEN);
		add("else",  TokenType.ELSE);
		add("end", 	 TokenType.END);
		add("do", 	 TokenType.DO);
		add("while", TokenType.WHILE);
		add("scan",  TokenType.SCAN);
		add("print", TokenType.PRINT);
		add("or", 	 TokenType.OR);
		add("and", 	 TokenType.AND);
		//VAR TYPER
		add("int", 	 TokenType.INT);
		add("float", TokenType.FLOAT);
		add("string",TokenType.STRING);

		//SYMBOLS
		add(";", TokenType.SEMICOLON); //";"
		add("=", TokenType.ASSIGN); //"="
		add("(", TokenType.OPEN_BRA);
		add(")", TokenType.CLOSE_BRA);
		add(".", TokenType.DOT);
		add("", TokenType.OPEN_QUO); //"
		add("", TokenType.CLOSE_QUO); //"

		//OPERATORS
		add("==", TokenType.EQUAL);
	    add(">",  TokenType.GREATER);
	    add(">=", TokenType.GREATER_EQ);
	    add("<",  TokenType.LOWER);
	    add("<=", TokenType.LOWER_EQ);
	    add("<>", TokenType.LOWER_GREATER);
	    add("+",  TokenType.PLUS);
	    add("-",  TokenType.MINUS);
	    add("*",  TokenType.STAR);
	    add("/",  TokenType.RIGHT_BAR);


		add("", TokenType.DIGIT);
		add("", TokenType.LETTER);
		add("", TokenType.CARACTER);
	}

    }

    //add no HashMap
	public void add(){

	}
    public void add(String token, TokenType type)
    {
        st.put(token, type);
    }

    public boolean contains(String token)
    {
        return st.containsKey(token);
    }

    public TokenType find(String token)
    {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }
}
