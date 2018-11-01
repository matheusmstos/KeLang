#include <cstdio>
#include <cctype>

#include "LexicalAnalysis.h"

LexicalAnalysis::LexicalAnalysis(const char* filename) : m_line(1) {
    m_file = fopen(filename, "r");
    if (!m_file)
        throw "Unable to open file";
}

LexicalAnalysis::~LexicalAnalysis() {
    fclose(m_file);
}

int LexicalAnalysis::line() const {
    return m_line;
}

struct Lexeme LexicalAnalysis::nextToken() {
    struct Lexeme lex = { "", END_OF_FILE };

    return lex;
}
