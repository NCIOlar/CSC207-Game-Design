package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;

import java.io.File;
import java.util.ArrayList;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * ZOOM LINK: <>
 * PASSWORD: <PASSWORD HERE>
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, settingsButton, loadButton_home, helpButton_home,
            easyButton_home, mediumButton_home, hardButton_home, shopButton, mapButton, homepageButton; //buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        intiUI();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("hewenju2's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        String roomImage = "/Games/Homepage.png";
        Image roomImageFile = new Image(roomImage);
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column3);
        gridPane.getRowConstraints().addAll( row1 , row2 , row3);

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 50, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 50, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Help");
        helpButton.setId("Help");
        customizeButton(helpButton, 50, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        mapButton = new Button("Map");
        mapButton.setId("Map");
        customizeButton(mapButton, 50, 50);
        makeButtonAccessible(mapButton, "Map Button", "This button pops up a map of the game.", "This button pops up a map of the game. Click it to navigate where you are!");
        addMapEvent();

        shopButton = new Button("Shop");
        shopButton.setId("Shop");
        customizeButton(shopButton, 50, 50);
        makeButtonAccessible(shopButton, "Shop Button", "This button pops up a shop containing goods.", "This button pops up a shop containing goods. Click it to buy equipments that you need when playing!");
        addShopEvent();

        homepageButton = new Button("Home");
        homepageButton.setId("Home");
        customizeButton(homepageButton, 50, 50);
        makeButtonAccessible(homepageButton, "Home Button", "This button exits the current game and directs to the homepage.", "This button exits the current game and directs to the homepage. Click it if you want to exit the game!");
        addHomeEvent();

        loadButton_home = new Button("Load Game");
        loadButton_home.setId("Load Game");
        customizeButton(loadButton_home, 150, 50);
        makeButtonAccessible(loadButton_home, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton_home = new Button("Help");
        helpButton_home.setId("Help");
        customizeButton(helpButton_home, 150, 50);
        makeButtonAccessible(helpButton_home, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        easyButton_home = new Button("Easy");
        easyButton_home.setId("Easy");
        customizeButton(easyButton_home, 150, 50);
        makeButtonAccessible(easyButton_home, "Easy Button", "This button initializes game with easy mode.", "This button initializes game with easy mode. Click it to be able to play.");
        addEasyEvent();

        mediumButton_home = new Button("Medium");
        mediumButton_home.setId("Medium");
        customizeButton(mediumButton_home, 150, 50);
        makeButtonAccessible(mediumButton_home, "Medium Button", "This button initializes game with medium mode.", "This button initializes game with medium mode. Click it to be able to play.");
        addMediumEvent();

        hardButton_home = new Button("Hard");
        hardButton_home.setId("Hard");
        customizeButton(hardButton_home, 150, 50);
        makeButtonAccessible(hardButton_home, "Hard Button", "This button initializes game with hard mode.", "This button initializes game with hard mode. Click it to be able to play.");
        addHardEvent();

        Image settings_icon = new Image("Games/Settings.png");
        ImageView settings_iv =new ImageView(settings_icon);
        settings_iv.setFitHeight(40);
        settings_iv.setFitWidth(40);
        settingsButton = new Button("", settings_iv);
        settingsButton.setId("Settings");
        customizeButton(settingsButton, 50, 50);
        makeButtonAccessible(settingsButton, "Settings Button", "This button opens the settings menu.", "This button opens the settings menu, it pops up settings where you can change displays.");

        VBox Buttons = new VBox();
        Buttons.getChildren().addAll(easyButton_home, mediumButton_home, hardButton_home, loadButton_home, helpButton_home);
        Buttons.setSpacing(30);
        Buttons.setAlignment(Pos.BOTTOM_CENTER);

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( Buttons, 1, 1 );  // Add buttons
        gridPane.add(settingsButton, 2, 0);

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

        Image image = new Image("Games/Cursor.png");  //pass in the image path
        gridPane.getScene().setCursor(new ImageCursor(image));
    }

    public void intiGame() {

        //Inventory + Room items
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        HBox topButtons1 = new HBox();
        topButtons1.getChildren().addAll(homepageButton, mapButton, shopButton);
        topButtons1.setSpacing(10);
        topButtons1.setAlignment(Pos.CENTER);

        HBox topButtons2 = new HBox();
        topButtons2.getChildren().addAll(saveButton, helpButton, loadButton, settingsButton);
        topButtons2.setSpacing(10);
        topButtons2.setAlignment(Pos.CENTER);


        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( topButtons1, 0, 0);  // Add buttons
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons2, 2, 0);  // Add buttons

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();



    }
    public void settingMenu(){
        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();



        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, loadButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);
        ColorAdjust colorAdjust = new ColorAdjust();

    }

    public void updatedSetting(ArrayList l){

    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #184ac9; -fx-text-fill: white;");
    }

    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        String commands = model.player.getCurrentRoom().getCommands();
        commands = "You possible moves are:\n" + commands;
        roomDescLabel.setText(commands);
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        Image roomImageFile = getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        stage.sizeToScene();
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));


        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private Image getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(1000);
        roomImageView.setFitHeight(800);


        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
        return roomImageFile;
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets 
     * folders of the given adventure game.
     */
    public void updateItems() {

        objectsInRoom.getChildren().removeIf(node -> true);
        objectsInInventory.getChildren().removeIf(node -> true);
        String separator = File.separator;
        ArrayList<String> inventory = this.model.player.getInventory();
        for (String objectName : inventory) {
            String objectImage = this.model.getDirectoryName() + separator + "objectImages" + separator + objectName + ".jpg";
            Image objectImageFile = new Image(objectImage);
            ImageView objectImageView = new ImageView(objectImageFile);
            objectImageView.setPreserveRatio(true);
            objectImageView.setFitWidth(100);
            String objectDesc = "";
            for (AdventureObject object : this.model.player.inventory) {
                if (object.getName().equals(objectName)) {
                    objectDesc = object.getDescription();
                }
            }
            objectImageView.setAccessibleText(objectDesc);
            Button objectButton = new Button(objectName);
            objectButton.setContentDisplay(ContentDisplay.TOP);
            objectButton.setGraphic(objectImageView);
            objectButton.setId(objectName);
            makeButtonAccessible(objectButton, objectName, "This button simulates " + objectDesc, "This button simulates " + objectDesc + ". Click it in order to put it to / drop it from your inventory");
            EventHandler<MouseEvent> eventhandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (objectsInRoom.getChildren().contains(objectButton)) {
                        objectsInRoom.getChildren().removeIf(node -> node.equals(objectButton));
                        objectsInInventory.getChildren().add(objectButton);
                        model.player.takeObject(objectName);
                    } else if (objectsInInventory.getChildren().contains(objectButton)) {
                        objectsInInventory.getChildren().removeIf(node -> node.equals(objectButton));
                        objectsInRoom.getChildren().add(objectButton);
                        model.player.dropObject(objectName);
                    }
                }
            };

            objectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, eventhandler);
            objectsInInventory.getChildren().add(objectButton);
        }

        ArrayList<AdventureObject> objectInRoom = this.model.player.getCurrentRoom().objectsInRoom;
        for (AdventureObject object : objectInRoom) {
            String objectImage = this.model.getDirectoryName() + separator + "objectImages" + separator + object.getName() + ".jpg";
            Image objectImageFile = new Image(objectImage);
            ImageView objectImageView = new ImageView(objectImageFile);
            objectImageView.setPreserveRatio(true);
            objectImageView.setFitWidth(100);
            objectImageView.setAccessibleText(object.getDescription());
            Button objectButton = new Button(object.getName());
            objectButton.setContentDisplay(ContentDisplay.TOP);
            objectButton.setGraphic(objectImageView);
            objectButton.setId(object.getName());
            makeButtonAccessible(objectButton, object.getName(), "This button simulates " + object.getDescription(), "This button simulates " + object.getDescription() + ". Click it in order to put it to / drop it from your inventory");

            EventHandler<MouseEvent> eventhandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (objectsInRoom.getChildren().contains(objectButton)) {
                        objectsInRoom.getChildren().removeIf(node -> node.equals(objectButton));
                        objectsInInventory.getChildren().add(objectButton);
                        model.player.takeObject(object.getName());
                    } else if (objectsInInventory.getChildren().contains(objectButton)) {
                        objectsInInventory.getChildren().removeIf(node -> node.equals(objectButton));
                        objectsInRoom.getChildren().add(objectButton);
                        model.player.dropObject(object.getName());
                    }
                }
            };

            objectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, eventhandler);
            objectsInRoom.getChildren().add(objectButton);
        }

        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);


    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {

        gridPane.getChildren().removeIf(node -> gridPane.getColumnIndex(node) == 1 && gridPane.getRowIndex(node) == 1);

        if (helpToggle) {
            updateScene("");
            helpToggle = false;
        } else {
            Label helpLabel = new Label(model.getInstructions());
            helpLabel.setStyle("-fx-text-fill: white;");
            helpLabel.setFont(new Font("Arial", 16));
            helpLabel.setAlignment(Pos.CENTER);
            helpLabel.setPrefWidth(500);
            helpLabel.setPrefHeight(500);
            helpLabel.setTextOverrun(OverrunStyle.CLIP);
            helpLabel.setWrapText(true);
            VBox helpBox = new VBox(helpLabel);
            helpBox.setPadding(new Insets(10));
            helpBox.setAlignment(Pos.TOP_CENTER);
            helpBox.setStyle("-fx-background-color: #000000;");
            String helpString = model.getInstructions();
            helpLabel.setText(helpString);
            gridPane.add(helpBox, 1, 1);
            helpToggle = true;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to the
     * easy button.
     */
    public void addEasyEvent() {
        easyButton_home.setOnAction(e -> {
            gridPane.requestFocus();
            this.model = new AdventureGame("EasyGame");
            gridPane.getChildren().removeIf(node -> true);
            intiGame();
        });
    }

    public void addMediumEvent() {
        mediumButton_home.setOnAction(e -> {
            gridPane.requestFocus();
            this.model = new AdventureGame("MediumGame");
            gridPane.getChildren().removeIf(node -> true);
            intiGame();
        });
    }

    public void addHardEvent() {
        hardButton_home.setOnAction(e -> {
            gridPane.requestFocus();
            this.model = new AdventureGame("HardGame");
            gridPane.getChildren().removeIf(node -> true);
            intiGame();
        });
    }

    public void addShopEvent() {

    }

    public void addHomeEvent() {

    }

    public void addMapEvent() {

    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
}
