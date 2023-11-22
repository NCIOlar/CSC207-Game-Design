package AdventureModel.Trolls;

import java.util.*;

/**
 * Class GameTroll.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class Fighting_Troll implements Troll {

    public int damage; // Amount of damage to give for each round

    public int health; // Amount of Hp it has

    public int difficulty; // Difficulty is either 1 (easy), 2 (medium), 3 (hard)

    public Fighting_Troll() {
        Random rand = new Random();
        Integer num = rand.nextInt(10);
    }

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions()
    {
        System.out.println("HUMAN! Your inferior species shall be extinguished. Starting with you.");
        System.out.println("You encounter a Krug ahead of you.");
        System.out.println("A fierce battle between it and you commences...");

    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        for (Integer i = 0; i < 3; i++) {
            String answer = scanner.next();
            try {
                Integer ans = Integer.parseInt(answer);
                if (ans.equals(this.answer)) {
                    System.out.println("Nice job, you may pass!");
                    return true;
                } else if (ans > this.answer) {
                    System.out.println("Too high");
                    System.out.println("Try Again");
                } else {
                    System.out.println("Too low");
                    System.out.println("Try Again");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("This is not an integer!");
                System.out.println("Try Again");
            }
        }
        System.out.println("You lose. You shall not pass\n");
        return false;
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */

    public static void main(String [] args) throws InterruptedException {
        Fighting_Troll s = new Fighting_Troll();
        boolean a = s.playGame();
    }
}
