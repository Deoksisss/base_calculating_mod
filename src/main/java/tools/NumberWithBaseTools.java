package tools;

import BaseCalculatorLogic.Tokens.NumberWithBase;

import static AltCodeGenerator.NumberCoderCore.getAbsFromAltCode;
import static AltCodeGenerator.NumberCoderCore.toAltCode;
import static BaseConverterLogic.Converter.convert;
import static tools.NumberNumberWithBaseConverter.toNumberWithBase;

public class NumberWithBaseTools {

    public static NumberWithBase toBase(NumberWithBase number, int targetBase) {

        // если система уже нужная — просто возвращаем
        if (number.base() == targetBase) {
            return number;
        }

        String raw = number.number();
        int scale = number.scale();

        // восстановление числа с запятой
        String value;
        if (scale == 0) {
            // целое число
            value = raw;
        } else {
            int split = raw.length() - scale;

            if (split <= 0) {
                // число вида 0,00...X
                value = "0," + "0".repeat(-split) + raw;
            } else {
                value = raw.substring(0, split) + "," + raw.substring(split);
            }
        }

        // конвертация системы счисления
        String converted = convert(value, number.base(), targetBase);

        // упаковка обратно в NumberWithBase
        return toNumberWithBase(converted + "@" + targetBase);
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
        return toAltCode(
                new NumberWithBase(
                        "-" + getAbsFromAltCode(number.number(), number.base()),
                        number.scale(),
                        number.base()
                )
        );
    }

}
