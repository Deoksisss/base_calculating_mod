package BaseConverterLogic;

import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;

public class ConverterCore {

    public static String convertFromDecimal(String number, Integer outputBase) {
        int n = Integer.parseInt(number);
        if (n == 0) return "0"; // <- исправлено

        int d;
        StringBuilder outputNumber = new StringBuilder();
        while (n > 0) {
            d = n % outputBase;
            n = n / outputBase;
            outputNumber.append(ALPHABET.charAt(d));
        }
        return outputNumber.reverse().toString();
    }


    public static String convertToDecimal(String number, Integer inputBase){

        int len = number.length()-1;
        int outputNumber = 0;
        for (int i = 0; i < number.length(); i++){
            int currentNumber = ALPHABET.indexOf(number.charAt(i));
            outputNumber += currentNumber*((int) Math.pow(inputBase, len-i));
        }

        return String.valueOf(outputNumber);
    }
}
