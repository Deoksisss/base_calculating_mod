package BaseConverterLogic;


import static net.mcreator.basecalculatingmod.BaseCalculatingModMod.ALPHABET;

public class ConverterCore {

    public static String convertFromDecimal(String number, Integer outputBase, StringBuilder out) {
        String[] parts = number.split(",");

        int intPart = Integer.parseInt(parts[0]);
        double fracPart = (parts.length > 1)
                ? Double.parseDouble("0." + parts[1])
                : 0.0;

        // --- целая часть ---
        StringBuilder intResult = new StringBuilder();
        if (intPart == 0) {
            intResult.append("0");
        } else {
            while (intPart > 0) {
                int d = intPart % outputBase;
                intPart /= outputBase;
                intResult.append(ALPHABET.charAt(d));
            }
            intResult.reverse();
        }

        // --- дробная часть ---
        if (fracPart == 0) {
            return intResult.toString();
        }

        StringBuilder fracResult = new StringBuilder();
        int limit = 5;

        while (fracPart > 0 && fracResult.length() < limit) {
            fracPart *= outputBase;
            int digit = (int) fracPart;
            fracResult.append(ALPHABET.charAt(digit));
            fracPart -= digit;
        }

        return intResult + "," + fracResult;
    }


    public static String convertToDecimal(String number, Integer inputBase, StringBuilder out) {
        String[] parts = number.split(",");

        int intResult = 0;
        String intPart = parts[0];
        int power = intPart.length() - 1;

        for (int i = 0; i < intPart.length(); i++) {
            int digit = ALPHABET.indexOf(intPart.charAt(i));
            intResult += digit * Math.pow(inputBase, power--);
        }

        double fracResult = 0.0;
        if (parts.length > 1) {
            String fracPart = parts[1];
            for (int i = 0; i < fracPart.length(); i++) {
                int digit = ALPHABET.indexOf(fracPart.charAt(i));
                fracResult += digit * Math.pow(inputBase, -(i + 1));
            }
        }
        double result = intResult + fracResult;
        return String.format("%,5f", result)
                .replaceAll("\\.?0+$", "");
    }
}
