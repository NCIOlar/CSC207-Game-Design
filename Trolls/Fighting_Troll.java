package Trolls;

import AdventureModel.Player;

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

    public String instructions; // Give instructions before the fight

    public int round = 0; // Record the round number

    public Fighting_Troll(int health, int damage) {
        this.health = health;
        this.damage = damage;
        this.instructions = "Your nearsightedness doomed us all... I will make sure this virus consumes us all, starting with you.\n" +
                "You encounter an infected scientist ahead of you.\nA fierce battle between them and you commence...";
    }

    /**
     * Receive damages from the player, used when during the fight
     */
    public void getDamage(Player player) {
        health -= player.damage;
    }

    /**
     * Give damages to the player, used when during the fight
     */
    public void giveDamage(Player player) {
        if (player.defense > 0 && player.defense >= damage) {
            player.defense -= damage;
        } else if (player.defense > 0) {
            player.health -= damage - player.defense;
            player.defense = 0;
        } else {
            player.health -= damage;
        }
    }

    /**
     * Play the GameTroll game
     */
    public void playRound(Player player) {
        getDamage(player);
        giveDamage(player);
        round += 1;
    }

    /**
     * getInstructions
     * _________________________
     * Receive instructions of the troll
     *
     * @return Instructions of the troll
     */
    public String getInstructions(){
        return instructions;
    };

    /**
     * getHeatlh
     * _________________________
     * Return the health of the troll
     *
     * @return Health of the troll
     */
    public int getHealth() {return health;};

    /**
     * getDamage
     * _________________________
     * Return the damage of the troll
     *
     * @return Damage of the troll
     */
    public int getDamage() {return damage;};

}
