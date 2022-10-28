import java.util.Scanner;

public class Main {
    static int budget;
    static Scanner scan = new Scanner(System.in);
    static byte totoSystem;
    static public void budgetSanitizer() {
        try {
            budget = Integer.parseInt(scan.next());
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a valid input!");
            budgetSanitizer();
        }
    }
    static public void totoSanitizer() {
        try {
            totoSystem = Byte.parseByte(scan.next());
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a valid Toto System!");
            totoSanitizer();
        }

        if (totoSystem > 12 || totoSystem<6) {
            System.out.println("Please enter a valid Toto System!");
            totoSanitizer();
        }
    }

    public static void main(String[] args) {
        System.out.println("Budget used for test? ");
        budgetSanitizer();
        System.out.println("Which Toto System? ");
        totoSanitizer();

        new TotoGameSimulation(budget, totoSystem);

    }
}