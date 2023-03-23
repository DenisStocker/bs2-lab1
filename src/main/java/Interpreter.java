import java.util.*;
import java.util.stream.Collectors;

public class Interpreter {

    private List<Token> tokens = List.of(Token.values());

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

    private AbstractMap.SimpleEntry<Token, String> match(String input) {
        return tokens.stream()
                .filter(token -> input.matches(token.getRegex()))
                .findFirst()
                .map(token -> new AbstractMap.SimpleEntry<>(token, input))
                .orElseThrow(() -> new RuntimeException("No match found for: " + input));
    }

    private Node parse(List<AbstractMap.SimpleEntry<Token, String>> tokens) {
        if (tokens.size() == 0) throw new RuntimeException("No tokens to parse");
        //else if (tokens.size() == 1) return new Node(tokens.get(0));
        List<Node> stack = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            AbstractMap.SimpleEntry<Token, String> token = tokens.get(i);
            if (token.getKey().equals(Token.OPEN)) {
                Node node = new Node.OperatorNode(tokens.get(i++));
                // TODO we left of here.. oh boy
            }
            else if (token.getKey().equals(Token.CLOSE)) {

            }
            else if (token.getKey().toString().matches("OP_.*")) {

            }
            else if (token.getKey().equals(Token.NUMBER)) {

            }
        }

        // TODO stack might be empty
        return stack.get(0);
    }

}
