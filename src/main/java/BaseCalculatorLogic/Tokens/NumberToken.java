package BaseCalculatorLogic.Tokens;

public class NumberToken implements Token {
    final NumberWithBase number;

     public NumberToken(NumberWithBase number) {
        this.number = number;
    }

    public NumberWithBase getNumber() {
        return number;
    }
}
