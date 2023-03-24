import java.lang.constant.Constable;
import java.util.AbstractMap;

// AST node(s)
public class Node {
    public static class Vertex extends Node {
        public Token operator;
        public Node lvalue;
        public Node rvalue;

        public Vertex() {
            this.operator = null;
            this.lvalue = null;
            this.rvalue = null;
        }

        public Vertex(AbstractMap.SimpleEntry<Token, String> pair) {
            this.operator = pair.getKey();
            this.lvalue = null;
            this.rvalue = null;
        }

        private void print(int depth) {
            // FIXME something is broke here Breakpoint at Line 27
            System.out.println(" ".repeat(depth * 2) + operator);
            if (lvalue == null || rvalue == null) {
                System.out.println(" ".repeat((depth + 1) * 2) + "null");
            }
            lvalue.print(depth + 1);
            rvalue.print(depth + 1);
        }
    }

    public static class Leaf extends Node {
        public String value;

        public Leaf(AbstractMap.SimpleEntry<Token, String> pair) {
            this.value = pair.getValue();
        }

        private void print(int depth) {
            System.out.println(" ".repeat(depth * 2) + value);
        }
    }

    public void print() {
        print(0);
    }
    private void print(int depth) {
        if (this.getClass() == Node.Vertex.class)
            ((Node.Vertex) this).print(depth);
        else ((Node.Leaf) this).print(depth);
    }
}





