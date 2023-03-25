import util.Pair;

import java.io.*;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Interpreter inter = new Interpreter();

        if (args.length == 1) { // Read from file
            File file = getFile(args[0]);

            try ( BufferedReader br = new BufferedReader(new FileReader(file)) ) {
                String line; int lineNum = 1;
                while ((line = br.readLine()) != null) {
                    List<Pair<Token, String>> tokens = inter.lexer(line);
                    Node root = inter.parser(tokens);
                    inter.emit_text(root);
                    // Debug code, remove comment to get output
                    System.out.println("--- Interpreting line: " + lineNum++); // Print line number
                    //tokens.forEach(p -> System.out.println(p.first() + " " + p.second())); // Print tokens
                    inter.printAST(root); // Print AST
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + file.getAbsolutePath());
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else { // REPL mode (from console input)
            // TODO implement REPL mode
        }
    }

    private static File getFile(String fileName) {
        URL path = Main.class.getResource(fileName);
        if (path == null) throw new RuntimeException("File not found: " + fileName);
        return new File(path.getFile().replace("%20", " "));
    }
}