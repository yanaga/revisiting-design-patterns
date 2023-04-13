package com.google.developers.wallet.interpreter.legacy;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class LegacyInterpreter {

    public static void main(String[] args) {
        System.out.println(evaluate("5 7 + 10 5 - -"));
    }

    public static int evaluate(String expression) {
        Stack<Integer> stack = new Stack<>();
        for (String s : expression.split(" ")) {
            Operation operation = Operation.fromString(s);
            if (operation != null) {
                Integer right = stack.pop();
                Integer left = stack.pop();
                stack.push(operation.apply(left, right));
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
}

enum Operation {
    ADD("+") {
        @Override
        public int apply(int x, int y) {
            return x + y;
        }
    },
    SUBTRACT("-") {
        @Override
        public int apply(int x, int y) {
            return x - y;
        }
    };

    public static final Map<String, Operation> valueMap =
            Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(o -> o.stringValue, o -> o));

    private final String stringValue;

    Operation(String stringValue) {
        this.stringValue = stringValue;
    }

    public static Operation fromString(String s) {
        return valueMap.get(s);
    }

    public abstract int apply(int x, int y);

}