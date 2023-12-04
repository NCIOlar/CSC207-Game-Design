package Tests;

import AdventureModel.*;
import views.*;
import Trolls.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;
import javafx.util.Duration;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;

import org.junit.jupiter.api.Test;
import views.Map;

import static org.junit.jupiter.api.Assertions.*;

class DEV_Difficulty {

    /**
     * Test whether the design pattern for rooms are implemented correctly
     */
    @Test
    void roomReaderTest() {
        AdventureGame game1 = new AdventureGame("EasyGame");
        assertEquals(19, game1.getRooms().size());
        assertTrue(game1.getRooms().get(1) instanceof NormalRoom);
        assertTrue(game1.getRooms().get(3) instanceof TrollRoom);
        assertTrue(game1.getRooms().get(6) instanceof DamageRoom);
        assertTrue(game1.getRooms().get(8) instanceof NormalRoom);
        assertTrue(game1.getRooms().get(9) instanceof TrollRoom);

        AdventureGame game2 = new AdventureGame("MediumGame");
        assertEquals(19, game2.getRooms().size());
        assertTrue(game2.getRooms().get(1) instanceof NormalRoom);
        assertTrue(game2.getRooms().get(3) instanceof TrollRoom);
        assertTrue(game2.getRooms().get(6) instanceof DamageRoom);
        assertTrue(game2.getRooms().get(8) instanceof NormalRoom);
        assertTrue(game2.getRooms().get(9) instanceof TrollRoom);

        AdventureGame game3 = new AdventureGame("HardGame");
        assertEquals(19, game2.getRooms().size());
        assertTrue(game3.getRooms().get(1) instanceof NormalRoom);
        assertTrue(game3.getRooms().get(3) instanceof TrollRoom);
        assertTrue(game3.getRooms().get(6) instanceof DamageRoom);
        assertTrue(game3.getRooms().get(8) instanceof NormalRoom);
        assertTrue(game3.getRooms().get(9) instanceof TrollRoom);
    }

    @Test
    void interpretActionTest() throws IOException {
        AdventureGame game = new AdventureGame("EasyGame");
        game.interpretAction("W");
        assertEquals(2, game.player.getCurrentRoom().getRoomNumber());
        String string1 = game.interpretAction("W");
        assertEquals("INVALID COMMAND, YOU ARE STARVING, YOU NEED POTATO TO GO TO THE NEXT ROOM", string1);
        assertEquals(2, game.player.getCurrentRoom().getRoomNumber());
        game.interpretAction("Take potato");
        game.interpretAction("W");
        assertEquals(6, game.player.getCurrentRoom().getRoomNumber());
        game.interpretAction("S");
        String string2 = game.interpretAction("A");
        assertEquals(3, game.player.getCurrentRoom().getRoomNumber());
        Fighting_Troll troll = new Fighting_Troll(1, 1);
        assertEquals(troll.getInstructions(),string2);
    }

    @Test
    void makingTrollTest() {
        Troll troll = new Fighting_Troll(1, 1);
        assertTrue(troll instanceof Fighting_Troll);
        assertEquals(1, ((Fighting_Troll) troll).getHealth());
        assertEquals(1, ((Fighting_Troll) troll).getDamage());
        assertEquals("HUMAN! Your inferior species shall be extinguished. Starting with you.\n" +
        "You encounter a Krug ahead of you.\nA fierce battle between it and you commences...", ((Fighting_Troll) troll).getInstructions());
    }

    @Test
    void formatTextTest() {
        String textToDisplay = new String("");
        AdventureGame model = new AdventureGame("EasyGame");
        if (textToDisplay == null || textToDisplay.isBlank() || textToDisplay.equals("YOU WIN")) {
            String roomDesc = model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) textToDisplay = roomDesc + "\n\nObjects in this room:\n" + objectString;
            else textToDisplay = roomDesc;
        } else {
            textToDisplay = textToDisplay;
        }
        assertTrue(textToDisplay.contains("You are at your living quarters."));

        model = new AdventureGame("EasyGame");
        model.interpretAction("W");
        textToDisplay = new String("");
        if (textToDisplay == null || textToDisplay.isBlank() || textToDisplay.equals("YOU WIN")) {
            String roomDesc = model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) textToDisplay = roomDesc + "\n\nObjects in this room:\n" + objectString;
            else textToDisplay = roomDesc;
        } else {
            textToDisplay = textToDisplay;
        }
        assertTrue(textToDisplay.contains("Objects in this room:\nA scrumptious raw potato"));
    }

}
