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

    private Expression parse(List<AbstractMap.SimpleEntry<Token, String>> tokens) {
        if (tokens.size() == 0) throw new RuntimeException("No tokens to parse");
        else if (tokens.size() == 1)
            if (tokens.get(0).getKey().equals(Token.NUMBER)) return new Expression.Number(tokens.get(0));
            else throw new RuntimeException("Invalid token: " + tokens.get(0).getValue());

        List<Expression> history = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            AbstractMap.SimpleEntry<Token, String> token = tokens.get(i);
            boolean isOperator = token.getKey().toString().matches("OP_.*");
            Expression.Operator op = isOperator ?(Expression.Operator) history.get(history.size()-1) :null;
            if (token.getKey().equals(Token.OPEN) && op != null) {
                Expression entry = new Expression.Operator();
                if (op.lvalue == null) op.lvalue = entry;
                else if (op.rvalue == null) op.rvalue = entry;
                else throw new RuntimeException("Invalid token: " + token.getValue() + " expected operator");
                i++;
                op.operator = tokens.get(i).getKey();
                history.add(op);
            }
            else if (token.getKey().equals(Token.CLOSE)) {
                history.remove(history.size() - 1);
            }
            else if (token.getKey().equals(Token.NUMBER) && op != null) {
                if (op.lvalue == null) op.lvalue = new Expression.Number(token);
                else if (op.rvalue == null) op.rvalue = new Expression.Number(token);
                else throw new RuntimeException("Invalid token: " + token.getValue() + " expected number");
            }
            else throw new RuntimeException("Invalid token: " + token.getValue());
        }
        return history.get(0);
    }

}
