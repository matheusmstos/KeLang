#include "Global.h"
#include "Instance.h"
#include "SpecialFunction.h"
#include "../value/FunctionValue.h"
#include "../value/InstanceValue.h"

Global* Global::global = 0;

Global::Global() {
    Instance* system = new Instance();
    system->setValue("print", new FunctionValue(new SpecialFunction(SpecialFunction::PrintFunction)));
    system->setValue("println", new FunctionValue(new SpecialFunction(SpecialFunction::PrintlnFunction)));
    system->setValue("read", new FunctionValue(new SpecialFunction(SpecialFunction::ReadFunction)));
    system->setValue("random", new FunctionValue(new SpecialFunction(SpecialFunction::RandomFunction)));
    system->setValue("clone", new FunctionValue(new SpecialFunction(SpecialFunction::CloneFunction)));
    // FIXME: Add the others.

    this->setValue("system", new InstanceValue(system));
}

Global::~Global() {
}

Global* Global::getGlobalTable() {
    if (global == 0)
        global = new Global();

    return global;
}