#include "FunctionCallExpr.h"

#include "../util/Arguments.h"
#include "../util/Function.h"
#include "../util/AccessPath.h"
#include "../util/InterpreterError.h"
#include "../value/FunctionValue.h"

FunctionCallExpr::FunctionCallExpr(AccessPath* path, int line)
    : Expr(line), m_path(path) {
}

FunctionCallExpr::~FunctionCallExpr() {
}

void FunctionCallExpr::addParam(Rhs* rhs) {
    m_params.push_back(rhs);
}

Type* FunctionCallExpr::rhs(Instance* self, Arguments* args) {
    Type* funct = m_path->value(self, args);
    if (funct->type() != Type::FunctionType)
        InterpreterError::abort(this->line());

    Memory* ref = m_path->reference(self, args);
    if (ref == 0)
        InterpreterError::abort(this->line());

    Function* f = ((FunctionValue*) funct)->value();
    Instance* fSelf = (Instance*) ref;
    Arguments* fArgs = new Arguments();

    for (int i = 0; i < m_params.size(); i++) {
        Rhs* p = m_params[i];
        Type* v = p->rhs(self, args);
        fArgs->setValue(("arg" + std::to_string(i+1)), v);
    }

    Type* ret = f->call(fSelf, fArgs);
    return ret;
}
