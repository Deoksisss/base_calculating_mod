package BaseConverterLogic;

public class Converter {

    public static String convert(String number, int inputBase, int outputBase) {
        if (inputBase ==  10) {
            return ConverterCore.convertFromDecimal(number, outputBase);
        }
        return  ConverterCore.convertFromDecimal(ConverterCore.convertToDecimal(number, inputBase), outputBase);
    }
}
