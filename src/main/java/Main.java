import java.io.*;
import java.util.AbstractMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Interpreter inter = new Interpreter();

        if (args.length == 1) { // Read from file
            File file = getFile(args[0]);

            try ( BufferedReader br = new BufferedReader(new FileReader(file)) ) {
                String line; int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    System.out.println("--- Interpreting line: " + lineNum++);
                    List<AbstractMap.SimpleEntry<Token, String>> tokens = inter.lexer(line);
                    tokens.forEach(p -> System.out.println(p.getKey() + " " + p.getValue()));
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + file.getAbsolutePath());
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else { // REPL mode (from console input)

        }
    }

    private static File getFile(String fileName) {
        String path = Main.class.getResource(fileName).getFile();
        System.out.println(path);
        return new File(path.replace("%20", " "));
    }
}