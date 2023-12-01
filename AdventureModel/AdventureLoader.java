package AdventureModel;

import AdventureModel.Trolls.Fighting_Troll;
import AdventureModel.Trolls.Gaming_Troll;
import AdventureModel.Trolls.Troll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class AdventureLoader. Loads an adventure from files.
 */
public class AdventureLoader {

    private AdventureGame game; //the game to return
    private String adventureName; //the name of the adventure

    /**
     * Adventure Loader Constructor
     * __________________________
     * Initializes attributes
     * @param game the game that is loaded
     * @param directoryName the directory in which game files live
     */
    public AdventureLoader(AdventureGame game, String directoryName) {
        this.game = game;
        this.adventureName = directoryName;
    }

     /**
     * Load game from directory
     */
    public void loadGame() throws IOException {
        parseRooms();
        parseObjects();
        parseSynonyms();

        Shop shop = new Shop(this.adventureName); // Create shops based on difficulty selected

    }

     /**
     * Parse Rooms File
     */
    private void parseRooms() throws IOException {

        int roomNumber;

        String roomFileName = this.adventureName + "/rooms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));

        while (buff.ready()) {

            String currRoom = buff.readLine(); // first line is the number of a room

            roomNumber = Integer.parseInt(currRoom); //current room number

            // now need to get room name
            String[] roomSettings = buff.readLine().split("/");

            // now we need to get the description
            String roomDescription = "";
            String line = buff.readLine();
            while (!line.equals("-----")) {
                roomDescription += line + "\n";
                line = buff.readLine();
            }
            roomDescription += "\n";

            Room room;
            String roomName = roomSettings[0];
            if (roomSettings.length == 4 && roomSettings[1].equals("FIGHT")) {
                Troll troll = new Fighting_Troll(Integer.parseInt(roomSettings[2]), Integer.parseInt(roomSettings[3]));
                room = new Room(roomName, roomNumber, roomDescription, adventureName, troll);
            } else if (roomSettings.length == 3 && roomSettings[1].equals("GAMES")) {
                Troll troll = new Gaming_Troll(roomSettings[2]);
                room = new Room(roomName, roomNumber, roomDescription, adventureName, troll);
            } else if (roomSettings.length == 3 && roomSettings[1].equals("DAMAGE")) {
                room = new Room(roomName, roomNumber, roomDescription, adventureName, Integer.parseInt(roomSettings[2]));
            } else {
                room = new Room(roomName, roomNumber, roomDescription, adventureName);
            }

            // now we make the motion table
            line = buff.readLine(); // reads the line after "-----"
            while (line != null && !line.equals("")) {
                String[] part = line.split(" \s+"); // have to use regex \\s+ as we don't know how many spaces are between the direction and the room number
                String direction = part[0];
                String dest = part[1];
                if (dest.contains("/")) {
                    String[] blockedPath = dest.split("/");
                    String dest_part = blockedPath[0];
                    String object = blockedPath[1];
                    Passage entry = new Passage(direction, dest_part, object);
                    room.getMotionTable().addDirection(entry);
                } else {
                    Passage entry = new Passage(direction, dest);
                    room.getMotionTable().addDirection(entry);
                }
                line = buff.readLine();
            }
            this.game.getRooms().put(room.getRoomNumber(), room);
        }

    }

     /**
     * Parse Objects File
     */
    public void parseObjects() throws IOException {

        String objectFileName = this.adventureName + "/objects.txt";
        BufferedReader buff = new BufferedReader(new FileReader(objectFileName));

        while (buff.ready()) {
            String objectName = buff.readLine();
            String objectDescription = buff.readLine();
            String objectLocation = buff.readLine();
//            String objectType = buff.readLine();
//            int objectEffect = Integer.parseInt(buff.readLine());
//            int objectCost = Integer.parseInt(buff.readLine());
            String objectType = "";
            int objectEffect = 0;
            int objectCost = 0;
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            int i = Integer.parseInt(objectLocation);
            Room location = this.game.getRooms().get(i);
            AdventureObject object = new AdventureObject(objectName, objectDescription, location, objectType, objectEffect, objectCost);
            location.addGameObject(object);
        }

    }

    /**
     * Parse Synonyms File
     */
    public void parseSynonyms() throws IOException {
        String synonymsFileName = this.adventureName + "/synonyms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(synonymsFileName));
        String line = buff.readLine();
        while(line != null){
            String[] commandAndSynonym = line.split("=");
            String command1 = commandAndSynonym[0];
            String command2 = commandAndSynonym[1];
            this.game.getSynonyms().put(command1,command2);
            line = buff.readLine();
        }

    }
}
