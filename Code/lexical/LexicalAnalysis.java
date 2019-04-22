package lexical;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

import java.util.*;

public class LexicalAnalysis implements AutoCloseable {

    private int line;
    private SymbolTable st;
    private PushbackInputStream input;

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        st = new SymbolTable();
        line = 1;
    }

    public void close() throws IOException {
        input.close();
    }

    public int getLine() {
        return this.line;
    }

	public Lexeme nextToken() throws IOException {
	    int estado = 0;
	    Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);

	    while (estado != 8 && estado != 9) {						// Estado 9 é estado de erro padrão.
	        int c = input.read();
	        System.out.println("[" + estado + ", \'" + ((char) c) + "\']");

	        switch (estado) {										// Segue implementação com máquina de estados anexada no arquivo.
	            case 0:												// Estado inicial
	                if (c == ' ' || c == '\t' || c == '\r') {
					   estado = 0;	
	                } else if (c == '\n') {
					   estado = 0;
	                   line++;
	                } else if (c == '%') {
					   lex.token += (char) c;
					   estado = 1;
					} else if (c == '<' || c == '>' || c == '!' || c == ':') {
	                   lex.token += (char) c;
	                   estado = 2;
	                } else if (c == '&') {
					   lex.token += (char) c;
					   estado = 3;
					} else if (c == '|') {
					   lex.token += (char) c;
					   estado = 4;
					} else if (Character.isDigit(c)) {
	                   lex.token += (char) c;
	                   lex.type = TokenType.NUMBER;
	                   estado = 5;
	                } else if (c == '_') {
					   lex.token += (char) c;
					   lex.type = TokenType.ID;
					   estado = 6;
					} else if (c == '{') {
					   lex.token += (char) c;
	                   lex.type = TokenType.STRING;
                       estado = 7;
					} else if (c == ';' || c == ',' || c == '(' || c == ')' || c == '-' || c == '+' || c == '/' || c == '.' ) {
	                   lex.token += (char) c;
	                   estado = 8;
	                } else if (Character.isLetter(c)) {
	                   lex.token += (char) c;TokenType
					   lex.type = TokenType.ID;
	                   estado = 8;
	                } else if (c == -1) {
	                   lex.type = TokenType.END_OF_FILE;
	                   estado = 9;
	                } 
	           	break;

				case 1: 										//Estado de comentário
					if (c == '\n' && c != '}'){
						lex.token += (char) c;
						line++;
						estado = 0;
					} else if (c !=  '\n' && c != '}'){
						lex.token += (char) c;
						line++;
						estado = 1;
					} else {
						lex.type = TokenType.INVALID_TOKEN;
						estado = 9;
					}
				break;

				case 2:											//Estado de operadores de dois digitos que o segundo dígito é =
					if (c == '='){
						lex.token += (char) c;
						estado = 8;
					} else {
						input.unread(c);
						estado = 8;
					}
				break;
	            
				case 3:											//Estado de operadores de dois digitos que o segundo digito é &
					if (c == '&'){
						lex.token += (char) c;
						estado = 8;
					} else {
						lex.type = TokenType.INVALID_TOKEN;
						estado = 9;
					}
				break;

				case 4:											//Estado de operadores de dois digitos que o segundo digito é |
					if (c == '|'){
						lex.token += (char) c;
						estado = 8;
					} else {
						lex.type = TokenType.INVALID_TOKEN;
						estado = 9;
					}
				break;

	            case 5:											//Reconhecimento de padrões numéricos
	                if (Character.isDigit(c)) {
	                   lex.token += (char) c;
	                   estado = 5;
	                } else {
	                   input.unread(c);
	                   estado = 8;
	                }
	            break;

		        case 6:											//Reconhecimento de padrões de ID
	                if (Character.isDigit(c) || Character.isLetter(c) || c == '_') {
	                    lex.token += (char) c;
						estado = 6;
	                } else {
	                    input.unread(c);
						estado = 8;
	                }
            	break;

				case 7: 
					if (Character.isLetter(c) && c != '}') {
						lex.token += (char) c;
						estado = 7;
					} else if (c == '}') {
						lex.token += (char) c;
						estado = 8;
					} else {
						lex.type = TokenType.INVALID_TOKEN;
						estado = 9;
					}
				break;
	        }
	    }
		lex.type = st.find(lex.token);
	    return lex;
	}
}
