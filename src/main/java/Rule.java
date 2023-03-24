import java.util.function.Function;

public enum Rule {
    EXPECT_OPEN((Token t) -> t == Token.OPEN),
    EXPECT_CLOSE((Token t) -> t == Token.CLOSE),
    EXPECT_OPERATOR(Token::isOperator),
    EXPECT_NUMBER((Token t) -> t == Token.NUMBER);

    private final Function<Token, Boolean> rule;

    Rule(Function<Token, Boolean> rule) {
        this.rule = rule;
    }

    public boolean apply(Token t) {
        return rule.apply(t);
    }
}