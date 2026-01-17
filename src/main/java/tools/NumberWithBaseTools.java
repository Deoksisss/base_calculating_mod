package tools;

import BaseCalculatorLogic.Tokens.NumberWithBase;

import static AltCodeGenerator.NumberCoderCore.getAbsFromAltCode;
import static AltCodeGenerator.NumberCoderCore.toAdditionalCode;
import static BaseConverterLogic.Converter.convert;
import static tools.NumberNumberWithBaseConverter.toNumberWithBase;

public class NumberWithBaseTools {

    public static NumberWithBase toBase(NumberWithBase number, int currentBase) {
        String value = number.number();
        value = value.substring(0, number.scale()) + "," + value.substring(number.scale()+1);


        return number.base() == currentBase
                ? number
                : toNumberWithBase(convert(value, number.base(), currentBase) + "@" + currentBase);
    }

    public static NumberWithBase normalize(NumberWithBase number, int maxScale, int maxIntLen) {
        String value = number.number();
        int scale = number.scale();
        int base = number.base();

        boolean negative = value.charAt(0) == '-';
        if (negative) {
            value = value.substring(1);
        }

        value = value + "0".repeat(maxScale - scale);

        int intLen = value.length() - maxScale;
        value = "0".repeat(maxIntLen - intLen) + value;

        if (negative) {
            value = "-" + value;
        }

        return new NumberWithBase(value, maxScale, base);
    }


    public static NumberWithBase negate(NumberWithBase number) {
        return toAdditionalCode(
                new NumberWithBase(
                        "-" + getAbsFromAltCode(number.number(), number.base()),
                        number.scale(),
                        number.base()
                )
        );
    }

}
