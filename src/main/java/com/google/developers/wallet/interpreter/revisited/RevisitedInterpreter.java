package com.google.developers.wallet.interpreter.revisited;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class RevisitedInterpreter {

    public static void main(String[] args) {
        System.out.println(evaluate("5 7 + 10 5 - -"));
    }

    public static int evaluate(String expression) {
        Stack<Integer> stack = new Stack<>();
        for (String s : expression.split(" ")) {
            Operation.fromString(s)
                    .ifPresentOrElse(
                            o -> stack.push(o.apply(stack.pop(), stack.pop())),
                            () -> stack.push(Integer.parseInt(s))
                    );
        }
        return stack.pop();
    }
}

enum Operation {
    ADD("+", (x, y) -> y + x),
    SUBTRACT("-", (x, y) -> y - x);

    public static final Map<String, Operation> valueMap =
            Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(o -> o.stringValue, o -> o));

    private final String stringValue;

    private final IntBinaryOperator operator;

    Operation(String stringValue, IntBinaryOperator operator) {
        this.stringValue = stringValue;
        this.operator = operator;
    }

    public static Optional<Operation> fromString(String s) {
        return Optional.ofNullable(valueMap.get(s));
    }

    public int apply(int x, int y) {
        return operator.applyAsInt(x, y);
    }
}