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
public class Gaming_Troll implements Troll {

    public Scanner scanner; //use to read input from user

    public int  answer; //what number?

    public int difficulty; // Difficulty is either 1 (easy), 2 (medium), 3 (hard)

    public Gaming_Troll() {
        Random rand = new Random();
        Integer num = rand.nextInt(10);
        this.scanner = new Scanner(System.in);
        this.answer = num;
    }

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions()
    {
        System.out.println("I am a TROLL. You must beat me at my game to pass.");
        System.out.println("I have an integer within the range from 0-9, you must guess it correctly within 3 trials to pass");
        System.out.println("Remember it is an integer");

    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        return true;
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */

    public static void main(String [] args) throws InterruptedException {
        Gaming_Troll s = new Gaming_Troll();
        boolean a = s.playGame();
    }
}
