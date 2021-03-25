import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("> ");
        String s = new Scanner(System.in).nextLine();
        Parsing.Calculate(s);
    }
}
