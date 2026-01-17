package BaseConverterLogic;

import BaseCalculatorLogic.Tokens.NumberWithBase;

public class Converter {
    public static String convert(String number, int inputBase, int outputBase) {
        boolean fl = false;
        if (number.startsWith("-")) {
            fl = true;
            number = number.substring(1);
        }
        if (inputBase ==  10) {
            number = (fl ? "-" : "") + ConverterCore.convertFromDecimal(number, outputBase);
            return number;
        }
        number = (fl ? "-" : "") + ConverterCore.convertFromDecimal(ConverterCore.convertToDecimal(number, inputBase), outputBase);
        return number;
    }

}
