package views;

import AdventureModel.AdventureGame;
import AdventureModel.Passage;
import AdventureModel.Room;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    HashMap<Integer, Room> rooms;
    GridPane map;
    ArrayList<Integer> isGenerated;

    ArrayList<String[]> blueprint = new ArrayList<>();


    public Map(AdventureGameView game) throws IOException {
        this.game = game.model;
        rooms = game.model.getRooms();
        map = new GridPane();
        isGenerated = new ArrayList<>(Collections.nCopies(10, 0));
        createBlueprint();
        this.game.player.getCurrentRoom().visit();
    }

    public void generateMap(){
        for (int i = 0; i < blueprint.size(); i++) {
            for (int j = 0; j < blueprint.get(i).length; j++) {
                String room = blueprint.get(i)[j];
                if(room.equals("-")){
                    Rectangle blackBox = new Rectangle(100, 100, Color.BLACK);
                    GridPane.setRowIndex(blackBox, i);
                    GridPane.setColumnIndex(blackBox, j);
                    map.getChildren().add(blackBox);
                }else if (room.equals("|")) {
                    if(checkSurroundings(i, j)){
                        String passageImage = game.getDirectoryName() + "/room-images/passage.png";
                        Image passageImageFile = new Image(passageImage);
                        ImageView passage = new ImageView(passageImageFile);
                        passage.setFitHeight(100);
                        passage.setFitWidth(100);
                        GridPane.setRowIndex(passage, i);
                        GridPane.setColumnIndex(passage, j);
                        map.getChildren().add(passage);
                    }else{
                        Rectangle blackBox = new Rectangle(100, 100, Color.BLACK);
                        GridPane.setRowIndex(blackBox, i);
                        GridPane.setColumnIndex(blackBox, j);
                        map.getChildren().add(blackBox);
                    }
                } else {
                    if(rooms.get(Integer.parseInt(room)).getVisited()) {
                        ImageView roomImage = getImage(Integer.parseInt(room));
                        roomImage.setFitHeight(100);
                        roomImage.setFitWidth(100);
                        GridPane.setRowIndex(roomImage, i);
                        GridPane.setColumnIndex(roomImage, j);
                        map.getChildren().add(roomImage);
                    }else{
                        Rectangle blackBox = new Rectangle(100, 100, Color.BLACK);
                        GridPane.setRowIndex(blackBox, i);
                        GridPane.setColumnIndex(blackBox, j);
                        map.getChildren().add(blackBox);
                    }
                }
            }
        }
    }


    public void createBlueprint() throws IOException {
        String roomFileName = game.getDirectoryName() + "/mapLayout.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));
        while(buff.ready()){
            blueprint.add(buff.readLine().split(" "));
        }

    }

    private ImageView getImage(int roomNumber){
        String roomImage = game.getDirectoryName() + "/room-images/" + roomNumber + ".png";
        Image roomImageFile = new Image(roomImage);
        return new ImageView(roomImageFile);
    }

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
            int right = Integer.parseInt(blueprint.get(row - 1)[column + 1]);
            return rooms.get(right).getVisited();
        }catch(Exception h){
            return false;
        }
    }

    public Pane showMap(){

        return map;
    }

}
