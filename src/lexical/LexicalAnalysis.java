package lexical;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

public class LexicalAnalysis implements AutoCloseable{

    private int line;
    private SymbolTable st;
    private PushbackInputStream input;

    public LexicalAnalysis(String filename) throws LexicalException{
        System.out.println(filename);
        try{
            st = new SymbolTable();
            input = new PushbackInputStream(new FileInputStream(filename));
        }
        catch (Exception e){
            throw new LexicalException("Unable to open file");

        }

        line = 1;
    }

	public Lexeme nextToken() throws IOException{
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
        int estado = 1;
        int c;

        while(estado != 8){
            c = input.read();
            //System.out.println("lexico: ("  + estado + ", " + ((char) c) + " ["  + c + "])");
            switch(estado){
                case(1):

				if(c == -1){
					lex = new Lexeme("", TokenType.END_OF_FILE);
					return lex;
				}

				else if (Character.isDigit(c)) {
					lex.token += (char)c;
					estado = 2;

				}

				else if (c == ";"  || c == "(" || c == ")" || c == "."
				|| c == "+" || c == "-" || c == "*" || c == "/") {
					lex.token += (char)c;
					estado = 8;
				}

				else if (c == "=" ||  c == ">") {
					lex.token += (char)c
					estado = 3;
				}

				else if (c == "<") {
					lex.token += (char)c
					estado = 4;
				}
				break;

				case(2):

				if (Character.isDigit(c)) {
					lex.token += (char)c;
					estado = 2;
				}
				else{
					if (c == ".") {
						lex.token += (char)c;
						estado = 2;
					}
					else{
						if(c != -1){
                            input.unread(c);
						}
                        lex.type = TokenType.NUMBER;
                        return lex;
					}
				}
				break;

				case(3):

				if (c == '=')
				{
					lex.token += (char) c;
					estado = 8;
				}
				else
				{
					if (c != -1)
						input.unread(c);
					estado = 8;
				}
				break;

				case(4):

				if (c == '=' || c == '>')
				{
					lex.token += (char) c;
					estado = 8;
				}
				else
				{
					if (c != -1)
						input.unread(c);
					estado = 8;
				}

				break;
			}

		}
	}
}
