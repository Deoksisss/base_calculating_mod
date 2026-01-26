package APIs;

import Tokens.NumberWithBase;

import static APIs.Calculator.calculate;
import static tools.NumberNumberWithBaseConverter.toNumberWithBase;
import static tools.NumberTools.inverse;
import static tools.NumberWithBaseTools.toBase;

public class NumberCoder {

    public static String toDirect(String inputNumber, StringBuilder out) {

        NumberWithBase number = toNumberWithBase(inputNumber);
        if (number.scale() == 0) {
            out.append("\n\nПереведём число в двоичную ").append(inputNumber).append("в двоичную систему счисления.\n");
            number = toBase(number, 2, out);
        } else {
            out.append("\n\nПрямой код существует только для целых чисел");
            throw new IllegalArgumentException("Вы ввели число в плавающей запятой. Это запрщено");
        }

        String value = number.number();

        boolean isNeg = value.startsWith("-");
        if (isNeg) {
            value = value.substring(1);
        }
        String res = (isNeg ? "1" : "0") + value;
        out.append("\nТеперь припишем в начале 1, если число отрицательное, 0 - если положительное (обозначает знак). Получим прямой код числа: ").append(res);
        return res;
    }

    public static String toReverseCode(String inputNumber, StringBuilder out) {
        NumberWithBase number = toNumberWithBase(inputNumber);
        if (number.scale() == 0) {
            out.append("\n\nПереведём число в двоичную ").append(inputNumber).append("в двоичную систему счисления.\n");
            number = toBase(number, 2, out);
        } else {
            out.append("\n\nОбратный код существует только для целых чисел");
            throw new IllegalArgumentException("Вы ввели число в плавающей запятой. Это запрщено");
        }
        String value = number.number();

        boolean isNeg = value.startsWith("-");
        if (isNeg) {
            value = value.substring(1);
        }
        String res = isNeg ? ("1" + inverse(value, 2)) : ("0" + value);
        out.append("теперь, если число положительное, просто припишем к нему 0, а если отрицательное припишем 1 и заменим все 0 в числе на 1 и наоброт. Получим обратный код: ").append(res).append("\n");
        return res;
    }

    public static String toAdditionalCode(String inputNumber, StringBuilder out) {
        out.append("\n\nЧтобы получить дополнительный код числа, нужно просто прибавить 1 к обратному коду числа. Поэтому сначала получим обратный код числа.\n\n");
        String ac = calculate(toReverseCode(inputNumber, out)+"@2+1@2", out, out);
        String res = ac.substring(0, ac.length()-2);
        out.append("Получим дополнительынй код числа: ").append(res);
        return res;
    }
}
