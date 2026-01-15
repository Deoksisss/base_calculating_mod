package tools;

import BaseCalculatorLogic.Tokens.NumberWithBase;
import BaseConverterLogic.Converter;

import static AltCodeGenerator.NumberCoderCore.getAbsFromAltCode;
import static AltCodeGenerator.NumberCoderCore.toAdditionalCode;

public class NumberWithBaseTools {

    public static NumberWithBase toBase(NumberWithBase number, int currentBase) {
        return number.base() == currentBase
                ? number
                : new NumberWithBase(Converter.convert(number.number(), number.base(), currentBase), number.scale(), currentBase);
    }

    public static NumberWithBase normalize(NumberWithBase number, int maxScale, int maxIntLen) {
        String value = number.number();
        int scale = number.scale();
        int base = number.base();

        boolean negative = value.charAt(0) == '-';
        if (negative) {
            value = value.substring(1); // временно убрали минус
        }

        // нормализация дробной части
        value = value + "0".repeat(maxScale - scale);

        // нормализация целой части
        int intLen = value.length() - maxScale;
        value = "0".repeat(maxIntLen - intLen) + value;

        // возвращаем знак
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
