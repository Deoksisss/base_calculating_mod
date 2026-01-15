package BaseCalculatorLogic.Tokens;

import java.util.ArrayList;
import java.util.List;

import static tools.NumberNumberWithBaseConverter.toNumberWithBase;

public class Tokenizer {
    public static List<Token> tokenizeExpression(String expression) {
        expression = expression.replaceAll("(?<!\\*)-", "+-");
        List<Token> tokens = new ArrayList<>();
        String[] multiLevel = expression.split("(?=[*+])|(?<=[*+])");
        for (int i = 0; i < multiLevel.length; i++) {
            if (multiLevel[i].isEmpty()) continue;

            switch (multiLevel[i]) {
                case "*":
                    tokens.add(new OperatorToken(Operator.MUL));
                    break;
                case "+":
                    tokens.add(new OperatorToken(Operator.ADD));
                    break;
                default:
                    tokens.add(new NumberToken(toNumberWithBase(multiLevel[i])));
            }
        }
        return tokens;
    }
}
