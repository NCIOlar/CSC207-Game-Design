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

    public Fighting_Troll(int health, int damage) {
        this.health = health;
        this.damage = damage;
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
        return true;
    }
}
