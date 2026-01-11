package BaseCalculatorLogic;

import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import static AltCodeGenerator.NumberCoderCore.getAbsFromAltCode;
import static AltCodeGenerator.NumberCoderCore.toAltCode;
import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;
import static tools.NumberTools.isNegative;
import static tools.NumverWithBaseTools.*;

public class CalculatorCore {

    public static NumberWithBase sum(NumberWithBase fOperand, NumberWithBase sOperand) {

        int carry = 0;
        int s, f, d;
        int currentBase = Math.max(fOperand.base(), sOperand.base());

        fOperand = toAltCode(toBase(fOperand, currentBase));
        sOperand = toAltCode(toBase(sOperand, currentBase));

        int maxScale = Math.max(fOperand.scale(), sOperand.scale());
        int maxIntLen = Math.max(fOperand.number().length()-maxScale, sOperand.number().length()-maxScale);

        fOperand = normalize(fOperand, maxScale, maxIntLen);
        sOperand = normalize(sOperand, maxScale, maxIntLen);

        String firstNumber = fOperand.number();
        String secondNumber = sOperand.number();

        StringBuilder sb = new StringBuilder(firstNumber.length());

        for (int i = firstNumber.length()-1; i >= 0; i--) {
            f = ALPHABET.indexOf(firstNumber.charAt(i));
            s = ALPHABET.indexOf(secondNumber.charAt(i));
            d = f + s + carry;
            carry = d / currentBase;
            sb.append(ALPHABET.charAt(d % currentBase));
        }

        if (carry != 0) {
            sb.append(ALPHABET.charAt(carry));
        }

        return new NumberWithBase(sb.reverse().toString(), maxScale, currentBase);
    }

    public static NumberWithBase multiply(NumberWithBase fOperand, NumberWithBase sOperand) {
        int s, f, d;
        int currentBase = Math.max(fOperand.base(), sOperand.base());
        NumberWithBase current = new NumberWithBase("0", 0, currentBase);
        NumberWithBase currentNumber;

        fOperand = toAltCode(toBase(fOperand, currentBase));
        sOperand = toAltCode(toBase(sOperand, currentBase));

        boolean isNegative = isNegative(fOperand.number(), sOperand.number(), currentBase);

        String firstNumber = getAbsFromAltCode(fOperand.number(), fOperand.base());
        String secondNumber = getAbsFromAltCode(sOperand.number(), sOperand.base());
        for (int i = 1; i <= secondNumber.length(); i++) {
            int carry = 0;
            StringBuilder sb = new StringBuilder();
            int secondIndex = secondNumber.length() - i;
            for (int j = 1; j <= firstNumber.length(); j++) {
                s = ALPHABET.indexOf(secondNumber.charAt(secondIndex));
                f = ALPHABET.indexOf(firstNumber.charAt(firstNumber.length()-j));
                d = f*s+carry;
                carry = d / currentBase;
                sb.append(ALPHABET.charAt(d % currentBase));
            }
            if (carry != 0) {
                sb.append(ALPHABET.charAt(carry));
            }
            currentNumber = new NumberWithBase(sb.reverse().append("0".repeat(i-1)).toString(), fOperand.scale()+sOperand.scale(), currentBase);
            current = sum(currentNumber, current);

        }

        if (isNegative) {
            current = negate(current);
        }
        return current;
    }
}
