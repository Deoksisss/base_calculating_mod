package BaseConverterLogic;

import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;

public class ConverterCore {

    public static String convertFromDecimal(String number, Integer outputBase, StringBuilder out) {

        out.append("Преобразование числа из десятичной системы в систему с основанием ")
                .append(outputBase.toString())
                .append("\n");

        String[] parts = number.split(",");

        out.append("Разделяем число на целую и дробную части\n");

        int intPart = Integer.parseInt(parts[0]);
        double fracPart = (parts.length > 1)
                ? Double.parseDouble("0." + parts[1])
                : 0.0;

        out.append("Целая часть: ").append(intPart).append("\n");
        out.append("Дробная часть: ").append(fracPart).append("\n");

        // --- целая часть ---
        out.append("\nПреобразование целой части методом последовательного деления на основание\n");

        StringBuilder intResult = new StringBuilder();
        if (intPart == 0) {
            out.append("Целая часть равна 0, результат = 0\n");
            intResult.append("0");
        } else {
            while (intPart > 0) {
                int d = intPart % outputBase;

                out.append("Остаток от деления: ")
                        .append(d)
                        .append(" → символ '")
                        .append(ALPHABET.charAt(d))
                        .append("'\n");

                intPart /= outputBase;
                intResult.append(ALPHABET.charAt(d));
            }

            out.append("Остатки получены в обратном порядке, выполняем реверс\n");
            intResult.reverse();
        }

        // --- дробная часть ---
        if (fracPart == 0) {
            out.append("\nДробная часть отсутствует, преобразование завершено\n");
            return intResult.toString();
        }

        out.append("\nПреобразование дробной части методом последовательного умножения на основание\n");

        StringBuilder fracResult = new StringBuilder();
        int limit = 5;

        out.append("Ограничение длины дробной части: ")
                .append(limit)
                .append(" знаков\n");

        while (fracPart > 0 && fracResult.length() < limit) {
            fracPart *= outputBase;
            int digit = (int) fracPart;

            out.append("Умножаем дробную часть на основание → ")
                    .append(fracPart)
                    .append(", целая часть = ")
                    .append(digit)
                    .append(" → символ '")
                    .append(ALPHABET.charAt(digit))
                    .append("'\n");

            fracResult.append(ALPHABET.charAt(digit));
            fracPart -= digit;
        }

        out.append("\nРезультат преобразования: ")
                .append(intResult)
                .append(",")
                .append(fracResult)
                .append("\n");

        return intResult + "," + fracResult;
    }


    public static String convertToDecimal(String number, Integer inputBase, StringBuilder out) {

        out.append("Преобразование числа из системы с основанием ")
                .append(inputBase.toString())
                .append(" в десятичную систему\n");

        String[] parts = number.split(",");

        int intResult = 0;
        String intPart = parts[0];
        int power = intPart.length() - 1;

        out.append("\nПреобразование целой части по формуле позиционной системы счисления\n");

        for (int i = 0; i < intPart.length(); i++) {
            int digit = ALPHABET.indexOf(intPart.charAt(i));

            out.append("Символ '")
                    .append(intPart.charAt(i))
                    .append("' = ")
                    .append(digit)
                    .append(" * ")
                    .append(inputBase)
                    .append("^")
                    .append(power)
                    .append("\n");

            intResult += (int) (digit * Math.pow(inputBase, power--));
        }

        double fracResult = 0.0;
        if (parts.length > 1) {

            out.append("\nПреобразование дробной части по формуле отрицательных степеней основания\n");

            String fracPart = parts[1];
            for (int i = 0; i < fracPart.length(); i++) {
                int digit = ALPHABET.indexOf(fracPart.charAt(i));

                out.append("Символ '")
                        .append(fracPart.charAt(i))
                        .append("' = ")
                        .append(digit)
                        .append(" * ")
                        .append(inputBase)
                        .append("^(-")
                        .append(i + 1)
                        .append(")\n");

                fracResult += digit * Math.pow(inputBase, -(i + 1));
            }
        }

        double result = intResult + fracResult;

        out.append("\nСуммируем целую и дробную части\n");
        out.append("Итоговое значение в десятичной системе: ")
                .append(result)
                .append("\n");

        return String.format("%,5f", result)
                .replaceAll("\\.?0+$", "");
    }
}
