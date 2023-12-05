
package Trolls;


import AdventureModel.Player;

import java.io.Serial;
import java.io.Serializable;

/**
 * Troll interface for Trolls used in the adventure game.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public interface Troll extends Serializable {

    /**
     * playGame
     * _________________________
     * Play the Trolls game
     *
     * @return true if player wins the game, else false
     */
    public void playRound(Player player);

    /**
     * getInstructions
     * _________________________
     * Receive instructions of the troll
     *
     * @return Instructions of the troll
     */
    public String getInstructions();
}
