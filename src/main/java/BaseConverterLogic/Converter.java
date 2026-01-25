package BaseConverterLogic;


import static tools.NumberNumberWithBaseConverter.isValid;

public class Converter {
    public static String convert(String number, int inputBase, int outputBase, StringBuilder out) {
        if (!(isValid(number+"@"+inputBase))) {
            System.out.println("error in validation");
            out.append("Число не существует в данной системе счисления");
            throw new NumberFormatException("Число не существует в данной системе счисления");
        }
        boolean fl = false;
        if (number.startsWith("-")) {
            fl = true;
            number = number.substring(1);
        }
        if (inputBase ==  10) {
            number = (fl ? "-" : "") + ConverterCore.convertFromDecimal(number, outputBase, out);
            return number;
        }
        number = (fl ? "-" : "") + ConverterCore.convertFromDecimal(ConverterCore.convertToDecimal(number, inputBase, out), outputBase, out);
        return number;
    }

}
