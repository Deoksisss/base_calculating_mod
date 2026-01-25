package Tokens;

import java.util.ArrayList;
import java.util.List;

import static tools.NumberNumberWithBaseConverter.toNumberWithBase;

public class Tokenizer {
    public static List<Token> tokenizeExpression(String expression) {
        expression = expression.replaceAll("(?<!\\*)-", "+-");
        List<Token> tokens = new ArrayList<>();
        String[] multiLevel = expression.split("(?=[*+])|(?<=[*+])");
        for (String s : multiLevel) {
            if (s.isEmpty()) continue;

            switch (s) {
                case "*":
                    tokens.add(new OperatorToken(Operator.MUL));
                    break;
                case "+":
                    tokens.add(new OperatorToken(Operator.ADD));
                    break;
                default:
                    tokens.add(new NumberToken(toNumberWithBase(s)));
            }
        }
        return tokens;
    }
}
