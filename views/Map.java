package views;

import AdventureModel.AdventureGame;
import AdventureModel.Passage;
import AdventureModel.Room;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Map {

    AdventureGame game; // This is the AdventureGame
    HashMap<Integer, Room> rooms; // Used to store and access room information
    GridPane map; // This is where the map will be displayed

    ArrayList<String[]> blueprint = new ArrayList<>(); // The blueprint from which the map will be designed using


    public Map(AdventureGameView game) throws IOException {
        this.game = game.model;
        rooms = game.model.getRooms();
        map = new GridPane();
        Image mapBackground = new Image(this.game.getDirectoryName() + "/mapBackground.png");

        map.setBackground(new Background(new BackgroundImage(mapBackground,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        createBlueprint();
        this.game.player.getCurrentRoom().visit();
    }


    /**
     * generateMap
     * __________________________
     *
     * Generates the map based on the blueprint and if a room has been visited or not
     */
    public void generateMap(){
        map.getChildren().clear();
        for (int i = 0; i < blueprint.size(); i++) {
            for (int j = 0; j < blueprint.get(i).length; j++) {
                String room = blueprint.get(i)[j];
                if(room.equals("-")){
                    Rectangle blackBox = new Rectangle(50, 50, Color.BLACK);
                    GridPane.setRowIndex(blackBox, i);
                    GridPane.setColumnIndex(blackBox, j);
                    blackBox.setVisible(false);
                    map.getChildren().add(blackBox);
                }else if (room.equals("|")) {
                    if(checkSurroundings(i, j)){
                        String passageImage = game.getDirectoryName() + "/room-images/passage.png";
                        Image passageImageFile = new Image(passageImage);
                        ImageView passage = new ImageView(passageImageFile);
                        passage.setFitHeight(50);
                        passage.setFitWidth(50);
                        GridPane.setRowIndex(passage, i);
                        GridPane.setColumnIndex(passage, j);
                        map.getChildren().add(passage);
                    }else{
                        Rectangle blackBox = new Rectangle(50, 50, Color.BLACK);
                        GridPane.setRowIndex(blackBox, i);
                        GridPane.setColumnIndex(blackBox, j);
                        blackBox.setVisible(false);
                        map.getChildren().add(blackBox);
                    }
                } else {
                    if(rooms.get(Integer.parseInt(room)).getVisited()) {
                        if(game.player.getCurrentRoom().getRoomNumber() == Integer.parseInt(room)){
                            ImageView roomImage = getImage(room + "a");
                            roomImage.setFitHeight(50);
                            roomImage.setFitWidth(50);
                            GridPane.setRowIndex(roomImage, i);
                            GridPane.setColumnIndex(roomImage, j);
                            map.getChildren().add(roomImage);
                        }else{
                            ImageView roomImage = getImage(room);
                            roomImage.setFitHeight(50);
                            roomImage.setFitWidth(50);
                            GridPane.setRowIndex(roomImage, i);
                            GridPane.setColumnIndex(roomImage, j);
                            map.getChildren().add(roomImage);
                        }
                    }else{
                        Rectangle blackBox = new Rectangle(50, 50, Color.BLACK);
                        GridPane.setRowIndex(blackBox, i);
                        GridPane.setColumnIndex(blackBox, j);
                        blackBox.setVisible(false);
                        map.getChildren().add(blackBox);
                    }
                }
            }
        }
    }


    /**
     * updateScene
     * __________________________
     *
     * Creates the blueprint from mapLayout.txt
     */
    public void createBlueprint() throws IOException {
        String roomFileName = game.getDirectoryName() + "/mapLayout.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));
        while(buff.ready()){
            blueprint.add(buff.readLine().split(" "));
        }

    }

    /**
     * getImage
     * __________________________
     *
     * Get the image for a room using roomNumber
     *
     * @param roomNumber the room from which to get the image for
     */
    private ImageView getImage(String roomNumber){
        String roomImage = game.getDirectoryName() + "/room-images/" + roomNumber + ".png";
        Image roomImageFile = new Image(roomImage);
        return new ImageView(roomImageFile);
    }

    /**
     * checkSurroundings
     * __________________________
     *
     * Checks passage surroundings to see if any rooms are visited
     *
     * @param row the row for which the passage is located
     * @param column the column for which the passage is located
     */
    private boolean checkSurroundings(int row, int column){
        if(checkUp(row, column)){
            return true;
        } else if (checkDown(row, column)) {
            return true;
        } else if (checkLeft(row, column)) {
            return true;
        } else if (checkRight(row, column)) {
            return true;
        }
        return false;
    }

    private boolean checkUp(int row, int column){
        try{
            int top = Integer.parseInt(blueprint.get(row + 1)[column]);
            return rooms.get(top).getVisited();
        }catch(Exception e){
            return false;
        }
    }

    private boolean checkDown(int row, int column){
        try{
            int bot = Integer.parseInt(blueprint.get(row - 1)[column]);
            return rooms.get(bot).getVisited();
        }catch(Exception e){
            return false;
        }
    }

    private boolean checkLeft(int row, int column){
        try{
            int left = Integer.parseInt(blueprint.get(row)[column - 1]);
            return rooms.get(left).getVisited();
        }catch(Exception e){
            return false;
        }
    }

    private boolean checkRight(int row, int column){
        try{
            int right = Integer.parseInt(blueprint.get(row)[column + 1]);
            return rooms.get(right).getVisited();
        }catch(Exception h){
            return false;
        }
    }

    public Pane showMap(){

        return map;
    }

}
