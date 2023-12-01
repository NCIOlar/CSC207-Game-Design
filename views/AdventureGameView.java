package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
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
    VBox Buttons;

    //Setting buttons
    Button increaseBrightnessButton, decreaseBrightnessButton, menuButton;
    Setting setting = new Setting(new GridPane());

    Button saveButton, loadButton, helpButton, settingsButton, loadButton_home, introductionButton_home,
            easyButton_home, mediumButton_home, hardButton_home, shopButton, mapButton, homepageButton; //buttons
    Boolean mapToggle = false;
    Map map;

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    String helpText; //help text

    TextField inputTextField; //for user input

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(Stage stage) throws IOException {
        this.stage = stage;
        intiUI();
    }

    /**
     * Initialize the UI (load the first homepage)
     */
    public void intiUI() throws IOException {

        // setting up the stage
        this.stage.setTitle("Last Hope");

        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        String roomImage = "/Games/Homepage.png";
        Image roomImageFile = new Image(roomImage);
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(200);
        ColumnConstraints column2 = new ColumnConstraints(600);
        ColumnConstraints column3 = new ColumnConstraints(200);
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

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        String text = "";
        String fileName = "Games/help.txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line+"\n";
            line = buff.readLine();
        }
        this.helpText = text;

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 65, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 65, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Help");
        helpButton.setId("Help");
        customizeButton(helpButton, 100, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addHelpEvent();

        mapButton = new Button("Map");
        mapButton.setId("Map");
        customizeButton(mapButton, 65, 50);
        makeButtonAccessible(mapButton, "Map Button", "This button pops up a map of the game.", "This button pops up a map of the game. Click it to navigate where you are!");
        addMapEvent();

        shopButton = new Button("Shop");
        shopButton.setId("Shop");
        customizeButton(shopButton, 65, 50);
        makeButtonAccessible(shopButton, "Shop Button", "This button pops up a shop containing goods.", "This button pops up a shop containing goods. Click it to buy equipments that you need when playing!");
        addShopEvent();

        homepageButton = new Button("Home");
        homepageButton.setId("Home");
        customizeButton(homepageButton, 100, 50);
        makeButtonAccessible(homepageButton, "Home Button", "This button exits the current game and directs to the homepage.", "This button exits the current game and directs to the homepage. Click it if you want to exit the game!");
        homepageButton.setAlignment(Pos.CENTER);
        addHomeEvent();

        loadButton_home = new Button("Load Game");
        loadButton_home.setId("Load Game");
        customizeButton(loadButton_home, 150, 50);
        makeButtonAccessible(loadButton_home, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoad_HomeEvent();

        introductionButton_home = new Button("Introduction");
        introductionButton_home.setId("Introduction");
        customizeButton(introductionButton_home, 150, 50);
        makeButtonAccessible(introductionButton_home, "Introduction Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addIntroduction_HomeEvent();

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
        Buttons.getChildren().addAll(introductionButton_home, easyButton_home, mediumButton_home, hardButton_home, loadButton_home);
        Buttons.setSpacing(30);
        Buttons.setAlignment(Pos.BOTTOM_CENTER);

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

    /**
     * Return to the homepage (after pressing home button when playing game)
     */
    public void returnHome() {

        String roomImage = "/Games/Homepage.png";
        Image roomImageFile = new Image(roomImage);
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));

        VBox Buttons = new VBox();
        Buttons.getChildren().addAll(introductionButton_home, easyButton_home, mediumButton_home, hardButton_home, loadButton_home);
        Buttons.setSpacing(30);
        Buttons.setAlignment(Pos.BOTTOM_CENTER);

        //add all the widgets to the GridPane
        gridPane.add( Buttons, 1, 1 );  // Add buttons
        gridPane.add(settingsButton, 2, 0);

        this.stage.getScene().setOnKeyPressed(null);

    }

    public void intiGame() {

        HBox topButtons2 = new HBox();
        topButtons2.getChildren().addAll(mapButton, shopButton, homepageButton, settingsButton, helpButton, saveButton, loadButton);
        topButtons2.setSpacing(10);
        topButtons2.setPrefWidth(400);
        topButtons2.setAlignment(Pos.CENTER);

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: BLACK;");
        commandLabel.setFont(new Font("Arial", 16));

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background: rgba(255,255,255,0.5); -fx-background-color: rgba(255,255,255,0.5)");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 1, 2, 3, 1 );

        Label objLabel =  new Label("Objects in Room");
        objLabel.setStyle("-fx-text-fill: WHITE;");
        objLabel.setFont(new Font("Arial", 16));
        objLabel.setAlignment(Pos.BOTTOM_RIGHT);

        //add all the widgets to the GridPane
        gridPane.add( topButtons2, 1, 0);  // Add buttons
        gridPane.add( objLabel, 0, 0);  // Add buttons

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        EventHandler<KeyEvent> eventhandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.W) {
                    submitEvent("W");
                } else if (keyEvent.getCode() == KeyCode.S) {
                    submitEvent("S");
                } else if (keyEvent.getCode() == KeyCode.A) {
                    submitEvent("A");
                } else if (keyEvent.getCode() == KeyCode.D) {
                    submitEvent("D");
                } else if (keyEvent.getCode() == KeyCode.E) {
                    submitEvent("E");
                }
            }
        };
        this.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, eventhandler);

    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String command = inputTextField.getText().strip();
                    submitEvent(command);
                } else if (keyEvent.getCode() == KeyCode.TAB) {
                    saveButton.requestFocus();
                }
            }
        };

        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);

    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        } else if (text.equalsIgnoreCase("BAG") || text.equalsIgnoreCase("E")) {
            //IMPLEMENT BAG FUNCTION HERE!
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("YOU LOST") && !output.equals("YOU WIN"))) {
            updateScene("");
            updateItems();
        } else if (output.equals("YOU WIN")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                returnHome();
                this.model = null;
            });
        } else if (output.equals("YOU LOST")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                returnHome();
                this.model = null;
            });
            pause.play();
        }
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

    public void showSettingMenu(){
        //update setting on current girdpane

        gridPane.getChildren().clear(); // reset gridpane
        // Buttons
        menuButton = new Button("Menu");
        menuButton.setId("menu");
        customizeButton(menuButton, 200, 50);
        makeButtonAccessible(menuButton, "menu", "menu", "menu");
        addMenu();

        increaseBrightnessButton = new Button("Increase Brightness");
        increaseBrightnessButton.setId("increaseBrightness");
        customizeButton(increaseBrightnessButton, 200, 50);
        makeButtonAccessible(increaseBrightnessButton, "increaseBrightness", "This button increase birghtness.", "This button increase birghtness");
        addIncreaseBrightnessEvent();

        decreaseBrightnessButton = new Button("Decrease Brightness");
        decreaseBrightnessButton.setId("decreaseBrightness");
        customizeButton(decreaseBrightnessButton, 200, 50);
        makeButtonAccessible(decreaseBrightnessButton, "decreaseBrightness", "decreaseBrightness", "decreaseBrightness");
        addDecreaseBrightnessEvent();

        VBox settingButtons = new VBox();
        settingButtons.getChildren().addAll(menuButton, increaseBrightnessButton, decreaseBrightnessButton);
        settingButtons.setSpacing(30);
        settingButtons.setAlignment(Pos.CENTER);

        gridPane.add(settingButtons, 1 ,1);

    }

    public void addSettingEvent(){
        settingsButton.setOnAction(e -> {
            showSettingMenu();
        });

    }

    public void addMenu(){
        menuButton.setOnAction(e -> {
            gridPane.getChildren().clear();
            gridPane.add( Buttons, 1, 1 );  // Add buttons
            gridPane.add(settingsButton, 2, 0);
            addSettingEvent();
        });

    }
    public void addIncreaseBrightnessEvent(){
        increaseBrightnessButton.setOnAction(e -> {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(setting.increaseBrightness());
            gridPane.setEffect(colorAdjust);
        });

    }

    public void addDecreaseBrightnessEvent(){
        decreaseBrightnessButton.setOnAction(e -> {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(setting.decreaseBrightness());
            gridPane.setEffect(colorAdjust);

        });

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
        roomDescLabel.setPrefWidth(600);
        roomDescLabel.setPrefHeight(200);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.BOTTOM_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        stage.sizeToScene();
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));

        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 2);
        Label playerInfo = new Label("❤️:" + model.player.health + "\n🔪: " + model.player.damage + "\n🛡️: " + model.player.defense);
        playerInfo.setStyle("-fx-text-fill: RED; -fx-background: rgba(255,255,255,0.5); -fx-background-color: rgba(255,255,255,0.5)");
        playerInfo.setFont(new Font("Arial", 32));
        playerInfo.setAlignment(Pos.CENTER);
        gridPane.add(playerInfo, 0, 2);

        if (this.model.player.getCurrentRoom().troll != null) {

        }
        //finally, articulate the description
        // if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
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
            String objectImage = this.model.getDirectoryName() + separator + "objectImages" + separator + objectName + ".png";
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
            String objectImage = this.model.getDirectoryName() + separator + "objectImages" + separator + object.getName() + ".png";
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

        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 1);
        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setMaxHeight(600);
        scO.setMaxWidth(150);
        scO.setStyle("-fx-background: rgba(255,255,255,0.5); -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

    }

    public void showMap(){
        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1);
        if (mapToggle) {
            updateScene("");
            mapToggle = false;
        } else {
            gridPane.add(map.showMap(), 1, 1);
            mapToggle = true;
        }


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
    public void showInstructions() throws IOException {

        String text = "";
        String fileName = "Games/help.txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line+"\n";
            line = buff.readLine();
        }
        Label helpLabel = new Label(text);
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
        helpLabel.setText(helpText);
        gridPane.add(helpBox, 1, 1);
        HBox lowButtons = new HBox();
        lowButtons.getChildren().addAll(homepageButton);
        lowButtons.setAlignment(Pos.CENTER);
        gridPane.add(lowButtons, 1, 2);
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addHelpEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            try {
                showInstructions();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * This method handles the event related to the
     * help button in homepage.
     */
    public void addIntroduction_HomeEvent() {
        introductionButton_home.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            try {
                showInstructions();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
     * load button in homepage.
     */
    public void addLoad_HomeEvent() {
        loadButton_home.setOnAction(e -> {
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
            stopArticulation();
            intiGame();
        });
    }

    public void addMediumEvent() {
        mediumButton_home.setOnAction(e -> {
            gridPane.requestFocus();
            this.model = new AdventureGame("MediumGame");
            gridPane.getChildren().removeIf(node -> true);
            stopArticulation();
            intiGame();
        });
    }

    public void addHardEvent() {
        hardButton_home.setOnAction(e -> {
            gridPane.requestFocus();
            this.model = new AdventureGame("HardGame");
            gridPane.getChildren().removeIf(node -> true);
            stopArticulation();
            intiGame();
        });
    }

    public void addShopEvent() {

    }

    public void addHomeEvent() {
        homepageButton.setOnAction(e -> {
            gridPane.requestFocus();
            gridPane.getChildren().removeIf(node -> true);
            stopArticulation();
            returnHome();
        });
    }

    public void addMapEvent() {
        mapButton.setOnAction(e -> {
            showMap();
            
        });
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
