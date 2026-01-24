package AltCodeGenerator;

import BaseCalculatorLogic.Tokens.NumberWithBase;

import static AltCodeGenerator.NumberCoderCore.toAltCode;
import static BaseCalculatorLogic.Calculator.calculate;
import static BaseCalculatorLogic.CalculatorCore.sum;
import static tools.NumberNumberWithBaseConverter.toNumberWithBase;
import static tools.NumberTools.addOne;
import static tools.NumberTools.inverse;
import static tools.NumberWithBaseTools.toBase;

public class NumberCoder {

    public static String toDirect(String inputNumber) {

        NumberWithBase number = toNumberWithBase(inputNumber);
        number = toBase(number, 2);
        String value = number.number();

        boolean isNeg = value.startsWith("-");
        if (isNeg) {
            value = value.substring(1);
        }
        return (isNeg ? "1" : "0") + value;
    }

    public static String toReverseCode(String inputNumber) {
        NumberWithBase number = toNumberWithBase(inputNumber);
        number = toBase(number, 2);
        String value = number.number();

        boolean isNeg = value.startsWith("-");
        if (isNeg) {
            value = value.substring(1);
        }
        return isNeg ? ("1" + inverse(value, 2)) : ("0" + value);
    }

    public static String toAdditionalCode(String inputNumber) {
        String ac = calculate(toReverseCode(inputNumber)+"@2+1@2");
        return ac.substring(0, ac.length()-2);
    }
}
