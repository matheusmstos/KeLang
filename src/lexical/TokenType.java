package Lexical;

/**
 * Created by Matheus on 29/09/18.
 */

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

    //VAR TYPER
    INT,
    FLOAT,
    STRING,

    //SYMBOLS
    SEMICOLON, //";"
    EQUAL,
    OPEN_BRA,
    CLOSE_BRA,
    DOT,
    OPEN_QUO, //"
    CLOSE_QUO, //"

    //OPERATORS
    COMPARE, //"=="
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
