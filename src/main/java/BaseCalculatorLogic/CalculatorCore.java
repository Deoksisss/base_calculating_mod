package BaseCalculatorLogic;

import BaseConverterLogic.Converter;
import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

public class CalculatorCore {

    private static NumberWithBase toBase(NumberWithBase number, int currentBase) {
        return number.base() == currentBase
                ? number
                : new NumberWithBase(Converter.convert(number.number(), number.base(), currentBase), currentBase);
    }

    public static NumberWithBase sum(NumberWithBase fOperand, NumberWithBase sOperand) {

        int carry = 0;
        int s, f, d;
        StringBuilder sb = new StringBuilder();
        int currentBase = Math.max(fOperand.base(), sOperand.base());

        fOperand = toBase(fOperand, currentBase);
        sOperand = toBase(sOperand, currentBase);

        String firstNumber = fOperand.number();
        String secondNumber = sOperand.number();

        for (int i = 1; i <= Math.max(firstNumber.length(), secondNumber.length()); i++) {
            int firstIndex = firstNumber.length() - i;
            int secondIndex = secondNumber.length() - i;

            f = (firstIndex >= 0) ? BaseCalculatingModMod.ALPHABET.indexOf(firstNumber.charAt(firstIndex)) : 0;
            s = (secondIndex >= 0) ? BaseCalculatingModMod.ALPHABET.indexOf(secondNumber.charAt(secondIndex)) : 0;

            d = f + s + carry;
            carry = d / currentBase;
            sb.append(BaseCalculatingModMod.ALPHABET.charAt(d % currentBase));

        }

        if (carry != 0) {
            sb.append(BaseCalculatingModMod.ALPHABET.charAt(carry));
        }

        return new NumberWithBase(sb.reverse().toString(), currentBase);
    }

    public static NumberWithBase multiply(NumberWithBase fOperand, NumberWithBase sOperand) {
        int s, f, d;
        int currentBase = Math.max(fOperand.base(), sOperand.base());
        NumberWithBase current = new NumberWithBase("0", currentBase);
        NumberWithBase currentNumber;

        fOperand = toBase(fOperand, currentBase);
        sOperand = toBase(sOperand, currentBase);

        String firstNumber = fOperand.number();
        String secondNumber = sOperand.number();
        for (int i = 1; i <= secondNumber.length(); i++) {
            int carry = 0;
            StringBuilder sb = new StringBuilder();
            int secondIndex = secondNumber.length() - i;
            for (int j = 1; j <= firstNumber.length(); j++) {
                s = BaseCalculatingModMod.ALPHABET.indexOf(secondNumber.charAt(secondIndex));
                f = BaseCalculatingModMod.ALPHABET.indexOf(firstNumber.charAt(firstNumber.length()-j));
                d = f*s+carry;
                carry = d / currentBase;
                sb.append(BaseCalculatingModMod.ALPHABET.charAt(d % currentBase));
            }
            if (carry != 0) {
                sb.append(BaseCalculatingModMod.ALPHABET.charAt(carry));
            }
            currentNumber = new NumberWithBase(sb.reverse().append("0".repeat(i-1)).toString(), currentBase);
            current = sum(currentNumber, current);

        }




        return current;
    }

}
