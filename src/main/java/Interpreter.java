import util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Interpreter {

    private final List<Token> tokens = List.of(Token.values());

    public Interpreter() {
    }

    public List<Pair<Token, String>> lexer(String input) {
        input = input
                .replaceAll("\\(", "\\( ")
                .replaceAll("\\)", " \\)");

        return Arrays.stream(input.split(" "))
                .map(this::match)
                .collect(Collectors.toList());
    }

    public Node parser(List<Pair<Token, String>> tokens) {
        if (tokens.size() == 0) throw new RuntimeException("No tokens to parse");
        else if (tokens.size() == 1)
            if (tokens.get(0).first().equals(Token.NUMBER)) return new Node.Leaf(tokens.get(0).second());
            else throw new RuntimeException("Syntax error: Invalid token: " + tokens.get(0).second());

        if (! Rule.EXPECT_OPEN.syntaxCheck(tokens.get(0).first()))
            throw new RuntimeException("Syntax error: Invalid token: " + tokens.get(0).second());

        Node root = null;
        Token previous = tokens.get(0).first();
        Stack<Node> history = new Stack<>();

        history.push(new Node.Vertex());
        tokens = tokens.subList(1, tokens.size());
        for (Pair<Token, String> entry : tokens) {
            Token token = entry.first();

            if (Stream.of(previous.getRules()).noneMatch(r -> r.syntaxCheck(token))) // Syntax error check
                throw new RuntimeException(String.format(
                        "Syntax error: Invalid token '%s' %s after %s with rules: %s",
                        entry.second(), entry.first(), previous, Arrays.toString(token.getRules())
                ));

            Node current = history.lastElement();
            if (token.equals(Token.OPEN)) {
                Node.Vertex oldVertex = (Node.Vertex) current;
                Node.Vertex newVertex = new Node.Vertex();
                history.push(newVertex);
                if (oldVertex.lvalue == null) oldVertex.lvalue = newVertex;
                else oldVertex.rvalue = newVertex;
            }
            else if (token.equals(Token.CLOSE)) {
                root = current;
                history.pop();
            }
            else if (token.isOperator()) {
                ((Node.Vertex) current).operator = token;
            }
            else if (token.equals(Token.NUMBER)) {
                Node.Vertex vertex = (Node.Vertex) current;
                if (vertex.lvalue == null) vertex.lvalue = new Node.Leaf(entry.second());
                else vertex.rvalue = new Node.Leaf(entry.second());
            }
            else throw new RuntimeException("Unexpected token: " + entry.second());
            previous = token;
        }
        if (root == null) throw new RuntimeException("Abstract Syntax Tree root node is null");
        return root;
    }

    public void emit_text(Node root) {
        List<Pair<Code, String>> code = generator(root);
        //emit(code, (Pair<Code, String> p) -> p.first().toString() + " " + p.second());
    }

    public void emit_bin(Node root) {
        List<Pair<Code, String>> code = generator(root);
        emit(code, (Pair<Code, String> p) -> p.first().instruction + " " + (byte) Integer.parseInt(p.second()));
    }



    /* Private helper and util methods */

    public void printAST(Node root) {
        if (root != null)
            root.print();
    }

    private Pair<Token, String> match(String input) {
        return tokens.stream()
                .filter(token -> input.matches(token.getRegex()))
                .findFirst()
                .map(token -> new Pair<>(token, input))
                .orElseThrow(() -> new RuntimeException("No match found for: " + input));
    }

    private List<Pair<Code, String>> generator(Node root) {
        if (root.getClass() == Node.Leaf.class) {
            Node.Leaf leaf = (Node.Leaf) root;
            return List.of(new Pair<>(Code.CONST, leaf.value));
        }

        List<Pair<Code, String>> code = new ArrayList<>();
        Stack<Node.Vertex> stack = new Stack<>();
        stack.push((Node.Vertex) root);

        // TODO - Implement generator

        code.add(new Pair<>(Code.PRINT, null));
        //code.forEach(p -> System.out.println(p.first() + " " + p.second()));
        return code;
    }

    private void emit(List<Pair<Code, String>> code, Function<Pair<Code, String>, String> mapper) {
        if (code == null) return;
        code.stream()
                .map(mapper)
                .forEach(System.out::println);
    }

}
