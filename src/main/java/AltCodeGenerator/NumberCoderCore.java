package AltCodeGenerator;

import BaseCalculatorLogic.NumberWithBase;
import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;
import static tools.NumberTools.addOne;
import static tools.NumberTools.inverse;

public class NumberCoderCore {


    public static NumberWithBase toNegCode(NumberWithBase number) {
        String raw = number.number();
        String value = raw.substring(1);

        if (raw.charAt(0) == '-') {
            return new NumberWithBase(inverse(value, number.base()), number.scale(), number.base());
        }
        else {
            return number;
        }
    }

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
}
