package BaseCalculatorLogic;

import BaseCalculatorLogic.Tokens.*;

import java.util.ArrayList;
import java.util.List;

import static BaseCalculatorLogic.CalculatorCore.multiply;
import static BaseCalculatorLogic.CalculatorCore.sum;
import static BaseCalculatorLogic.Tokens.Tokenizer.tokenizeExpression;
import static tools.NumberNumberWithBaseConverter.toNumber;


public class Calculator {

    public static String Calculate(String expression) {
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

        NumberWithBase result = ((NumberToken) firstRound.get(0)).getNumber();

        for (int i = 0; i < firstRound.size(); i++) {
            OperatorToken op = (OperatorToken) firstRound.get(i);
            NumberToken next = (NumberToken) firstRound.get(++i);

            if (op.getOperator() == Operator.ADD) {
                result = sum(result,next.getNumber());
            }
        }

        return toNumber(result);
    }
}
