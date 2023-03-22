import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        if (args.length != 1)
            throw new IllegalArgumentException("You must provide exactly one argument");

        File file = new File(args[0]);
        if (!file.exists())
            throw new IllegalArgumentException("File does not exist");
         */

        Map<String, String> tokens = new HashMap<>();

        String code = "(+ (* 5 6) (+ (* 23 5) (+ 6 26)))";

        code = code
                .replaceAll("\\(", "\\( ")
                .replaceAll("\\)", " \\)");

        Arrays.stream(code.split(" "))
                .forEach(token -> {
                    if (token.equals("\\(")) tokens.put("OPEN", token);
                    else if (token.equals("\\)")) tokens.put("CLOSE", token);
                    else if (token.matches("\\d+")) tokens.put("NUMBER", token);
                    else if (token.matches("\\+|\\*|")) tokens.put("OPERATOR", token);
        });

        tokens.forEach((k, v) -> System.out.println(k + " = " + v));
    }
}