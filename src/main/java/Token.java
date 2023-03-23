public enum Token {
    OPEN("\\("),
    CLOSE("\\)"),
    OP_ADD("\\+"),
    OP_SUB("\\-"),
    OP_MUL("\\*"),
    NUMBER("\\d+");

    private String regex;

    Token(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}