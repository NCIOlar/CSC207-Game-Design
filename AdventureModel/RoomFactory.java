package AdventureModel;

import Trolls.Troll;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains the information about a 
 * room factory that constructs different kinds of rooms
 */
public class RoomFactory {

    /**
     * RoomFactory Constructor
     */
    public RoomFactory() {

    }

    /**
     * Returns a normal room by using the attributes given in the arugment
     *
     * @return a new NormalRoom
     */
    public NormalRoom createRoom(String roomName, int roomNumber, String roomDescription, String adventureName) {
        return new NormalRoom(roomName, roomNumber, roomDescription, adventureName);
    }

    /**
     * Returns a troll room by using the attributes given in the arugment
     *
     * @return a new TrollRoom
     */
    public TrollRoom createRoom(String roomName, int roomNumber, String roomDescription, String adventureName, Troll troll) {
        return new TrollRoom(roomName, roomNumber, roomDescription, adventureName, troll);
    }

    /**
     * Returns a damage room by using the attributes given in the arugment
     *
     * @return a new DamageRoom
     */
    public DamageRoom createRoom(String roomName, int roomNumber, String roomDescription, String adventureName, int damage) {
        return new DamageRoom(roomName, roomNumber, roomDescription, adventureName, damage);
    }
}
