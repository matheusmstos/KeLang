package ke_lexical;

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

	public Lexeme nextToken() throws IOException{
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
        int estado = 1;
        int c;

        while(estado != 10 && estado != 9){
            c = input.read();
            //System.out.println("lexico: ("  + estado + ", " + ((char) c) + " ["  + c + "])");
            switch(estado){
                case 1:

					if (c == ' ' || c == '\t' || c == '\r') {
					}

					else if (c == '\n') {
					   line++;
				   }

					//se comeca com digito, tem que ser NUMBER
    				else if (Character.isDigit(c)) {
    					lex.token += (char)c;
                        lex.type = TokenType.NUMBER;
    					estado = 2;
    				}

					//se comeca com aspas, tem que ser LITERAL
					else if (c == '\"') {
						lex.type = TokenType.LITERAL;
						estado = 3;
					}

					//aqui pode ser "int", "String", var...
                    else if (Character.isLetter(c)) {
                        lex.token += (char)c;
                        estado = 4;
                    }


    				else if (c == ';'  || c == '(' || c == ')' || c == '.'
    				|| c == '+' || c == '-' || c == '*' || c == '/') {
    					lex.token += (char)c;
    					estado = 10;
    				}

    				else if (c == '=' ||  c == '>') {
    					lex.token += (char)c;
    					estado = 5;
    				}

    				else if (c == '<') {
    					lex.token += (char)c;
    					estado = 6;
    				}

					else if(c == -1){
						lex = new Lexeme("", TokenType.END_OF_FILE);
						return lex;
					}

					else {
	                   lex.token += (char) c;
	                   lex.type = TokenType.INVALID_TOKEN;
	                   estado = 10;
	                }
				break;

				case 2: //isso quer dizer que ele tem um estado terminal diff

    				if (Character.isDigit(c)) {
                       lex.token += (char) c;
                       estado = 2;
                    }
                    else {
                       if (c != -1)
                           input.unread(c);
                       estado = 10;
                    }

				break;

				case 3: //esse tambem tem um estado terminal diff
					if(c == '"') {
						return lex;
						}
						else {
							if (c == -1) {
								lex.type = TokenType.UNEXPECTED_EOF;
								return lex;
							}

							lex.token += (char) c;
							estado = 10;
					}

				break;

                case 4:
                    if (Character.isDigit(c) || Character.isLetter(c)) {
                        lex.token += (char)c;
                        estado = 4;
                    }
                    else{
                        if (c != -1)
                            input.unread(c);
                        estado = 9;
                    }

                break;


				case 5:

					if (c == '='){
						lex.token += (char) c;
						estado = 9;
					}
					else{
						if (c != -1)
							input.unread(c);
						estado = 9;
					}
					break;

				case 6:

					if (c == '=' || c == '>'){
						lex.token += (char) c;
						estado = 9;
					}
					else{
						if (c != -1)
							input.unread(c);
						estado = 9;
					}
				break;
			}

		}

		if (estado == 9) {
			if(st.contains(lex.token)){
				lex.type = st.find(lex.token);
			}
			else{
				lex.type = TokenType.IDENTIFIER;
			}
		}

		return lex;
	}
}
