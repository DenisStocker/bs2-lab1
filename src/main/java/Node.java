import util.Pair;

import java.util.function.Consumer;
import java.util.function.Function;

// AST node(s)
public class Node {

    private static final int intentDepthMultiplier = 3;

    public static class Vertex extends Node {
        public Token operator;
        public Node lvalue;
        public Node rvalue;

        public Vertex() {
            this.operator = null;
            this.lvalue = null;
            this.rvalue = null;
        }

        private void print(int depth) {
            System.out.println(" ".repeat(depth * intentDepthMultiplier) + operator);
            lvalue.print(depth + 1);
            rvalue.print(depth + 1);
        }
    }

    public static class Leaf extends Node {
        public String value;

        public Leaf(String value) {
            this.value = value;
        }

        private void print(int depth) {
            System.out.println(" ".repeat(depth * intentDepthMultiplier) + value);
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





