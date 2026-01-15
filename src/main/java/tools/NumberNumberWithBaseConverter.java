package tools;

import BaseCalculatorLogic.Tokens.NumberWithBase;

import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;

public class NumberNumberWithBaseConverter {

    private static boolean isValid(String number) {
        int atIndex = number.indexOf('@');
        if (atIndex == -1 || number.indexOf('@', atIndex +1) != -1) {
            return false;
        }

        String numberPart = number.substring(0, atIndex);
        String basePart = number.substring(atIndex +1);

        if (numberPart.isEmpty() || numberPart.equals("-")) {
            return false;
        }

        if (!(numberPart.startsWith("-") || numberPart.indexOf('-') == -1)) {
            return false;
        }

        String unsigned = numberPart.startsWith("-")
                ? numberPart.substring(1)
                : numberPart;

        if (unsigned.startsWith(",") || unsigned.endsWith(",")) {
            return false;
        }

        int firstCommaIndex = numberPart.indexOf(',');
        int lastCommaIndex = numberPart.lastIndexOf(',');
        if (firstCommaIndex != lastCommaIndex) {
            return false;
        }

        int base;
        try {
            base = Integer.parseInt(basePart);
        } catch (NumberFormatException e) {
            return false;
        }
        if (base < 2 || base > ALPHABET.length()) {
            return false;
        }

        String allowed = ALPHABET.substring(0, base);

        for (int i = 0; i < numberPart.length(); i++) {
            char c = numberPart.charAt(i);
            if (c == ',' || c == '-') {
                continue;
            }
            if (allowed.indexOf(c) == -1) {
                return false;
            }
        }


        return true;
    }

    public static NumberWithBase toNumberWithBase(String inputNumber) {

        if (!(isValid(inputNumber))) {
            throw new NumberFormatException("");
        }

        int atIndex = inputNumber.indexOf('@');

        String numberPart = inputNumber.substring(0, atIndex);
        int base = Integer.parseInt(inputNumber.substring(atIndex+1));

        int scale = 0;
        String number;

        int commaIndex = numberPart.indexOf(',');
        if (commaIndex != -1) {
            scale = numberPart.length() - commaIndex - 1;
            number = numberPart.replace(",", "");
        } else {
            number = numberPart;
        }

        return new NumberWithBase(number, scale, base);

    }

    public static String toNumber(NumberWithBase number) {
        String value = number.number();
        int scale = number.scale();
        int base = number.base();

        if (scale == 0) {
            return value + "@" + base;
        } else {
            int intPartLength = value.length() - scale;
            return value.substring(0, intPartLength) + "," + value.substring(intPartLength) + "@" + base;
        }
    }

}
