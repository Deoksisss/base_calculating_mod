package APIs;

import Tokens.*;

import java.util.ArrayList;
import java.util.List;

import static Core.NumberCoderCore.fromAdditionalCode;
import static Core.CalculatorCore.multiply;
import static Core.CalculatorCore.sum;
import static Tokens.Tokenizer.tokenizeExpression;
import static tools.NumberNumberWithBaseConverter.toNumber;


public class Calculator {

    public static String calculate(String expression, StringBuilder out, StringBuilder trash) {
        out.append("Начнём работу с выражением ").append(expression).append("\n\n");
        List<Token> tokens = tokenizeExpression(expression);
        List<Token> firstRound = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token instanceof OperatorToken(Operator operator) && operator == Operator.MUL) {
                NumberToken left = (NumberToken) firstRound.removeLast();
                NumberToken right = (NumberToken) tokens.get(++i);

                NumberWithBase result = multiply(left.number(),right.number(), out, trash);

                firstRound.add(new NumberToken(result));
            } else {
                firstRound.add(token);
            }
        }

        NumberWithBase result = null;
        OperatorToken pendingOp = null;

        for (Token token : firstRound) {
            if (token instanceof NumberToken(NumberWithBase number)) {
                if (result == null) {
                    result = number;
                } else if (pendingOp != null) {
                    if (pendingOp.operator() == Operator.ADD) {
                        result = sum(result, number, out, trash);
                    }
                    pendingOp = null;
                }
            } else if (token instanceof OperatorToken opToken) {
                pendingOp = opToken;
            }
        }

        if (result != null) {
            return toNumber(fromAdditionalCode(result));
        }
        return "";
    }
}
