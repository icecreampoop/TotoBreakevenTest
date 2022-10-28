import java.text.NumberFormat;
import java.util.Scanner;

public class TotoGameSimulation {
    Scanner sc = new Scanner(System.in);
    float boardRuns;

    public TotoGameSimulation(int budget, int userTotoSystem) {

        //tabulating number of tries
        switch (userTotoSystem) {
            case 12 -> boardRuns = (float) budget / 924;
            case 11 -> boardRuns = (float) budget / 462;
            case 10 -> boardRuns = (float) budget / 210;
            case 9 -> boardRuns = (float) budget / 84;
            case 8 -> boardRuns = (float) budget / 28;
            case 7 -> boardRuns = (float) budget / 7;
            default -> boardRuns = budget;
        }

        NumberGenerator numGen = new NumberGenerator();
        numGen.totoResultsSimulator();
        float userSimulatedTotalWinnings = numGen.userTotoNumbers(userTotoSystem, (int)boardRuns);

        //formatting to look nicer
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String budgetPrint = formatter.format(budget);
        String winningsPrint = formatter.format(userSimulatedTotalWinnings);

        System.out.println("Budget used: " + budgetPrint);
        System.out.println("Winnings based on simulation: " + winningsPrint);
        }
}