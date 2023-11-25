package AdventureModel;

import java.io.Serializable; //you will need this to save the game!

/**
 * This class keeps track of the props or the objects in the game.
 * These objects have a name, description, and location in the game.
 * The player with the objects can pick or drop them as they like and
 * these objects can be used to pass certain passages in the game.
 */
public class AdventureObject implements Serializable {
    /**
     * The name of the object.
     */
    private String objectName;

    /**
     * The description of the object.
     */
    private String description;

    /**
     * The location of the object.
     */
    private Room location = null;
    // new attributes
    public String objectType;
    public int objectEffect;
    public int objectCost;

    /**
     * Adventure Object Constructor
     * ___________________________
     * This constructor sets the name, description, and location of the object.
     *
     * @param name The name of the Object in the game.
     * @param description One line description of the Object.
     * @param location The location of the Object in the game.
     */
    public AdventureObject(String name, String description, Room location, String objectType, int objectEffect, int objectCost){
        this.objectName = name;
        this.description = description;
        this.location = location;
        this.objectType = objectType;
        this.objectEffect = objectEffect;
        this.objectCost = objectCost;
    }

    /**
     * Getter method for the name attribute.
     *
     * @return name of the object
     */
    public String getName(){
        return this.objectName;
    }

    /**
     * Getter method for the description attribute.
     *
     * @return description of the game
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * This method returns the location of the object if the object is still in
     * the room. If the object has been pickUp up by the player, it returns null.
     *
     * @return returns the location of the object if the objects is still in
     * the room otherwise, returns null.
     */
    public Room getLocation(){
        return this.location;
    }

    // returns the object's type (what it does)
    public String getType() {
        return this.objectType;
    }

    // returns the object's effect (magnitude of its effect)
    public int getEffect() {
        return this.objectEffect;
    }

    // returns the object's cost (for shop purposes)
    public int getCost() {
        return this.objectCost;
    }

    // Create new instance of Medkit item sold in shop

//    public AdventureObject newMedkit() {
//        return new AdventureObject("Medkit",
//                "A state-of-the-art medical kit equipped with advanced healing technology.\nThis heals the player for 20HP when consumed.",
//                null, "Consumable", 20, 100);
//    }
//
//    // Create new instance of Vest item sold in shop
//    public AdventureObject newVest() {
//        return new AdventureObject("Vest",
//                "A bulletproof vest crafted from durable kevlar.\nThis gives the player 10 defense when consumed.",
//                null, "Consumable", 10, 200);
//    }
//
//    // Create new instance of Mask item sold in shop
//    public AdventureObject newMask() {
//        return new AdventureObject("Mask",
//                "A gas mask blocks off noxious fumes. Perfect for exploring hazardous environments.\nThis makes the player immune to toxic gas when consumed.",
//                null, "Consumable", 0, 200);
//    }
//
//    // Create new instance of Stim item sold in shop
//    public AdventureObject newStim() {
//        return new AdventureObject("Stim",
//                "A slender syringe containing a potent cocktail of stimulants.\nThis increases player's damage by 20 when consumed",
//                null, "Consumable", 20, 200);
//    }
//
//    // Create new instance of Skip item sold in shop
//    public AdventureObject newSkip() {
//        return new AdventureObject("Skip",
//                "The Glock. One shot, one kill.\nThis item skips the current fight when consumed.",
//                null, "Consumable", 10000, 200);
//    }

}
