package tools;
import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;

public class NumberTools {

    public static boolean isNegative(String firstNumber, String secondNumber, int base) {
        boolean isFirstNumberNegative = (ALPHABET.indexOf(firstNumber.charAt(0)) >= base/2);
        boolean isSecondNumberNegative = (ALPHABET.indexOf(secondNumber.charAt(0)) >= base/2);
        return isFirstNumberNegative ^ isSecondNumberNegative;
    }

    public static String inverse(String number, int base) {
        char altChar;
        StringBuilder altNumber = new StringBuilder(number.length());
        for (int j = 0; j < number.length(); j++) {
            char i = number.charAt(j);
            altChar = ALPHABET.charAt(base - 1 - ALPHABET.indexOf(i));
            altNumber.append(altChar);
        }
        return altNumber.toString();
    }

    public static String addOne(String number, int base) {
        StringBuilder sb = new StringBuilder(number);
        int i = sb.length() - 1;
        while (i >= 0) {
            int idx = ALPHABET.indexOf(sb.charAt(i));
            if (idx + 1 < base) {
                sb.setCharAt(i, ALPHABET.charAt(idx + 1));
                break;
            } else {
                sb.setCharAt(i, ALPHABET.charAt(0));
                i--;
            }
        }
        return sb.toString();
    }
}
