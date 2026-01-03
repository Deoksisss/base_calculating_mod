package BaseCalculatorLogic;

public class Calculator {

    public static String calculate(String expression) {
        String[] multiLevel = expression.split("(?=[+-])|(?<=[+-])");
        for (int i = 0; i < multiLevel.length; i++) {
            switch (multiLevel[i]) {
                case "+", "-":
                    break;
                default:
                    // сделать сюда функцию обработки умножения
            }
        }
    }
}
