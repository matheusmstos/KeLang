package lexical;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

import java.util.*;

import javax.lang.model.util.ElementScanner6;

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

	    while (estado != 11 && estado != 12) {						// Estado 9 é estado de erro padrão.
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
					} else if (c == '{') {
	                   lex.token += (char) c;
	                   estado = 2;
	                } else if (Character.isLetter(c)) {
					   lex.token += (char) c;
					   estado = 3;
					} else if (c == '_') {
					   lex.token += (char) c;
					   estado = 4;
					} else if (c == ';' || c == '+' || c == '-' || c == '(' || c == ')' || c == '*' || c == '/' || c == ',') {
	                   if(c == ';'){
							lex.token += (char) c;
							lex.type = TokenType.PVG;
							estado = 11;
					   } else if(c == '+'){
							lex.token += (char) c;
							lex.type = TokenType.PLUS;
							estado = 11;
					   } else if(c == '-'){
							lex.token += (char) c;
							lex.type = TokenType.MINUS;
							estado = 11;
					   } else if(c == '('){
							lex.token += (char) c;
							lex.type = TokenType.OPEN_PAR;
							estado = 11;
					   } else if(c == ')'){
							lex.token += (char) c;
							lex.type = TokenType.CLOSE_PAR;
							estado = 11;
					   } else if(c == '*'){
							lex.token += (char) c;
							lex.type = TokenType.MUL;
							estado = 11;
					   } else if(c == '/'){
							lex.token += (char) c;
							lex.type = TokenType.DIV;
							estado = 11;
					   } else if(c == ','){
							lex.token += (char) c;
							lex.type = TokenType.VG;
							estado = 11;
					   } else {
							lex.type = TokenType.INVALID_TOKEN;
							estado = 12;
					   }
	                } else if (c == '<' || c == '>' || c == '!') {
					   lex.token += (char) c;
					   estado = 5;
					} else if (c == '|') {
					   lex.token += (char) c;
                       estado = 6;
					} else if (c == '&') {
	                   lex.token += (char) c;
	                   estado = 7;
	                } else if (Character.isDigit(c)) {
	                   lex.token += (char) c;
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
						estado = 1;
					} else {
						lex.type = TokenType.INVALID_TOKEN;
						estado = 9;
					}
				break;

				case 2:											//Estado de String
					if (c != '}'){
						lex.token += (char) c;
						estado = 2;
					} else {
						lex.token += (char) c;
						lex.type = TokenType.STRING;
						estado = 11;
					}
				break;
	            
				case 3:											//Estado de identificadores que começam com letra e palavras reservadas
					if (Character.isLetter(c)){
						//Pensando
						switch(lex.token){
							case 'a':
							case 'ap':
							case 'app':
							case 'd':
							case 'do':
							case 'e':
							case 'el':
							case 'en':
							case 'els':
							case 'end':
							case 'else':
							case 'i':
							case 'if':
							case 'in':
							case 'int':
							case 'inte':
							case 'integ':
							case 'intege':
							case 'integer:'
							case 'r':
							case 're':
							case 'rea':
							case 'rep':
							case 'read':
							case 'real':
							case 'repe':
							case 'repea':
							case 'repeat':
							case 's':
							case 'st':
							case 'sta':
							case 'sto':
							case 'star':
							case 'stop':
						}
					} else {
						lex.type = TokenType.ID;
						estado = 11;
					}
				break;

				case 4:											//Estado de identificadores que começam com _
					if (c == '_' || Character.isDigit(c) || Character.isLetter(c)){
						lex.token += (char) c;
						estado = 4;
					} else {
						lex.type = TokenType.ID;
						input.unread(c);
						estado = 11;
					}
				break;

	            case 5:											//Reconhecimento de operadores
	                if (c == '=') {
						lex.token += (char) c;
	                    switch(lex.token){
						    case '<': 
								lex.type = TokenType.LOWER_EQUAL;
							break;
						    case '>': 
						   		lex.type = TokenType.GREATER_EQUAL;
							break;
							case '!': 
								lex.type = TokenType.NOT_EQUAL;
						    break;
						    case ':': 
								lex.type = TokenType.ATRIB;
							break;
						}
						estado = 11;
	                } else {
						input.unread(c);
	                    switch(lex.token){
							case '<': 
								lex.type = TokenType.LOWER_THAN;
								estado = 11;
							break;
							case '>': 
								lex.type = TokenType.GREATER_THAN;
								estado = 11;
							break;
							case '!':
								lex.type = TokenType.NOT;
								estado = 11;
							break;
							case ':':
								lex.type = TokenType.INVALID_TOKEN;
								estado = 12;
							break;
					    }
	                   
	                }
	            break;

		        case 6:											//Reconhecimento de operadores |
	                if (c == '|') {
						lex.token += (char) c;
						lex.type = TokenType.OR;
						estado = 11;
	                } else {
						input.unread(c);
						lex.type = TokenType.INVALID_TOKEN;
						estado = 12;
	                }
            	break;

				case 7:											//Reconhecimento de operadores & 
					if (c == '&') {
						lex.token += (char) c;
						lex.type = TokenType.AND;
						estado = 11;
					} else {
						input.unread(c);
						lex.type = TokenType.INVALID_TOKEN;
						estado = 12;
					}
				break;

				case 8:											//Reconhecimento de constantes numéricas
					if	(Character.isDigit(c)){
						lex.token += (char) c;
						estado = 8;
					} else if (c == '.'){
						lex.token += (char) c;
						estado = 9;
					} else {
						input.unread(c);
						lex.type = TokenType.NUM_INT;
						estado = 11;
					}
				break;

				case 9:											//Reconhecimento de número real
					if (Character.isDigit(c)){
						lex.token += (char) c;
						estado = 10;
					} else {
						lex.type = TokenType.INVALID_TOKEN;
						estado = 12;
					}
				break;

				case 10:										//Reconhecimento de número real
					if (Character.isDigit(c)){
						lex.token += (char) c;
						estado = 10;
					} else {
						input.unread(c);
						lex.type = TokenType.NUM_REAL;
						estado = 11;
					}
				break;
	        }
	    }
		lex.type = st.find(lex.token);
	    return lex;
	}
}
