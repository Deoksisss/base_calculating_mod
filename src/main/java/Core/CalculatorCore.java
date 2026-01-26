package Core;

import Tokens.NumberWithBase;

import static Core.NumberCoderCore.toAltCode;
import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;
import static tools.NumberNumberWithBaseConverter.toNumber;
import static tools.NumberWithBaseTools.*;

public class CalculatorCore {

    public static NumberWithBase sum(NumberWithBase fOperand, NumberWithBase sOperand, StringBuilder out, StringBuilder trash) {

        int carry = 0;
        int s, f, d;
        int currentBase = Math.max(fOperand.base(), sOperand.base());
        int maxScale = Math.max(fOperand.scale(), sOperand.scale());

        out.append("Складываем ").append(fOperand.number()).append(" в ").append(fOperand.base()).append(" системе с ").append(sOperand.number()).append(" в ").append(sOperand.base()).append(" системе\n\n");
        out.append("Переведём числа в общую систему счисления.\n");

        fOperand = toBase(fOperand, currentBase, trash);
        sOperand = toBase(sOperand, currentBase, trash);


        int maxIntLen = Math.max(
                fOperand.number().length() - fOperand.scale(),
                sOperand.number().length() - sOperand.scale()
        );

        fOperand = normalize(fOperand, maxScale, maxIntLen+1);
        sOperand = normalize(sOperand, maxScale, maxIntLen+1);

        fOperand = toAltCode(fOperand);
        sOperand = toAltCode(sOperand);

        String firstNumber = fOperand.number();
        String secondNumber = sOperand.number();
        out.append("Теперь оставим без изменения положительные числа, а в отрицательных все числа заменим обратными, теперь мы имеем:").append(firstNumber).append(" и ").append(secondNumber);

        StringBuilder sb = new StringBuilder(firstNumber.length());
        out.append("\n\nТеперь поразрядно сложим числа, начиная с конца, не забывая про перенос: \n\n");
        for (int i = firstNumber.length()-1; i >= 0; i--) {
            f = ALPHABET.indexOf(firstNumber.charAt(i));
            s = ALPHABET.indexOf(secondNumber.charAt(i));
            d = f + s + carry;
            out.append(f).append("+").append(s).append("+").append(carry).append("=").append(d);
            carry = d / currentBase;
            out.append(". Перенос: ").append(carry).append("\n");
            sb.append(ALPHABET.charAt(d % currentBase));
        }
        String res = sb.reverse().toString();
        out.append("Результат сложения: ").append(res).append("\n\n\n");
        return new NumberWithBase(res, maxScale, currentBase);
    }

    public static NumberWithBase multiply(NumberWithBase fOperand, NumberWithBase sOperand, StringBuilder out, StringBuilder trash) {
        int s, f, d;
        int currentBase = Math.max(fOperand.base(), sOperand.base());

        out.append("\nУмножаем ").append(fOperand.number()).append(" в ").append(fOperand.base()).append(" системе. На ").append(sOperand.number()).append(" в ").append(sOperand.base()).append(" системе. (Про запятую пока забудем)\n\n");

        NumberWithBase current = new NumberWithBase("0", 0, currentBase);
        NumberWithBase currentNumber;
        boolean fIsNegative = fOperand.number().startsWith("-");
        boolean sIsNegative = sOperand.number().startsWith("-");
        boolean isNegative = fIsNegative ^ sIsNegative;
        out.append("Проверим число на отрицательность: первое ").append(fIsNegative ? "отрицательное " : "положительное").append(", второе ").append(sIsNegative ? "отрицательное" : "положительное").append(". Соответственно итоговое число ").append(isNegative ? "отрицательное." : "положительное.\n\n");
        out.append("Переведём числа в общую систему.\n");

        fOperand = toBase(fOperand, currentBase, trash);
        sOperand = toBase(sOperand, currentBase, trash);

         String firstNumber = fIsNegative ? fOperand.number().substring(1) : fOperand.number();
         String secondNumber = sIsNegative ? sOperand.number().substring(1) : sOperand.number();
         out.append("Будем умножать на цифры второго числа слева направо, затем результаты сложим, на забывая про пенеос.\n\n");
        for (int i = 1; i <= secondNumber.length(); i++) {
            int carry = 0;
            StringBuilder sb = new StringBuilder();
            int secondIndex = secondNumber.length() - i;
            for (int j = 1; j <= firstNumber.length(); j++) {
                s = ALPHABET.indexOf(secondNumber.charAt(secondIndex));
                char fChar = firstNumber.charAt(firstNumber.length() - j);
                f = ALPHABET.indexOf(fChar);
                d = f*s+carry;
                out.append(secondNumber.charAt(secondIndex)).append(" * ").append(fChar).append(carry).append(" = ").append(ALPHABET.charAt(d));
                carry = d / currentBase;
                out.append(". Перенос: ").append(carry).append("\n");
                sb.append(ALPHABET.charAt(d % currentBase));
            }
            if (carry != 0) {
                sb.append(ALPHABET.charAt(carry));
                out.append("Последний перенос: ").append(carry);
            }
            String currentString = sb.reverse().append("0".repeat(i-1)).toString();
            currentNumber = new NumberWithBase(currentString, fOperand.scale()+sOperand.scale(), currentBase);
            current = sum(currentNumber, current, out, trash);
            out.append("\n В результате умножение на одно из цифр второго числа получим ").append(currentString).append(". Теперь сложим его с суммой умножений на предыдущие числа: ").append(current.number()).append("\n\n");

        }
        if (isNegative) {
            current = negate(current);
        }
        out.append("Теперь Запишем результат умножения с учётом знака и запятых ").append(toNumber(current));
        return current;
    }
}
