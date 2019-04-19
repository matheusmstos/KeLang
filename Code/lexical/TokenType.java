package lexical;

public enum TokenType {
    // Tokens Especiais
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,

    // Palavras chave
    APP,            // app
    START,          // start
    STOP,           // stop
    INTEGER,        // integer
    REAL,           // real
    IF,             // if
    THEN,           // then
    ELSE,           // else
    END,            // end
    REPEAT,         // repeat
    UNTIL,          // until
    WHILE,          // while
    DO,             // do
    READ,           // read   
    WRITE,          // write

    // Símbolos
    PVG,            // ;
    VG,             // ,
    OPEN_PAR,       // (
    CLOSE_PAR,      // )
    OPEN_KEY,       // {
    CLOSE_KEY,      // }
    ATRIB,          // :=

    // Operadores
    PLUS,           // +
    MINUS,          // -
    MUL,            // *
    DIV,            // /
    OR,             // ||
    AND,            // &&
    EQUAL,          // =
    NOT_EQUAL,      // !=
    GREATER_THAN,   // >
    GREATER_EQUAL,  // >=
    LOWER_THAN,     // <
    LOWER_EQUAL,    // <=
    NOT,            // !

    // Outros
    VAR,            // Variavel
    NUMBER,         // Números
    STRING,         // String
    
};
