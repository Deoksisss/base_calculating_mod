package BaseCalculatorLogic;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public static String calculate(String expression) {
        String[] multiLevel = expression.split("(?=[+-])|(?<=[+-])");
        for (int i = 0; i < multiLevel.length; i++) {
            switch (multiLevel[i]) {
                case "+", "-":
                    break;
                default:
                    multiLevel[i] = multiParser(multiLevel[i]);
            }
        }
    }

    public static String multiParser(String expression) {
        String[] numbers = expression.split("\\*");
        List<NumberWithBase> Operands = new ArrayList<>();

        for (String n : numbers) {
            String[] parts = n.split("@");
            Operands.add(new NumberWithBase(parts[0], Integer.parseInt(parts[1])));
        }

        while (Operands.size() != 1) {
            Operands.set(0, CalculatorCore.multiply(Operands.get(0), Operands.get(1)));
            Operands.remove(1);
        }
        NumberWithBase result = Operands.get(0);
        return (result.number()+"@"+result.base());
    }
}
