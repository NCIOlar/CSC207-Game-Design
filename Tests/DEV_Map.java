package Tests;

import AdventureModel.AdventureGame;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DEV_Map {

    /**
     * Test to make sure visit works so that map can properly show viewable rooms and passages
     */
    @Test
    void mapViewableTest() throws IOException {
        AdventureGame game = new AdventureGame("EasyGame");
        game.player.getCurrentRoom().visit();
        game.movePlayer("UP");
        game.player.getCurrentRoom().visit();
        assertTrue(game.player.getCurrentRoom().getVisited());
        assertTrue(game.getRooms().get(1).getVisited());
        assertFalse(game.getRooms().get(3).getVisited());
        assertFalse(game.getRooms().get(12).getVisited());
        assertFalse(game.getRooms().get(18).getVisited());
        assertFalse(game.getRooms().get(5).getVisited());

    }

    /**
     * Test to make sure blueprint works so that map can be generated properly
     */
    @Test
    void generateMapTest() throws IOException {
        ArrayList<String[]> blueprint = new ArrayList<>();
        AdventureGame game = new AdventureGame("EasyGame");
        String roomFileName = game.getDirectoryName() + "/mapLayout.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));
        while(buff.ready()){
            blueprint.add(buff.readLine().split(" "));
        }
        assertEquals("[-, -, |, -, |, -, -, -, -, -, -]", Arrays.toString(blueprint.get(1)));
        assertEquals("[-, -, -, -, |, -, -, -, |, -, -]", Arrays.toString(blueprint.get(5)));
        assertEquals("[-, -, -, -, -, -, 1, -, -, -, -]", Arrays.toString(blueprint.get(10)));
        assertEquals("[14, |, 13, |, 12, -, -, -, -, -, -]", Arrays.toString(blueprint.get(0)));
        assertEquals("[-, -, 4, |, 3, |, 2, |, 5, -, -]", Arrays.toString(blueprint.get(8)));
    }


}