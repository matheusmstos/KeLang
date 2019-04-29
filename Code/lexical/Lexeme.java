package lexical;

public class Lexeme {

    public String token;
    public TokenType type;

    public Lexeme(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    public String getToken(Lexeme lex){
        return lex.token;
    }
}
