package AdventureModel;

import jdk.jshell.spi.ExecutionControl;
import AdventureModel.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class Player implements Serializable {
    /**
     * The current room that the player is located in.
     */
    private Room currentRoom;
    private Shop shop;

    /**
     * The list of items that the player is carrying at the moment.
     */
    public ArrayList<AdventureObject> inventory;
    public String name;
    public int health;
    public int damage;
    public int defense;
    public int funds;
    public boolean isImmune; // for mask item

    public String requiredObj; // Required Object for entering a room
    /**
     * Adventure Game Player Constructor
     */
    public Player(Room currentRoom) {
        this.inventory = new ArrayList<AdventureObject>();
        this.currentRoom = currentRoom;
        this.health = 100;
        this.damage = 20;
        this.defense = 0;
        this.funds = 20;
        this.isImmune = false;
    }

    /**
     * This method adds an object into players inventory if the object is present in
     * the room and returns true. If the object is not present in the room, the method
     * returns false.
     *
     * @param object name of the object to pick up
     * @return true if picked up, false otherwise
     */
    public boolean takeObject(String object){
        if(this.currentRoom.checkIfObjectInRoom(object)){
            AdventureObject object1 = this.currentRoom.getObject(object);
            this.currentRoom.removeGameObject(object1);
            this.addToInventory(object1);
            return true;
        } else {
            return false;
        }
    }


    /**
     * checkIfObjectInInventory
     * __________________________
     * This method checks to see if an object is in a player's inventory.
     *
     * @param s the name of the object
     * @return true if object is in inventory, false otherwise
     */
    public boolean checkIfObjectInInventory(String s) {
        for (AdventureObject adventureObject : this.inventory) {
            if (adventureObject.getName().equals(s)) return true;
        }
        return false;
    }


    /**
     * This method drops an object in the players inventory and adds it to the room.
     * If the object is not in the inventory, the method does nothing.
     *
     * @param s name of the object to drop
     */
    public void dropObject(String s) {
        for(int i = 0; i<this.inventory.size();i++){
            if(this.inventory.get(i).getName().equals(s)) {
                this.currentRoom.addGameObject(this.inventory.get(i));
                this.inventory.remove(i);
            }
        }
    }

    /**
     * Setter method for the current room attribute.
     *
     * @param currentRoom The location of the player in the game.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * This method adds an object to the inventory of the player.
     *
     * @param object Prop or object to be added to the inventory.
     */
    public void addToInventory(AdventureObject object) {
        this.inventory.add(object);
    }


    /**
     * Getter method for the current room attribute.
     *
     * @return current room of the player.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Getter method for string representation of inventory.
     *
     * @return ArrayList of names of items that the player has.
     */
    public ArrayList<String> getInventory() {
        ArrayList<String> objects = new ArrayList<>();
        for (AdventureObject adventureObject : this.inventory) {
            objects.add(adventureObject.getName());
        }
        return objects;
    }

    // new methods
    /**
     *
     * @return player name
     */
    public String getName() {
        return this.name;
    }
    /**
     *
     * @return player health
     */
    public int getHealth() {
        return this.health;
    }
    /**
     *
     * @return player damage
     */
    public int getDamage() {
        return this.damage;
    }
    /**
     *
     * @return player defense
     */
    public int getDefense() {
        return this.defense;
    }
    /**
     *
     * @return player funds
     */
    public int getFunds() {
        return this.funds;
    }

    /**
     *
     * @param object to be bought
     * @return if the object was successfully purchased
     */
    public boolean buyObject(AdventureObject object, Shop shop) {
        boolean outOfStock = true;
        if (shop.objectsForSale.get(object) == 0) {
            return false;
        }
        for (AdventureObject object1: shop.objectsForSale.keySet()) {
            if (!(shop.objectsForSale.get(object1) == 0)) {
                outOfStock = false;
            }
        }
        if (outOfStock) {
            shop.setOutOfStock();
        }
        if (shop.outOfStock) {
            return false;
        }else if (this.funds >= object.getCost()) {
            int updatedQuantity = shop.objectsForSale.get(object) - 1;
            shop.objectsForSale.put(object, updatedQuantity);
            this.inventory.add(object);
            this.funds -= object.getCost();
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param object to be used from player inventory and have its effects applied
     */
    public void useItem(AdventureObject object) {
        if (object.getName().equals("MEDKIT")) {
            if ((this.health + object.getEffect()) >= 100) {
                this.health = 100;
                this.inventory.remove(object);
            } else {
                this.health += object.getEffect();
                this.inventory.remove(object);
            }
        } else if (object.getName().equals("VEST")) {
            this.defense += 10;
            this.inventory.remove(object);
        } else if (object.getName().equals("MASK")) {
            this.isImmune = true;
            this.inventory.remove(object);
        } else if (object.getName().equals("STIM")) {
            this.damage += 20;
            this.inventory.remove(object);
        } else if (object.getName().equals("SKIP")) {
            this.inventory.remove(object);
        } else if (object.getName().equals("MONEY")) {
            this.funds += object.getEffect();
            this.inventory.remove(object);
        }
    }
}
