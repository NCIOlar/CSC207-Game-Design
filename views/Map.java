//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package views;

import AdventureModel.AdventureGame;
import AdventureModel.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Map {
    AdventureGame game;
    HashMap<Integer, Room> rooms;
    GridPane map;
    ArrayList<Integer> isGenerated;
    ArrayList<String[]> blueprint = new ArrayList();

    public Map(AdventureGameView game) throws IOException {
        this.game = game.model;
        this.rooms = game.model.getRooms();
        this.map = new GridPane();
        this.isGenerated = new ArrayList(Collections.nCopies(10, 0));
        this.createBlueprint();
        this.game.player.getCurrentRoom().visit();
    }

    public void generateMap() {
        for(int i = 0; i < this.blueprint.size(); ++i) {
            for(int j = 0; j < ((String[])this.blueprint.get(i)).length; ++j) {
                String room = ((String[])this.blueprint.get(i))[j];
                Rectangle blackBox;
                if (room.equals("-")) {
                    blackBox = new Rectangle(100.0, 100.0, Color.BLACK);
                    GridPane.setRowIndex(blackBox, i);
                    GridPane.setColumnIndex(blackBox, j);
                    this.map.getChildren().add(blackBox);
                } else if (room.equals("|")) {
                    if (this.checkSurroundings(i, j)) {
                        String passageImage = this.game.getDirectoryName() + "/room-images/passage.png";
                        Image passageImageFile = new Image(passageImage);
                        ImageView passage = new ImageView(passageImageFile);
                        passage.setFitHeight(100.0);
                        passage.setFitWidth(100.0);
                        GridPane.setRowIndex(passage, i);
                        GridPane.setColumnIndex(passage, j);
                        this.map.getChildren().add(passage);
                    } else {
                        blackBox = new Rectangle(100.0, 100.0, Color.BLACK);
                        GridPane.setRowIndex(blackBox, i);
                        GridPane.setColumnIndex(blackBox, j);
                        this.map.getChildren().add(blackBox);
                    }
                } else if (((Room)this.rooms.get(Integer.parseInt(room))).getVisited()) {
                    ImageView roomImage = this.getImage(Integer.parseInt(room));
                    roomImage.setFitHeight(100.0);
                    roomImage.setFitWidth(100.0);
                    GridPane.setRowIndex(roomImage, i);
                    GridPane.setColumnIndex(roomImage, j);
                    this.map.getChildren().add(roomImage);
                } else {
                    blackBox = new Rectangle(100.0, 100.0, Color.BLACK);
                    GridPane.setRowIndex(blackBox, i);
                    GridPane.setColumnIndex(blackBox, j);
                    this.map.getChildren().add(blackBox);
                }
            }
        }

    }

    public void createBlueprint() throws IOException {
        String roomFileName = this.game.getDirectoryName() + "/mapLayout.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));

        while(buff.ready()) {
            this.blueprint.add(buff.readLine().split(" "));
        }

    }

    private ImageView getImage(int roomNumber) {
        String var10000 = this.game.getDirectoryName();
        String roomImage = var10000 + "/room-images/" + roomNumber + ".png";
        Image roomImageFile = new Image(roomImage);
        return new ImageView(roomImageFile);
    }

    private boolean checkSurroundings(int row, int column) {
        if (this.checkUp(row, column)) {
            return true;
        } else if (this.checkDown(row, column)) {
            return true;
        } else if (this.checkLeft(row, column)) {
            return true;
        } else {
            return this.checkRight(row, column);
        }
    }

    private boolean checkUp(int row, int column) {
        try {
            int top = Integer.parseInt(((String[])this.blueprint.get(row + 1))[column]);
            return ((Room)this.rooms.get(top)).getVisited();
        } catch (Exception var4) {
            return false;
        }
    }

    private boolean checkDown(int row, int column) {
        try {
            int bot = Integer.parseInt(((String[])this.blueprint.get(row - 1))[column]);
            return ((Room)this.rooms.get(bot)).getVisited();
        } catch (Exception var4) {
            return false;
        }
    }

    private boolean checkLeft(int row, int column) {
        try {
            int left = Integer.parseInt(((String[])this.blueprint.get(row))[column - 1]);
            return ((Room)this.rooms.get(left)).getVisited();
        } catch (Exception var4) {
            return false;
        }
    }

    private boolean checkRight(int row, int column) {
        try {
            int right = Integer.parseInt(((String[])this.blueprint.get(row - 1))[column + 1]);
            return ((Room)this.rooms.get(right)).getVisited();
        } catch (Exception var4) {
            return false;
        }
    }

    public Pane showMap() {
        return this.map;
    }
}
