package BaseCalculatorLogic;

import BaseCalculatorLogic.Tokens.*;

import java.util.ArrayList;
import java.util.List;

import static BaseCalculatorLogic.Tokens.Tokenizer.tokenizeExpression;
import static tools.NumberToNumberWithBase.toNumberWithBase;


public class Calculator {

    public static String Calculate(String expression) {
        List<Token> tokens = tokenizeExpression(expression);
    }
}
