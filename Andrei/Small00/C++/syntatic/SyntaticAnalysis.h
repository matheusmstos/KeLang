#ifndef SYNTATIC_ANALYSIS_H
#define SYNTATIC_ANALYSIS_H

#include "../lexical/LexicalAnalysis.h"
#include <map>

class Command;

class SyntaticAnalysis {
public:
    explicit SyntaticAnalysis(LexicalAnalysis& lex);
    virtual ~SyntaticAnalysis();

    Command* start();

private:
    LexicalAnalysis& m_lex;
    Lexeme m_current;

    void matchToken(enum TokenType type);
    void showError();

};

#endif
