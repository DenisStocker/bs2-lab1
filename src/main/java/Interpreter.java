import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Interpreter {

    private final List<Token> tokens = List.of(Token.values());

    public Interpreter() {
    }

    public List<AbstractMap.SimpleEntry<Token, String>> lexer(String input) {
        input = input
                .replaceAll("\\(", "\\( ")
                .replaceAll("\\)", " \\)");

        return Arrays.stream(input.split(" "))
                .map(this::match)
                .collect(Collectors.toList());
    }

    public Node parser(List<AbstractMap.SimpleEntry<Token, String>> tokens) {
        if (tokens.size() == 0) throw new RuntimeException("No tokens to parse");
        else if (tokens.size() == 1)
            if (tokens.get(0).getKey().equals(Token.NUMBER)) return new Node.Leaf(tokens.get(0));
            else throw new RuntimeException("Invalid token: " + tokens.get(0).getValue());

        if (! Rule.EXPECT_OPEN.apply(tokens.get(0).getKey()))
            throw new RuntimeException("Invalid token: " + tokens.get(0).getValue());

        Node root = null;
        Token previous = tokens.get(0).getKey();
        Stack<Node> history = new Stack<>();
        history.push(new Node.Vertex());
        tokens = tokens.subList(1, tokens.size());
        for (AbstractMap.SimpleEntry<Token, String> entry : tokens) {
            Token token = entry.getKey();

            if (! previous.applyRules(token)) // Syntax error check
                throw new RuntimeException(String.format(
                        "Invalid token '%s' %s after %s with rules: %s",
                        entry.getValue(), entry.getKey(), previous, Arrays.toString(token.getRules())
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
                if (vertex.lvalue == null) vertex.lvalue = new Node.Leaf(entry);
                else vertex.rvalue = new Node.Leaf(entry);
                // TODO might need to check if rvalue is null
            }
            else throw new RuntimeException("Unexpected token: " + entry.getValue());

            previous = token;
        }
        if (root == null) throw new RuntimeException("Abstract Syntax Tree root node is null");
        return root;
    }


    /* Private helper and util methods */

    private AbstractMap.SimpleEntry<Token, String> match(String input) {
        return tokens.stream()
                .filter(token -> input.matches(token.getRegex()))
                .findFirst()
                .map(token -> new AbstractMap.SimpleEntry<>(token, input))
                .orElseThrow(() -> new RuntimeException("No match found for: " + input));
    }

    public void printAST(Node root) {
        if (root != null)
            root.print();
    }

}
