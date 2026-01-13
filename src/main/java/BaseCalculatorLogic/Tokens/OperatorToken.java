package BaseCalculatorLogic.Tokens;

public class OperatorToken implements Token {

    final Operator operator;

    public OperatorToken(Operator operator) {
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }
}
