package tools;

import BaseCalculatorLogic.NumberWithBase;
import BaseConverterLogic.Converter;

import static AltCodeGenerator.NumberCoderCore.getAbsFromAltCode;
import static AltCodeGenerator.NumberCoderCore.toAltCode;

public class NumverWithBaseTools {

    public static NumberWithBase toBase(NumberWithBase number, int currentBase) {
        return number.base() == currentBase
                ? number
                : new NumberWithBase(Converter.convert(number.number(), number.base(), currentBase), number.scale(), currentBase);
    }

    public static NumberWithBase normalize(NumberWithBase number, int maxScale, int maxIntLen) {
        String value = number.number();
        int scale = number.scale();
        value = value + "0".repeat(maxScale-scale);
        value = "0".repeat(maxIntLen-(value.length()-maxScale)) + value;
        return new NumberWithBase(value, maxScale, number.base());
    }

    public static NumberWithBase negate(NumberWithBase number) {
        return toAltCode(
                new NumberWithBase(
                        "-" + getAbsFromAltCode(number.number(), number.base()),
                        number.scale(),
                        number.base()
                )
        );
    }

}
