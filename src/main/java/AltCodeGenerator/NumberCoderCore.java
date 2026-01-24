package AltCodeGenerator;

import BaseCalculatorLogic.Tokens.NumberWithBase;

import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;
import static tools.NumberTools.addOne;
import static tools.NumberTools.inverse;

public class NumberCoderCore {

    public static NumberWithBase toAltCode(NumberWithBase number) {
        if (number.number().charAt(0) == '-') {
            String value = number.number().substring(1);
            value = addOne(inverse(value, number.base()), number.base()); // инверсия +1
            return new NumberWithBase(value, number.scale(), number.base());
        } else {
            return number;
        }
    }

    public static String getAbsFromAltCode(String number, int base) {
        if (number.charAt(0) >= ALPHABET.charAt(base/2)) {
            return addOne(inverse(number, base), base);
        }
        return number;
    }

    public static NumberWithBase fromAdditionalCode (NumberWithBase number) {
        String value = number.number();
        int scale = number.scale();
        int base = number.base();

        if (value.charAt(0) >= ALPHABET.charAt(base/2)) {
            value = "-" + addOne(inverse(value, base), base);
        }

        return new NumberWithBase(value, scale, base);
    }
}
