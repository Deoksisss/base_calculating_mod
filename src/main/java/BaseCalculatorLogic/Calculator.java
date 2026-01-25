package BaseCalculatorLogic;

import BaseCalculatorLogic.Tokens.*;

import java.util.ArrayList;
import java.util.List;

import static AltCodeGenerator.NumberCoderCore.fromAdditionalCode;
import static BaseCalculatorLogic.CalculatorCore.multiply;
import static BaseCalculatorLogic.CalculatorCore.sum;
import static BaseCalculatorLogic.Tokens.Tokenizer.tokenizeExpression;
import static tools.NumberNumberWithBaseConverter.toNumber;


public class Calculator {

    public static String calculate(String expression, StringBuilder out) {
        out.append("Начнём работу с выражением ").append(expression).append("\n\n");
        List<Token> tokens = tokenizeExpression(expression);
        List<Token> firstRound = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token instanceof OperatorToken(Operator operator) && operator == Operator.MUL) {
                NumberToken left = (NumberToken) firstRound.removeLast();
                NumberToken right = (NumberToken) tokens.get(++i);

                NumberWithBase result = multiply(left.number(),right.number(), out);

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
                        result = sum(result, number, out);
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
