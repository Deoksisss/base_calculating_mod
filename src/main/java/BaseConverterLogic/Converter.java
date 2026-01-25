package BaseConverterLogic;

public class Converter {
    public static String convert(String number, int inputBase, int outputBase, StringBuilder out) {
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
