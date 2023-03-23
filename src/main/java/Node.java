import java.util.AbstractMap;

public class Node {
    public static class OperatorNode extends Node {
        private Token operator;
        private Node lvalue;
        private Node rvalue;

        public OperatorNode(AbstractMap.SimpleEntry<Token, String> pair) {
            this.operator = pair.getKey();
            this.lvalue = null;
            this.rvalue = null;
        }
    }

    public static class ExpressionNode extends Node {
        private String value;

        public ExpressionNode(AbstractMap.SimpleEntry<Token, String> pair) {
            this.value = pair.getValue();
        }
    }
}





