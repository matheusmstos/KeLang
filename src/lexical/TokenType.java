package lexical;

public enum TokenType {
    //FUNCTIONS
    START,
    EXIT,
    IF,
    THEN,
    ELSE,
    END,
    DO,
    WHILE,
    SCAN,
    PRINT,
    OR,
    AND,

    //VAR TYPE
    INT,
    FLOAT,
    STRING,

    //SYMBOLS
    SEMICOLON, //";"
    ASSIGN, //"="
    OPEN_BRA,
    CLOSE_BRA,
    DOT,
    OPEN_QUO, //"
    CLOSE_QUO, //"

    //OPERATORS
    EQUAL, //"=="
    GREATER,
    GREATER_EQ,
    LOWER,
    LOWER_EQ,
    LOWER_GREATER, //"<>"
    PLUS,
    MINUS,
    STAR, //"*"
    RIGHT_BAR, // "/"

    DIGIT,
    LETTER,
    CARACTER,
}
