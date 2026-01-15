package AltCodeGenerator;

import BaseCalculatorLogic.Tokens.NumberWithBase;

import static tools.NumberNumberWithBaseConverter.toNumberWithBase;
import static tools.NumberWithBaseTools.toBase;

public class NumberCoder {

    public static String toDirect(String inputNumber) {

        NumberWithBase number = toNumberWithBase(inputNumber);
        number = toBase(number, 2);

        String value = number.number();
        int scale = number.scale();
        int base = number.base();

        boolean isNeg = value.startsWith("-");
        if (isNeg) {
            value = value.substring(1);
        }


        return (isNeg ? "1" : "0") + value;
    }
}
