import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NumberGenerator {
    Random random = new Random();
    int randomNumbers = random.nextInt(49) + 1;
    int snowballCounter = 1;
    int additionalNumber = 0;
    boolean additionalNumberCheck = false;
    byte counter = 0;
    Set<Integer> totoResults = new HashSet<>();
    Set<Integer> userTotoNumbers = new HashSet<>();

    public void totoResultsSimulator() {
        totoResults.clear();
        randomNumbers = random.nextInt(49) + 1;

        for (int x = 0; x < 6; x++) {
            if (totoResults.contains(randomNumbers)) {
                x--;
            } else {
                totoResults.add(randomNumbers);
            }
            randomNumbers = random.nextInt(49) + 1;
        }

        while (totoResults.contains(randomNumbers))
            randomNumbers = random.nextInt(49) + 1;

        additionalNumber = randomNumbers;
    }


    public float userTotoNumbers(int userTotoSystem, int numberOfTries) {
        WebScraper totoStats = new WebScraper();
        totoStats.webScraper();
        PrizeStructure prizeCheck = new PrizeStructure();
        prizeCheck.setPrize(totoStats.grp2Statistics(),totoStats.grp3Statistics(),totoStats.grp4Statistics());

        for (int x = 0; x < numberOfTries; x++) {
            //setting up prize structure
            if (x>0) {
                //snowball chance
                float randomSnowball = random.nextFloat(100) + 1;
                if (randomSnowball <= totoStats.snowballStatistics()) {
                    snowballCounter++;
                }
                if (snowballCounter == 1) {
                    prizeCheck.setGrp1Prize(1_000_000);
                } else if (snowballCounter == 2) {
                    prizeCheck.setGrp1Prize(2_400_000);
                } else if (snowballCounter == 3) {
                    prizeCheck.setGrp1Prize(4_500_000);
                } else if (snowballCounter == 4) {
                    prizeCheck.setGrp1Prize(8_000_000);
                    snowballCounter = 1;    //to cap snowball at 4 max
                }
            }

            userTotoNumbers.clear();
            randomNumbers = random.nextInt(49) + 1;

            for (int y = 0; y < userTotoSystem; y++) {
                if (userTotoNumbers.contains(randomNumbers)) {
                    y--;
                } else {
                    userTotoNumbers.add(randomNumbers);
                }
                randomNumbers = random.nextInt(49) + 1;
            }

            //prize check data collection by iterating through user hashset
            for (int setElement : userTotoNumbers) {
                if (totoResults.contains(setElement)) {
                    counter++;
                }
                if (setElement == additionalNumber) {
                    additionalNumberCheck = true;
                }

            }

            //prize check
            prizeCheck.prizeChecker(additionalNumberCheck,counter,userTotoSystem);

            //reset parameters for next run
            counter = 0;
            additionalNumberCheck = false;
        }
        //return value for prize check in simulation
        return prizeCheck.userWinnings;
    }
}
