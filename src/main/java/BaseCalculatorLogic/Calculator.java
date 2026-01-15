package BaseCalculatorLogic;

import BaseCalculatorLogic.Tokens.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static AltCodeGenerator.NumberCoderCore.fromAdditionalCode;
import static BaseCalculatorLogic.CalculatorCore.multiply;
import static BaseCalculatorLogic.CalculatorCore.sum;
import static BaseCalculatorLogic.Tokens.Tokenizer.tokenizeExpression;
import static tools.NumberNumberWithBaseConverter.toNumber;


public class Calculator {

    public static String calculate(String expression) {
        List<Token> tokens = tokenizeExpression(expression);
        List<Token> firstRound = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token instanceof OperatorToken opToken && opToken.getOperator() == Operator.MUL) {
                NumberToken left = (NumberToken) firstRound.removeLast();
                NumberToken right = (NumberToken) tokens.get(++i);

                NumberWithBase result = multiply(left.getNumber(),right.getNumber());

                firstRound.add(new NumberToken(result));
            } else {
                firstRound.add(token);
            }
        }

        NumberWithBase result = null;
        OperatorToken pendingOp = null;

        for (Token token : firstRound) {
            if (token instanceof NumberToken numToken) {
                if (result == null) {
                    result = numToken.getNumber();
                } else if (pendingOp != null) {
                    if (pendingOp.getOperator() == Operator.ADD) {
                        result = sum(result, numToken.getNumber());
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
