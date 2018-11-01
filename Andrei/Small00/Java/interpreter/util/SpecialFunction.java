package interpreter.util;

import java.util.Random;
import java.util.Scanner;

import interpreter.value.Value;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;

public class SpecialFunction extends Function {

    private FunctionType type;
    private Scanner in;
    private Random rand;

    public SpecialFunction(FunctionType type) {
        this.type = type;
        this.in = new Scanner(System.in);
        this.rand = new Random();
    }

    public Value<?> call(Instance self, Arguments args) {
        Value<?> v;

        switch (type) {
            case Print:
                v = this.print(args);
                break;
            case Println:
                v = this.println(args);
                break;
            case Read:
                v = this.read(args);
                break;
            case Random:
                v = this.random(args);
                break;
            // FIXME: add the others
            case Clone:
                v = this.clone(args);
                break;
            default:
                throw new RuntimeException("FIXME: implement me!");
        }

        return v;
    }

    private Value<?> print(Arguments args) {
        if (args.contains("arg1")) {
            Value<?> v = args.getValue("arg1");
            if (v instanceof IntegerValue) {
                IntegerValue iv = (IntegerValue) v;
                System.out.print(v.value());
            } else if (v instanceof StringValue) {
                StringValue sv = (StringValue) v;
                System.out.print(sv.value());
            } else {
                throw new RuntimeException("FIXME: Implement me!");
            }
        }

        return IntegerValue.Zero;
    }

    private Value<?> println(Arguments args) {
        Value<?> v = print(args);
        System.out.println();
        return v;
    }

    private Value<?> read(Arguments args) {
        // Print the argument.
        this.print(args);

        String str = in.nextLine();
        try {
           int n = Integer.parseInt(str);
           IntegerValue iv = new IntegerValue(n);
           return iv;
        } catch (Exception e) {
           StringValue sv = new StringValue(str);
           return sv;
        }
    }

    private Value<?> random(Arguments args) {
        if (!args.contains("arg1"))
            InterpreterError.abort("system.random: primeiro argumento inexistente");
        else if (!args.contains("arg2"))
            InterpreterError.abort("system.random: segundo argumento inexistente");

        Value<?> v1 = args.getValue("arg1");
        Value<?> v2 = args.getValue("arg2");

        if (!(v1 instanceof IntegerValue))
            InterpreterError.abort("system.random: primeiro argumento não é inteiro");
	else if (!(v2 instanceof IntegerValue))
            InterpreterError.abort("system.random: segundo argumento não é inteiro");

        int n1 = ((IntegerValue) v1).value();
        int n2 = ((IntegerValue) v2).value();

        if (n2 <= n1)
            InterpreterError.abort("system.random: segundo argumento menor/igual ao primeiro");

        int r = rand.nextInt(n2 - n1 + 1) + n1;
        IntegerValue iv = new IntegerValue(r);
        return iv;
    }

    private Value<?> clone(Arguments args) {
        if (!args.contains("arg1"))
            InterpreterError.abort("clone: primeiro argumento inexistente");

        Value<?> v = args.getValue("arg1");
        if (!(v instanceof InstanceValue))
            InterpreterError.abort("clone: primeiro argumento não é instance");

        Instance i = ((InstanceValue) v).value();
        InstanceValue iv = new InstanceValue(i.dup());
        return iv;
    }

}
