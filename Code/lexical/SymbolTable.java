package lexical;

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();
        
        // Palavras chave
        st.put("app", TokenType.APP);
        st.put("start", TokenType.START);
        st.put("stop", TokenType.STOP);           
        st.put("integer", TokenType.INTEGER);
        st.put("real",TokenType.REAL);
        st.put("if", TokenType.IF);
        st.put("then",TokenType.THEN);
        st.put("else",TokenType.ELSE);
        st.put("end",TokenType.END);
        st.put("repeat",TokenType.REPEAT);
        st.put("until",TokenType.UNTIL);
        st.put("while",TokenType.WHILE);
        st.put("do",TokenType.DO);
        st.put("read",TokenType.READ);
        st.put("write",TokenType.WRITE);

        //Símbolos
        st.put(";",TokenType.PVG);
        st.put(",",TokenType.VG);
        st.put("(",TokenType.OPEN_PAR);
        st.put(")",TokenType.CLOSE_PAR);
        st.put("{",TokenType.OPEN_KEY);
        st.put("}",TokenType.CLOSE_KEY);
        st.put(":=",TokenType.ATRIB);
            
        // Operadores
  
        st.put("+",TokenType.PLUS);
        st.put("-",TokenType.MINUS);
        st.put("*",TokenType.MUL);
        st.put("/",TokenType.DIV);
        st.put("||",TokenType.OR);
        st.put("&&",TokenType.AND);
        st.put("=",TokenType.EQUAL);
        st.put("!=",TokenType.NOT_EQUAL);
        st.put(">",TokenType.GREATER_THAN);
        st.put(">=",TokenType.GREATER_EQUAL);
        st.put("<",TokenType.LOWER_THAN);
        st.put("<=",TokenType.LOWER_EQUAL);
        st.put("!",TokenType.NOT);
   }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }
}
