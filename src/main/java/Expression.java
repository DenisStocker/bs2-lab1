import java.util.AbstractMap;

// AST node(s)
public class Expression {
    public static class Operator extends Expression {
        public Token operator;
        public Expression lvalue;
        public Expression rvalue;

        public Operator() {
            this.operator = null;
            this.lvalue = null;
            this.rvalue = null;
        }

        public Operator(AbstractMap.SimpleEntry<Token, String> pair) {
            this.operator = pair.getKey();
            this.lvalue = null;
            this.rvalue = null;
        }
    }

    public static class Number extends Expression {
        public String value;

        public Number(AbstractMap.SimpleEntry<Token, String> pair) {
            this.value = pair.getValue();
        }
    }
}





