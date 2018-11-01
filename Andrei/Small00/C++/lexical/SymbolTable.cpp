#include "SymbolTable.h"

SymbolTable::SymbolTable() {
    // TIP: To add the tokens.
    // m_st["???"] = ???;

    // symbols

    // keywords

    // operators

}

SymbolTable::~SymbolTable() {
}

bool SymbolTable::contains(std::string token) {
    return m_st.find(token) != m_st.end();
}

enum TokenType SymbolTable::find(std::string token) {
    return this->contains(token) ?
              m_st[token] : INVALID_TOKEN;
}