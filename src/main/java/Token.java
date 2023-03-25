public enum Token {
    OPEN("\\(", Rule.EXPECT_OPERATOR),
    CLOSE("\\)", Rule.EXPECT_CLOSE, Rule.EXPECT_OPEN, Rule.EXPECT_NUMBER),
    OP_ADD("\\+", Rule.EXPECT_OPEN, Rule.EXPECT_NUMBER),
    OP_SUB("\\-", Rule.EXPECT_OPEN, Rule.EXPECT_NUMBER),
    OP_MUL("\\*", Rule.EXPECT_OPEN, Rule.EXPECT_NUMBER),
    NUMBER("\\d+", Rule.EXPECT_NUMBER, Rule.EXPECT_CLOSE, Rule.EXPECT_OPEN);

    private final String regex;
    private final Rule[] rules;

    Token(String regex, Rule... rules) {
        this.regex = regex;
        this.rules = rules;
    }

    public String getRegex() {
        return regex;
    }

    public Rule[] getRules() {
        return rules;
    }

    public boolean isOperator() {
        return this.toString().matches("OP_.*");
    }
}