package lexical;

public enum TokenType {

	//special tokens
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,

	//functions
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

    //variable types
    INT,
    FLOAT,
    STRING,

    //symbols
    SEMICOLON, //";"
    ASSIGN, //"="
    OPEN_BRA,
    CLOSE_BRA,
    DOT,
    OPEN_QUO, //"
    CLOSE_QUO, //"

    //operators
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

	//others
    NUMBER,
    LETTER,
    CARACTER,
}
