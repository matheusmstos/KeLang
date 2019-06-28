package sintatico;

import java.io.IOException;

import lexico.Lexeme;
import lexico.TokenType;
import lexico.LexicalAnalysis;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;
    public static int aux;

    public SyntaticAnalysis(LexicalAnalysis l) throws IOException {
        this.lex = l;
        this.current = l.nextToken();
    }

    private void eat(TokenType type) throws IOException {
        if (type == current.type) {
            System.out.println("Comi [" + type + "]");
            current = lex.nextToken();
        } else {
            caseError();
        }
    }

    private void caseError() {

        switch (current.getType()) {
        case INVALID_TOKEN:
            System.out.println("Lexema invalido [" + current.getToken() + "]");
            System.exit(1);
            break;
        case UNEXPECTED_EOF:
        case END_OF_FILE:
            System.out.println("Fim de arquivo inesperado");
            System.exit(1);
            break;

        default:
            System.out.println("Lexema nao esperado [" + current.getToken() + "]");
            System.exit(1);
            break;
        }
    }

    public boolean execute() throws IOException {
        System.out.println("execute called");
        program();

        if (current.getType() == TokenType.END_OF_FILE) {
            return true;
        } else {
            this.caseError();
            return false;
        }
    }

    // program ::= app identifier body
    public void program() {
        System.out.println("program called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case APP:
                eat(TokenType.APP);
                identifier();
                body();
                break;

            default:
                System.out.println("Program failed - Did not start with app?");
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // identifier ::= letter |"_" {letter | digit | "_"}
    public void identifier() {
        System.out.println("identifier called [" + current.getToken() + "]");
        try {
            eat(TokenType.ID);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }

    }

    // body ::= [ decl-list] start stmt-list stop
    public void body() {
        System.out.println("body called [" + current.getToken() + "]");
        try {
            decllist();
            eat(TokenType.START);
            stmtlist();
            eat(TokenType.STOP);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // decl-list ::= decl {";" decl}
    public void decllist() {
        System.out.println("decllist called [" + current.getToken() + "]");
        try {
            decl();
            while(current.getType()==TokenType.PVG) {
                casePVG();
                decl();
            }
                           // casePVG();
            System.out.println("decllist out [" + current.getToken() + "]");
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    private void casePVG() throws IOException {
        if(current.getType()==TokenType.PVG){
            eat(TokenType.PVG);
        }
    }

    // decl ::= type ident-list
    public boolean decl() {
        System.out.println("decl called [" + current.getToken() + "]");
        try {
            switch(current.getType()){
                case INTEGER:
                case REAL:
                    type();
                    identlist();
                    break;
                default:
                    break;
            }
            // if (type()) {
            //     identlist();
            //     return true;
            // } 
            /*else {
                if(current.getType()==TokenType.PVG){
                    casePVG();
                }
                return false;
            }*/
            System.out.println("decl out [" + current.getToken() + "]");
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // ident-list ::= identifier {"," identifier}
    public void identlist() {
        System.out.println("identlist called [" + current.getToken() + "]");
        try {
            identifier();
            while (current.getType() == TokenType.VG) {
                eat(TokenType.VG);
                identifier();
            }
            System.out.println("identlist out [" + current.getToken() + "]");
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }

    }

    // type ::= integer | real
    public boolean type() {
        System.out.println("type called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case INTEGER:
                eat(TokenType.INTEGER);
                System.out.println("type out [" + current.getToken() + "]");
                return true;
            case REAL:
                eat(TokenType.REAL);
                System.out.println("type out [" + current.getToken() + "]");
                return true;
            default:
                System.out.println("Not a type - returning false");
                System.out.println("type out [" + current.getToken() + "]");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // stmt-list ::= stmt {";" stmt}
    public void stmtlist() {
        System.out.println("stmtlist called [" + current.getToken() + "]");
        try {
            stmt();
            while(current.getType()==TokenType.PVG || current.getType()==TokenType.WRITE || current.getType()==TokenType.READ || current.getType()==TokenType.ID || current.getType()==TokenType.WHILE || current.getType()==TokenType.IF || current.getType()==TokenType.REPEAT){
                casePVG();
                stmt();
            }
            // while (stmt()) {
            //     stmt();
            // }
            System.out.println("stmtlist out [" + current.getToken() + "]");
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // stmt ::= assign-stmt | if-stmt | while-stmt | repeat-stmt | read-stmt |
    // write-stmt
    public void stmt() {
        System.out.println("stmt called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case ID:
                assignstmt();
                System.out.println("Sai do assignstmt");
              //  casePVG();
              System.out.println("stmt out [" + current.getToken() + "]");
               break;
            case IF:
                ifstmt();
              //  casePVG();
              System.out.println("stmt out [" + current.getToken() + "]");
               break;
            case WHILE:
                whilestmt();
              //  casePVG();
              System.out.println("stmt out [" + current.getToken() + "]");
               break;
            case REPEAT:
                repeatstmt();
              //  casePVG();
              System.out.println("stmt out [" + current.getToken() + "]");
               break;
            case READ:
                readstmt();
              //  casePVG();
              System.out.println("stmt out [" + current.getToken() + "]");
               break;
            case WRITE:
                writestmt();
              //  casePVG();
              System.out.println("stmt out [" + current.getToken() + "]");
               break;
            default:
                System.out.println("Not a valid statement, returning false");
                System.out.println("stmt out [" + current.getToken() + "]");
                break;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // assign-stmt ::= identifier ":=" simple_expr
    public boolean assignstmt() {
        System.out.println("assignstmt called [" + current.getToken() + "]");
        try {
            identifier();
            eat(TokenType.ATRIB);
            simpleexpr();
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // if-stmt ::= if condition then stmt-list end | if condition then stmt-list
    // else stmt-list end
    public boolean ifstmt() {
        System.out.println("ifstmt called [" + current.getToken() + "]");
        try {
            eat(TokenType.IF);
            condition();
            eat(TokenType.THEN);
            stmtlist();
            if (current.getType() == TokenType.ELSE) {
                eat(TokenType.ELSE);
                stmtlist();
                eat(TokenType.END);
            } else {
            eat(TokenType.END);
            }

        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // condition ::= expression
    public void condition() {
        System.out.println("condition called [" + current.getToken() + "]");
        try {
            expression();
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // repeat-stmt ::= repeat stmt-list stmt-suffix
    public boolean repeatstmt() {
        System.out.println("repeatstmt called [" + current.getToken() + "]");
        try {
            eat(TokenType.REPEAT);
            stmtlist();
            stmtsuffix();
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // stmt-suffix ::= until condition
    public void stmtsuffix() {
        System.out.println("stmtsuffix called [" + current.getToken() + "]");
        try {
            eat(TokenType.UNTIL);
            condition();
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // while-stmt ::= stmt-prefix stmt-list end
    public boolean whilestmt() {
        System.out.println("whilestmt called [" + current.getToken() + "]");
        try {
            stmtprefix();
            stmtlist();
            eat(TokenType.END);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // stmt-prefix ::= while condition do
    public void stmtprefix() {
        System.out.println("stmtprefix called [" + current.getToken() + "]");
        try {

        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // read-stmt ::= read "(" identifier ")"
    public boolean readstmt() {
        System.out.println("readstmt called [" + current.getToken() + "]");
        try {
            eat(TokenType.READ);
            eat(TokenType.OPEN_PAR);
            identifier();
            eat(TokenType.CLOSE_PAR);

        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // write-stmt ::= write "(" writable ")"
    public boolean writestmt() {
        System.out.println("writestmt called [" + current.getToken() + "]");
        try {
            eat(TokenType.WRITE);
            eat(TokenType.OPEN_PAR);
            writable();
            eat(TokenType.CLOSE_PAR);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // writable ::= simple-expr | literal
    public void writable() {
        System.out.println("writable called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case STRING:
                literal();
                break;
            default:
                simpleexpr();
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // expression ::= simple-expr | simple-expr relop simple-expr
    // expression ::= simple-expr [ relop expression ]
    public void expression() {
        System.out.println("expression called [" + current.getToken() + "]");
        try {
            simpleexpr();
            switch (current.getType()) {
            case EQUAL:
            case GREATER_THAN:
            case GREATER_EQUAL:
            case LOWER_THAN:
            case LOWER_EQUAL:
            case NOT_EQUAL:
                relop();
                expression();
            default:
                break;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // simple-expr ::= term | simple-expr addop term
    // simple-expr ::= term [ addop simple-expr ]
    public void simpleexpr() {
        System.out.println("simpleexpr called [" + current.getToken() + "]");
        try {
                term();
               // casePVG();
                switch(current.getType()){
                    case PLUS:
                    case MINUS:
                    case OR:
                        addop();
                        simpleexpr();
                        break;
                    default: 
                        break;
                }
                // if(addop()) {
                //     simpleexpr();
                // }
                
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // term ::= factor-a | term mulop factor-a
    // term :: = factor-a [ mulop term ]
    public boolean term() {
        System.out.println("term called [" + current.getToken() + "]");
        try {
            factor_a();
            switch(current.getType()){
                case MUL:
                case DIV:
                case AND:
                    mulop();
                    term();
                default:
                    break;
            }
            // if(mulop()){
            //     term();
            // }
            return true;
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // fator-a ::= factor | "!" factor | "-" factor
    public boolean factor_a() {
        System.out.println("factor-a called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case NOT:
                eat(TokenType.NOT);
                return (factor());
            case MINUS:
                eat(TokenType.MINUS);
                return (factor());
            default:
                return (factor());
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // factor ::= identifier | constant | "(" expression ")"
    public boolean factor() {
        System.out.println("factor called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case ID:
                identifier();
                return true;
            case NUM_REAL:
            case NUM_INT:
                constant();
                return true;
            case OPEN_PAR:
                eat(TokenType.OPEN_PAR);
                expression();
                eat(TokenType.CLOSE_PAR);
                return true;
            default:
                System.out.println("Factor failed, returning false - not ID, const, (expression)");
                caseError();
                return false;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // relop ::= "=" | ">" | ">=" | "<" | "<=" | "!="
    public void relop() {
        System.out.println("relop called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case EQUAL:
                eat(TokenType.EQUAL);
                break;
            case GREATER_THAN:
                eat(TokenType.GREATER_THAN);
                break;
            case GREATER_EQUAL:
                eat(TokenType.GREATER_EQUAL);
                break;
            case LOWER_THAN:
                eat(TokenType.LOWER_THAN);
                break;
            case LOWER_EQUAL:
                eat(TokenType.LOWER_EQUAL);
                break;
            case NOT_EQUAL:
                eat(TokenType.NOT_EQUAL);
                break;
            default:
                caseError();
                break;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // addop ::= "+" | "-" | "||"
    public boolean addop() {
        System.out.println("addop called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case PLUS:
                eat(TokenType.PLUS);
                return true;
            case MINUS:
                eat(TokenType.MINUS);
                return true;
            case OR:
                eat(TokenType.OR);
                return true;
            default:
                System.out.println("addop failed, returning false");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // mulop ::= "*" | "/" | "&&"
    public boolean mulop() {
        System.out.println("mulop called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case AND:
                eat(TokenType.AND);
                return true;
            case DIV:
                eat(TokenType.DIV);
                return true;
            case MUL:
                eat(TokenType.MUL);
                return true;
            default:
                System.out.println("mulop failed, returning false");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }

    // constant ::= integer_const | float_const
    public void constant() {
        System.out.println("constant called [" + current.getToken() + "]");
        try {
            switch (current.getType()) {
            case NUM_INT:
                integerconst();
                break;
            case NUM_REAL:
                floatconst();
                break;
            default:
                caseError();
                break;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // integer_const ::= digit {digit}
    public void integerconst() {
        System.out.println("integerconst called [" + current.getToken() + "]");
        try {
            eat(TokenType.NUM_INT);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // float_const ::= digit {digit} "." digit {digit}
    public void floatconst() {
        System.out.println("integerconst called [" + current.getToken() + "]");
        try {
            eat(TokenType.NUM_REAL);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    // literal ::= " {" {caractere} "}"
    public boolean literal() {
        System.out.println("literal called [" + current.getToken() + "]" + " [" + current.getType() + "]");
        try {
            eat(TokenType.STRING);
            return true;
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
        return false;
    }
    // letter ::= [A-Za-z]
    // digit ::= [0-9]
    // caractere ::= um dos 256 caracteres do conjunto ASCII, exceto "}" e quebra de
    // linha
}
