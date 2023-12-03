package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains the information about a 
 * room in the Adventure Game.
 */
public interface Room extends Serializable {

    /**
     * Returns a comma delimited list of every
     * object's description that is in the given room,
     * e.g. "a can of tuna, a beagle, a lamp".
     *
     * @return delimited string of object descriptions
     */
    public String getObjectString();

    /**
     * Returns a comma delimited list of every
     * move that is possible from the given room,
     * e.g. "DOWN, UP, NORTH, SOUTH".
     *
     * @return delimited string of possible moves
     */
    public String getCommands();

    /**
     * This method adds a game object to the room.
     *
     * @param object to be added to the room.
     */
    public void addGameObject(AdventureObject object);

    /**
     * This method removes a game object from the room.
     *
     * @param object to be removed from the room.
     */
    public void removeGameObject(AdventureObject object);

    /**
     * This method checks if an object is in the room.
     *
     * @param objectName Name of the object to be checked.
     * @return true if the object is present in the room, false otherwise.
     */
    public boolean checkIfObjectInRoom(String objectName);

    /**
     * Sets the visit status of the room to true.
     */
    public void visit();

    /**
     * Getter for returning an AdventureObject with a given name
     *
     * @param objectName: Object name to find in the room
     * @return: AdventureObject
     */
    public AdventureObject getObject(String objectName);

    /**
     * Getter method for the number attribute.
     *
     * @return: number of the room
     */
    public int getRoomNumber();

    /**
     * Getter method for the description attribute.
     *
     * @return: description of the room
     */
    public String getRoomDescription();


    /**
     * Getter method for the name attribute.
     *
     * @return: name of the room
     */
    public String getRoomName();


    /**
     * Getter method for the visit attribute.
     *
     * @return: visit status of the room
     */
    public boolean getVisited();


    /**
     * Getter method for the motionTable attribute.
     *
     * @return: motion table of the room
     */
    public PassageTable getMotionTable();

    /**
     * Getter method for the objectsInRoom attribute.
     *
     * @return: The arraylist for the objectsinRoom
     */
    public ArrayList<AdventureObject> getObjectsinRoom();
}
