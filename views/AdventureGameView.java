package views;

import AdventureModel.*;
import views.Map;
import Trolls.Fighting_Troll;
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
    String adventureName;

    AdventureLoader loader;
    //Setting buttons
    //Setting buttons
    Button increaseBrightnessButton, decreaseBrightnessButton, menuButton, gobackButton,increaseContrastButton,
            decreaseContrastButton, settingInGameButton, increaseVolumeButton, decreaseVolumeButton;
    Setting setting = new Setting(new GridPane());
    VBox settingButtons = new VBox();


    Button saveButton, loadButton, helpButton, settingsButton, loadButton_home, introductionButton_home,
            easyButton_home, mediumButton_home, hardButton_home, shopButton, mapButton, homepageButton, backButton,
            buyVestButton, buyStimButton, buyMedkitButton, buyMaskButton, buySkipButton; //buttons
    Boolean mapToggle = false;
    Map map;
    Shop shop;
    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image

    private MediaPlayer mediaPlayer; //to play music
    private boolean mediaPlaying; //to know if the music is playing

    private MediaPlayer audioPlayer; //to play audio
    private boolean audioPlaying; //to know if the audio is playing

    private String preMusic = "Games/Solar_Sailer.mp3";
    private BorderPane shopPane = new BorderPane();

    EventHandler<KeyEvent> eventhandler; //EventHandler for WASD
    String helpText; //help text

    TextField inputTextField; //for user input

    Boolean helpToggle = false; //is help on display?

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

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
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
        addHoverEvent(saveButton);

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 65, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();
        addHoverEvent(loadButton);

        helpButton = new Button("Help");
        helpButton.setId("Help");
        customizeButton(helpButton, 100, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addHelpEvent();
        addHoverEvent(helpButton);

        mapButton = new Button("Map");
        mapButton.setId("Map");
        customizeButton(mapButton, 65, 50);
        makeButtonAccessible(mapButton, "Map Button", "This button pops up a map of the game.", "This button pops up a map of the game. Click it to navigate where you are!");
        addMapEvent();
        addHoverEvent(mapButton);

        shopButton = new Button("Shop");
        shopButton.setId("Shop");
        customizeButton(shopButton, 65, 50);
        makeButtonAccessible(shopButton, "Shop Button", "This button pops up a shop containing goods.", "This button pops up a shop containing goods. Click it to buy equipments that you need when playing!");
        addShopEvent();
        addHoverEvent(shopButton);

        backButton = new Button("Back");
        backButton.setId("back");
        customizeButton(backButton, 65, 50);
        makeButtonAccessible(backButton, "Back Button", "returns to game from shop", "returns to game from shop");
        addReturnEvent();

        homepageButton = new Button("Home");
        homepageButton.setId("Home");
        customizeButton(homepageButton, 100, 50);
        makeButtonAccessible(homepageButton, "Home Button", "This button exits the current game and directs to the homepage.", "This button exits the current game and directs to the homepage. Click it if you want to exit the game!");
        homepageButton.setAlignment(Pos.CENTER);
        addHomeEvent();
        addHoverEvent(homepageButton);

        loadButton_home = new Button("Load Game");
        loadButton_home.setId("Load Game");
        customizeButton(loadButton_home, 150, 50);
        makeButtonAccessible(loadButton_home, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoad_HomeEvent();
        addHoverEvent(loadButton_home);

        introductionButton_home = new Button("Introduction");
        introductionButton_home.setId("Introduction");
        customizeButton(introductionButton_home, 150, 50);
        makeButtonAccessible(introductionButton_home, "Introduction Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addIntroduction_HomeEvent();
        addHoverEvent(introductionButton_home);

        easyButton_home = new Button("Easy");
        easyButton_home.setId("Easy");
        customizeButton(easyButton_home, 150, 50);
        makeButtonAccessible(easyButton_home, "Easy Button", "This button initializes game with easy mode.", "This button initializes game with easy mode. Click it to be able to play.");
        addEasyEvent();
        addHoverEvent(easyButton_home);

        mediumButton_home = new Button("Medium");
        mediumButton_home.setId("Medium");
        customizeButton(mediumButton_home, 150, 50);
        makeButtonAccessible(mediumButton_home, "Medium Button", "This button initializes game with medium mode.", "This button initializes game with medium mode. Click it to be able to play.");
        addMediumEvent();
        addHoverEvent(mediumButton_home);

        hardButton_home = new Button("Hard");
        hardButton_home.setId("Hard");
        customizeButton(hardButton_home, 150, 50);
        makeButtonAccessible(hardButton_home, "Hard Button", "This button initializes game with hard mode.", "This button initializes game with hard mode. Click it to be able to play.");
        addHardEvent();
        addHoverEvent(hardButton_home);

        roomDescLabel.setPrefWidth(600);
        roomDescLabel.setPrefHeight(150);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        roomDescLabel.setStyle("-fx-text-fill: BLACK");

        roomDescLabel.setPrefWidth(600);
        roomDescLabel.setPrefHeight(150);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        roomDescLabel.setStyle("-fx-text-fill: BLACK");

        Image settings_icon = new Image("Games/Settings.png");
        ImageView settings_iv =new ImageView(settings_icon);
        settings_iv.setFitHeight(40);
        settings_iv.setFitWidth(40);
        settingsButton = new Button("", settings_iv);
        settingsButton.setId("Settings");
        customizeButton(settingsButton, 50, 50);
        makeButtonAccessible(settingsButton, "Settings Button", "This button opens the settings menu.", "This button opens the settings menu, it pops up settings where you can change displays.");
        addSettingEvent();
        addHoverEvent(settingsButton);
        settingInGameButton = new Button("", settings_iv);
        settingInGameButton.setId("SettingInGame");
        customizeButton(settingInGameButton, 50, 50);
        makeButtonAccessible(settingInGameButton, "Settings Button", "This button opens the settings menu.", "This button opens the settings menu, it pops up settings where you can change displays.");
        addSettingInGameEvent();
        addHoverEvent(settingInGameButton);


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


        String path2 = "Games/Solar_Sailer.mp3";
        Media media = new Media(Paths.get(path2).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        Setting.setVolume(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlaying = true;

    }

    /**
     * Return to the homepage (after pressing home button when not in the homepage)
     */
    public void returnHome() {

        if (this.model != null) {
            this.stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
        }

        gridPane.getChildren().clear();
        String roomImage = "/Games/Homepage.png";
        Image roomImageFile = new Image(roomImage);
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));

        VBox Buttons = new VBox();
        Buttons.getChildren().addAll(introductionButton_home, easyButton_home, mediumButton_home, hardButton_home, loadButton_home);
        Buttons.setSpacing(30);
        Buttons.setAlignment(Pos.BOTTOM_CENTER);

        //reset setting buttons
        Image settings_icon = new Image("Games/Settings.png");
        ImageView settings_iv =new ImageView(settings_icon);
        settings_iv.setFitHeight(40);
        settings_iv.setFitWidth(40);
        settingsButton = new Button("", settings_iv);
        settingsButton.setId("Settings");
        customizeButton(settingsButton, 50, 50);
        makeButtonAccessible(settingsButton, "Settings Button", "This button opens the settings menu.", "This button opens the settings menu, it pops up settings where you can change displays.");
        addSettingEvent();

        //add all the widgets to the GridPane
        gridPane.add( Buttons, 1, 1 );  // Add buttons
        gridPane.add(settingsButton, 2, 0);

        String path2 = "Games/Solar_Sailer.mp3";
        if (!preMusic.equals(path2)) {
            preMusic = path2;
            mediaPlayer.dispose();
            Media media = new Media(Paths.get(path2).toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            Setting.setVolume(mediaPlayer);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlaying = true;
        }

    }

    /**
     * Initialises the Game when player chooses the difficulty
     */
    public void intiGame() throws IOException {

        gridPane.getChildren().clear();
        HBox topButtons2 = new HBox();
        topButtons2.getChildren().addAll(mapButton, shopButton, homepageButton, settingInGameButton, helpButton, saveButton, loadButton);
        topButtons2.setSpacing(10);
        topButtons2.setPrefWidth(400);
        topButtons2.setAlignment(Pos.CENTER);

        Label commandLabel = new Label("Enter Command");
        commandLabel.setStyle("-fx-text-fill: BLACK;");
        commandLabel.setFont(new Font("Arial", 16));

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background: rgba(255,255,255,0.3); -fx-background-color: rgba(255,255,255,0.3)");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 2, 2, 3, 1);

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setStyle("-fx-text-fill: WHITE;");
        objLabel.setFont(new Font("Arial", 16));
        objLabel.setAlignment(Pos.BOTTOM_RIGHT);

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 16));
        objLabel.setAlignment(Pos.BOTTOM_RIGHT);

        //add all the widgets to the GridPane
        gridPane.add( topButtons2, 1, 0);  // Add buttons
        gridPane.add( objLabel, 0, 0);  // Add buttons
        gridPane.add( invLabel, 2, 0 );  // Add label

        // This piece of code initializes map on load
        if(this.model != null){
            map = new Map(this);
        }
        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        eventhandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.W) {
                    submitEvent("W");
                } else if (keyEvent.getCode() == KeyCode.S) {
                    submitEvent("S");
                } else if (keyEvent.getCode() == KeyCode.A) {
                    submitEvent("A");
                } else if (keyEvent.getCode() == KeyCode.D) {
                    submitEvent("D");
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
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null) {
            updateScene("");
            updateItems();
        } else if (output.equals("YOU WIN")) {
            stopArticulation();
            updateScene("YOU WIN");
            updateItems();
        } else if (output.equals("YOU LOST")) {
            stopArticulation();
            updateScene("YOU DIED! GAME OVER!");
            updateItems();
        } else {
            updateScene(output);
            updateItems();
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
    /**
     * Show the setting menu
     */
    public void showSettingMenu(){
        //update setting on current girdpane

        if (model != null) {
            this.stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
        }

        gridPane.getChildren().clear(); // reset gridpane

        increaseBrightnessButton = new Button("Increase Brightness");
        increaseBrightnessButton.setId("Increase Brightness");
        customizeButton(increaseBrightnessButton, 200, 50);
        makeButtonAccessible(increaseBrightnessButton, "Increase Brightness", "This button increases brightness.", "This button increases brightness, press it to increase the brightness of the scene");
        addIncreaseBrightnessEvent();
        addHoverEvent(increaseBrightnessButton);

        decreaseBrightnessButton = new Button("Decrease Brightness");
        decreaseBrightnessButton.setId("Decrease Brightness");
        customizeButton(decreaseBrightnessButton, 200, 50);
        makeButtonAccessible(decreaseBrightnessButton, "Decrease Brightness", "This button decreases brightness", "This button decreases brightness, press it to decrease the brightness of the scene");
        addDecreaseBrightnessEvent();
        addHoverEvent(decreaseBrightnessButton);

        increaseContrastButton = new Button("Increase Contrast");
        increaseContrastButton.setId("increaseContrast");
        customizeButton(increaseContrastButton, 200, 50);
        makeButtonAccessible(increaseContrastButton, "Increase Contrast", "This button increases contrast", "This button increases contrast, press it to increase the contrast of the scene");
        addIncreaseContrastEvent();
        addHoverEvent(increaseContrastButton);

        decreaseContrastButton = new Button("Decrease Contrast");
        decreaseContrastButton.setId("decreaseContrast");
        customizeButton(decreaseContrastButton, 200, 50);
        makeButtonAccessible(decreaseContrastButton, "Decrease Contrast", "This button decreases contrast", "This button decreases contrast, press it to decrease the contrast of the scene");
        addDecreaseContrastEvent();
        addHoverEvent(decreaseContrastButton);

        increaseVolumeButton = new Button("Increase Volume");
        increaseVolumeButton.setId("increaseVolume");
        customizeButton(increaseVolumeButton, 200, 50);
        makeButtonAccessible(increaseVolumeButton, "Increase Volume", "This button increases volume", "This button increases the volume of background music");
        addIncreaseVolumeEvent();
        addHoverEvent(increaseVolumeButton);

        decreaseVolumeButton = new Button("Decrease Volume");
        decreaseVolumeButton.setId("decreaseVolume");
        customizeButton(decreaseVolumeButton, 200, 50);
        makeButtonAccessible(decreaseVolumeButton, "Decrease Volume", "This button Decreases volume", "This button Decreases the volume of background music");
        addDecreaseVolumeEvent();
        addHoverEvent(decreaseVolumeButton);

        HBox brightness = new HBox();
        brightness.getChildren().addAll(increaseBrightnessButton, decreaseBrightnessButton);
        brightness.setSpacing(30);
        brightness.setAlignment(Pos.CENTER);

        HBox contrast = new HBox();
        contrast.getChildren().addAll(increaseContrastButton, decreaseContrastButton);
        contrast.setSpacing(30);
        contrast.setAlignment(Pos.CENTER);

        HBox volume = new HBox();
        volume.getChildren().addAll(increaseVolumeButton, decreaseVolumeButton);
        volume.setSpacing(30);
        volume.setAlignment(Pos.CENTER);

        //settingButtons.getChildren().clear();
        settingButtons.getChildren().addAll(brightness, contrast, volume);
        settingButtons.setSpacing(30);
        settingButtons.setAlignment(Pos.BOTTOM_CENTER);

        gridPane.add(settingButtons, 1 ,1);

    }
    /**
     * setup the setting button
     */
    public void addSettingEvent(){
        settingsButton.setOnAction(e -> {
            String hitNormal = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(hitNormal).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

            gridPane.getChildren().clear(); // reset gridpane
            settingButtons.getChildren().clear();//reset buttons
            // Buttons
            menuButton = new Button("Home");
            menuButton.setId("Home");
            customizeButton(menuButton, 200, 50);
            makeButtonAccessible(menuButton, "Home", "This button goes back to the homepage", "This button goes back to the homepage, click it to go back to the homepage");
            addMenuEvent();
            addHoverEvent(menuButton);
            settingButtons.getChildren().add(menuButton);

            //paint rest of thebuttons
            showSettingMenu();
        });
    }

    /**
     * setting up Home Button
     */
    public void addMenuEvent(){
        menuButton.setOnAction(e -> {
//            gridPane.getChildren().clear();
//            gridPane.add( Buttons, 1, 1 );  // Add buttons
//            gridPane.add(settingsButton, 2, 0);
//            addSettingEvent();
            String hitNormal = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(hitNormal).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();
//          gridPane.getChildren().clear();
//          gridPane.add( Buttons, 1, 1 );  // Add buttons
//          gridPane.add(settingsButton, 2, 0);
//          addSettingEvent();
            gridPane.requestFocus();
            stopArticulation();
            gridPane.getChildren().clear();
            returnHome();
        });

    }
    /**
     * Setup increaseBrightness Button
     */
    public void addIncreaseBrightnessEvent(){
        increaseBrightnessButton.setOnAction(e -> {
            setting.increaseBrightness(this.gridPane);
        });

    }
    /**
     * Setup decreaseBrightness Button
     */
    public void addDecreaseBrightnessEvent(){
        decreaseBrightnessButton.setOnAction(e -> {
              setting.decreaseBrightness(this.gridPane);
        });

    }
    /**
     * Setup increaseContrast Button
     */
    public void addIncreaseContrastEvent(){
        increaseContrastButton.setOnAction(e -> {
            setting.increaseContrast(this.gridPane);
        });
    }
    /**
     * Setup decreaseContrast Button
     */
    public void addDecreaseContrastEvent(){
        decreaseContrastButton.setOnAction(e -> {
            setting.decreaseContrast(this.gridPane);
        });
    }
    /**
     * showSetting in Game
     */
    public void showSettingInGame(){
        showSettingMenu();
    }
    /**
     * Setup increaseVolume Button
     */
    public void addIncreaseVolumeEvent(){
        increaseVolumeButton.setOnAction(e -> {
            Setting.increaseVolume(this.mediaPlayer);
        });
    }
    /**
     * Setup decreaseVolume Button
     */
    public void addDecreaseVolumeEvent(){
        decreaseVolumeButton.setOnAction(e -> {
            Setting.decreaseVolume(this.mediaPlayer);
        });
    }

    /**
     * Set up setting button in Game
     */
    public void addSettingInGameEvent(){
        settingInGameButton.setOnAction(e -> {
            gridPane.getChildren().clear(); // reset gridpane
            settingButtons.getChildren().clear();//reset buttons
            //make button
            // Buttons
            gobackButton = new Button("Resume Game");
            gobackButton.setId("Resume Game");
            customizeButton(gobackButton, 200, 50);
            makeButtonAccessible(gobackButton, "Resume Game", "This button resumes the game", "This button resumes the game, press it to continue the game");
            addGoback();
            settingButtons.getChildren().add(gobackButton);
            addHoverEvent(gobackButton);

            //paint rest of the buttons
            showSettingInGame();
        });
    }
    /**
     * Setup Resume Game Button
     */
    public void addGoback(){
        gobackButton.setOnAction(e -> {
            gridPane.getChildren().clear();
            try {
                intiGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            updateScene("");
        });
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
     * Show the current room, and print some text below it. Update Music, Audio
     * and player stats as well.
     * If troll is in the room, will show all the round fighting with troll
     * The priority of the display messages would be Win/Lose/Invalid command messages
     * > Troll play messages > Room descriptions
     * If more than one messages is possible at the same time, the one with higher priority
     * would be presented.
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 2);
        Label playerHealth = new Label("❤️: " + model.player.health);
        playerHealth.setStyle("-fx-text-fill: RED; -fx-background: transparent; -fx-background-color: transparent");
        playerHealth.setFont(new Font("Arial", 24));
        playerHealth.setAlignment(Pos.CENTER);
        Label playerDamage = new Label("🔪: " + model.player.damage);
        playerDamage.setStyle("-fx-text-fill: YELLOW; -fx-background: transparent; -fx-background-color: transparent");
        playerDamage.setFont(new Font("Arial", 24));
        playerDamage.setAlignment(Pos.CENTER);
        Label playerDefense = new Label("🛡️: " + model.player.defense);
        playerDefense.setStyle("-fx-text-fill: GREEN; -fx-background: transparent; -fx-background-color: transparent");
        playerDefense.setFont(new Font("Arial", 24));
        playerDefense.setAlignment(Pos.CENTER);
        Label playerPoison;
        if (this.model.player.isImmune) {
            playerPoison = new Label("Immune: ✅");
        } else {
            playerPoison = new Label("Immune: ❌");
        }
        playerPoison.setStyle("-fx-text-fill: BLUE; -fx-background: transparent; -fx-background-color: transparent");
        playerPoison.setFont(new Font("Arial", 24));
        playerPoison.setAlignment(Pos.CENTER);
        VBox player = new VBox(playerHealth, playerDefense, playerDamage, playerPoison);
        player.setStyle("-fx-background: rgba(255,255,255,0.3); -fx-background-color: rgba(255,255,255,0.3)");
        player.setPadding(new Insets(20, 20, 20, 20));
        player.setSpacing(5);
        gridPane.add(player, 0, 2);

        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 2);

        Image roomImageFile = getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        VBox roomPane = new VBox(roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.BOTTOM_CENTER);
        roomPane.setStyle("-fx-background-color: rgba(255,255,255,0.3)");
        gridPane.add(roomPane, 1, 2);

        if (model.player.getCurrentRoom() instanceof TrollRoom && textToDisplay.equals(((TrollRoom) model.player.getCurrentRoom()).troll.getInstructions())) {
            mapToggle = false; // Fixes syncing issue on map
            if (((TrollRoom) model.player.getCurrentRoom()).troll instanceof Fighting_Troll) {
                String musicFile;
                String adventureName = this.model.getDirectoryName();
                musicFile = "./" + adventureName + "/sounds/Fight.mp3" ;
                Media sound = new Media(new File(musicFile).toURI().toString());
                if (!audioPlaying) {
                    audioPlayer = new MediaPlayer(sound);
                    audioPlayer.play();
                    audioPlaying = true;
                }

                this.stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
                Image zombieImage = new Image("Games/Zombie.png"); //get the image of the zombie
                ImageView zombieImageView = new ImageView(zombieImage);
                zombieImageView.setFitHeight(300);
                zombieImageView.setFitWidth(300);

                if (this.model.player.health > 0 && ((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).getHealth() > 0) {
                    gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1);
                    Label zombieHealth = new Label("❤️: " + ((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).getHealth());
                    zombieHealth.setStyle("-fx-text-fill: RED; -fx-background: transparent; -fx-background-color: transparent");
                    zombieHealth.setFont(new Font("Arial", 24));
                    zombieHealth.setAlignment(Pos.CENTER);
                    Label zombieDamage = new Label("🔪: " + ((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).getDamage());

                    zombieDamage.setStyle("-fx-text-fill: YELLOW; -fx-background: transparent; -fx-background-color: transparent");
                    zombieDamage.setFont(new Font("Arial", 24));
                    zombieDamage.setAlignment(Pos.CENTER);
                    VBox zombie;

                    if (((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).round > 0) {
                        Label zombieRound = new Label("ROUND " + ((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).round);

                        zombieRound.setStyle("-fx-text-fill: WHITE; -fx-background: transparent; -fx-background-color: transparent");
                        zombieRound.setFont(new Font("Arial", 32));
                        zombieRound.setAlignment(Pos.CENTER);
                        zombie = new VBox(zombieRound, zombieImageView, zombieHealth, zombieDamage);
                    } else {
                        zombie = new VBox(zombieImageView, zombieHealth, zombieDamage);
                    }
                    zombie.setStyle("-fx-background: transparent; -fx-background-color: transparent");
                    zombie.setPadding(new Insets(20, 20, 20, 20));
                    zombie.setSpacing(5);
                    zombie.setAlignment(Pos.CENTER);
                    gridPane.add(zombie, 1, 1);
                    gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 0);

                    ((TrollRoom) model.player.getCurrentRoom()).troll.playRound(model.player);
                    if (this.model.player.getHealth() > 0 && ((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).getHealth() <= 0) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(2));
                        pause.setOnFinished(event -> {
                            this.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
                            gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1);
                            formatText("You beat the infected scientist");
                            HBox topButtons2 = new HBox();
                            topButtons2.getChildren().addAll(mapButton, shopButton, homepageButton, settingInGameButton, helpButton, saveButton, loadButton);
                            topButtons2.setSpacing(10);
                            topButtons2.setPrefWidth(400);
                            topButtons2.setAlignment(Pos.CENTER);
                            gridPane.add(topButtons2, 1, 0);
                        });
                        pause.play(); // method require revision
                    } else if (this.model.player.getHealth() > 0 && ((Fighting_Troll) ((TrollRoom) model.player.getCurrentRoom()).troll).getHealth() > 0) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(2));
                        pause.setOnFinished(event -> {
                            updateScene(((TrollRoom) model.player.getCurrentRoom()).troll.getInstructions());
                        });
                        pause.play(); // method require revision
                    } else {
                        updateScene("YOU DIED! GAME OVER!");
                    }

                }
            }
        }
        if (roomDescLabel.getText().equals("You beat the infected scientist")) {
            gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 2);
            roomPane = new VBox(roomDescLabel);
            roomPane.setPadding(new Insets(10));
            roomPane.setAlignment(Pos.BOTTOM_CENTER);
            roomPane.setStyle("-fx-background-color: rgba(255,255,255,0.3)");
            gridPane.add(roomPane, 1, 2);
        }
        stage.sizeToScene();
        BackgroundImage background = new BackgroundImage(roomImageFile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane.setBackground(new Background(background));

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();

        model.player.getCurrentRoom().visit();
        int roomNum = this.model.player.getCurrentRoom().getRoomNumber();
        if (textToDisplay.equals("YOU WIN")) {
            String path2 = "Games/Victory.mp3";
            if (!preMusic.equals(path2)) {
                preMusic = path2;
                mediaPlayer.dispose();
                Media media = new Media(Paths.get(path2).toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaPlaying = true;
            }
            gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 0);
            this.stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                returnHome();
                this.model = null;
            });
            pause.play();
        } else if (roomDescLabel.getText().equals("YOU DIED! GAME OVER!")) {
            String path2 = "Games/Game_Over.mp3";
            if (!preMusic.equals(path2)) {
                preMusic = path2;
                mediaPlayer.dispose();
                Media media = new Media(Paths.get(path2).toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaPlaying = true;
            }
            gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 0);
            this.stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                returnHome();
                this.model = null;
            });
            pause.play();
        } else if(roomNum <= 8 || roomNum ==15){
            String path2 = "Games/Sea_Of_Simulation.mp3";
            if (!preMusic.equals(path2)) {
                preMusic = path2;
                mediaPlayer.dispose();
                Media media = new Media(Paths.get(path2).toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                Setting.setVolume(mediaPlayer);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlaying = true;
            }
        }
        else if (roomNum <= 14){
            String path2 = "Games/Disc_Wars.mp3";
            if (!preMusic.equals(path2)) {
                preMusic = path2;
                mediaPlayer.dispose();
                Media media = new Media(Paths.get(path2).toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                Setting.setVolume(mediaPlayer);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlaying = true;
            }
        }
        else if (roomNum <= 19 && roomNum >= 16){

            String path2 = "Games/Flynn_Lives.mp3";
            if (!preMusic.equals(path2)) {
                preMusic = path2;
                mediaPlayer.dispose();
                Media media = new Media(Paths.get(path2).toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                Setting.setVolume(mediaPlayer);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlaying = true;
            }

        }
        map.generateMap();

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
        if (textToDisplay == null || textToDisplay.isBlank() || textToDisplay.equals("YOU WIN")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else {
            roomDescLabel.setText(textToDisplay);
        }
        if (roomDescLabel.getText().equals("You beat the infected scientist")) {
            String musicFile;
            stopArticulation();
            String adventureName = this.model.getDirectoryName();
            musicFile = "./" + adventureName + "/sounds/Win.mp3" ;
            Media sound = new Media(new File(musicFile).toURI().toString());
            if (!audioPlaying) {
                audioPlayer = new MediaPlayer(sound);
                audioPlayer.play();
                audioPlaying = true;
            }
        }
        roomDescLabel.setStyle("-fx-text-fill: BLACK;");
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
                        gridPane.requestFocus();
                    } else if (objectsInInventory.getChildren().contains(objectButton)) {
                        objectsInInventory.getChildren().removeIf(node -> node.equals(objectButton));
                        for (AdventureObject object : model.player.inventory) {
                            if (object.getName().equals(objectName) && object.getType().equals("Consumable")) {
                                model.player.useItem(object);
                                gridPane.requestFocus();
                                updateScene("");
                                break;
                            } else if (object.getName().equals(objectName)) {
                                objectsInRoom.getChildren().add(objectButton);
                                model.player.dropObject(object.getName());
                                gridPane.requestFocus();
                                break;
                            }
                            }
                        }
                    }
            };

            objectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, eventhandler);
            objectsInInventory.getChildren().add(objectButton);
        }

        ArrayList<AdventureObject> objectInRoom = this.model.player.getCurrentRoom().getObjectsinRoom();
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
                        gridPane.requestFocus();
                    } else if (objectsInInventory.getChildren().contains(objectButton)) {
                        objectsInInventory.getChildren().removeIf(node -> node.equals(objectButton));
                        for (AdventureObject object : model.player.inventory) {
                            if (object.getName().equals(objectButton.getText()) && object.getType().equals("Consumable")) {
                                model.player.useItem(object);
                                gridPane.requestFocus();
                                updateScene("");
                                break;
                            } else if (object.getName().equals(objectButton.getText())) {
                                objectsInRoom.getChildren().add(objectButton);
                                model.player.dropObject(object.getName());
                                gridPane.requestFocus();
                                break;
                            }
                        }
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
        scO.setStyle("-fx-background: rgba(255,255,255,0.3); -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 1);
        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setPadding(new Insets(10));
        scI.setMaxHeight(600);
        scI.setMaxWidth(150);
        scI.setStyle("-fx-background: rgba(255,255,255,0.3); -fx-background-color:transparent;");
        scI.setFitToWidth(true);
        gridPane.add(scI,2,1);
    }

    /**
     * showMap
     * __________________________
     *
     * This method will make map pop up on and off the screen when called based on mapToggle
     */
    public void showMap(){
        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1);
        if (mapToggle) {
            updateScene("");
            mapToggle = false;
        } else {
            map.generateMap();
            gridPane.add(map.showMap(), 1, 1);
            mapToggle = true;
        }


    }
    /**
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

        gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1);
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
        helpLabel.setFont(new Font("Arial", 15));
        helpLabel.setAlignment(Pos.CENTER);
        helpLabel.setPrefWidth(600);
        helpLabel.setPrefHeight(600);
        helpLabel.setTextOverrun(OverrunStyle.CLIP);
        helpLabel.setWrapText(true);
        VBox helpBox = new VBox(helpLabel);
        helpBox.setPadding(new Insets(10));
        helpBox.setAlignment(Pos.TOP_CENTER);
        helpBox.setStyle("-fx-background-color: rgba(0,0,0, 0.7);");
        helpLabel.setText(helpText);
        gridPane.add(helpBox, 1, 1);
        HBox lowButtons = new HBox();
        lowButtons.getChildren().addAll(homepageButton);
        lowButtons.setAlignment(Pos.CENTER);
        gridPane.add(lowButtons, 1, 2);
    }

    /**
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
    public void showHelp() throws IOException {
        if (helpToggle) {
            if (model != null) {
                this.stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, eventhandler);
            }
            gridPane.getChildren().removeIf(node -> true);
            helpToggle = false;
            intiGame();
            updateScene("");
        } else {
            gridPane.getChildren().removeIf(node -> true);
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
            helpLabel.setFont(new Font("Arial", 15));
            helpLabel.setAlignment(Pos.CENTER);
            helpLabel.setPrefWidth(600);
            helpLabel.setPrefHeight(600);
            helpLabel.setTextOverrun(OverrunStyle.CLIP);
            helpLabel.setWrapText(true);
            VBox helpBox = new VBox(helpLabel);
            helpBox.setPadding(new Insets(10));
            helpBox.setAlignment(Pos.TOP_CENTER);
            helpBox.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
            helpLabel.setText(helpText);
            gridPane.add(helpBox, 1, 1);
            HBox lowButtons = new HBox();
            lowButtons.getChildren().addAll(helpButton);
            lowButtons.setAlignment(Pos.CENTER);
            gridPane.add(lowButtons, 1, 2);
            helpToggle = true;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addHelpEvent() {
        helpButton.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

            stopArticulation(); //if speaking, stop
            try {
                showHelp();
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
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

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
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

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
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

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
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();
            
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to pressing the
     * easy button.
     */
    public void addEasyEvent() {
        easyButton_home.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();
            adventureName = "EasyGame";

            gridPane.requestFocus();
            this.model = new AdventureGame("EasyGame");
            shop = new Shop(adventureName);
            gridPane.getChildren().removeIf(node -> true);
            try {
                map = new Map(this);
                map.generateMap();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stopArticulation();


            try {
                intiGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * This method handles the event related to pressing the
     * medium button.
     */
    public void addMediumEvent() {
        mediumButton_home.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();
            adventureName = "MediumGame";

            gridPane.requestFocus();
            this.model = new AdventureGame("MediumGame");
            shop = new Shop(adventureName);
            gridPane.getChildren().removeIf(node -> true);
            try {
                map = new Map(this);
                map.generateMap();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stopArticulation();
            try {
                intiGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * This method handles the event related to pressing the
     * hard button.
     */
    public void addHardEvent() {
        hardButton_home.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();
            adventureName = "HardGame";

            gridPane.requestFocus();
            this.model = new AdventureGame("HardGame");
            shop = new Shop(adventureName);
            gridPane.getChildren().removeIf(node -> true);
            try {
                map = new Map(this);
                map.generateMap();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stopArticulation();
            try {
                intiGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // Shop interface when button is clicked
    public void addShopEvent() {
        shopButton.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

            gridPane.requestFocus();
            showShop();
        });
    }

    /**
     * This method handles the event related to pressing the
     * home button
     */

    public void addHomeEvent() {
        homepageButton.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

            gridPane.requestFocus();
            stopArticulation();
            returnHome();
        });
    }

    /**
     * addMapEvent
     * __________________________
     *
     * Adds the showMap method as an event to the map button.
     */
    public void addMapEvent() {
        mapButton.setOnAction(e -> {

            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

            showMap();

        });
    }
//
//    /**
//     * This method creates an effect when a button is hovered over.
//     */
//    private void addHoverEvent(Button button) {
//        button.setOnMouseEntered(event -> {
//            button.setStyle("-fx-background-color: #7698ef; -fx-text-fill: #000000;"); // Change button colour});
//
//            button.setOnMouseExited(event -> {
//                button.setStyle("-fx-background-color: #184ac9; -fx-text-fill: white;");
//
    /**
     *
     * Returns back to game screen when exiting shop
     */
    public void addReturnEvent() {
        backButton.setOnAction(e -> {
            String soundFile = ("Games/BUTTON.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
            mediaPlayer2.play();

            gridPane.getChildren().clear();
            updateScene("");
            updateItems();
            try {
                intiGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * This method handles the event of player purchasing a vest in shop
     */
    public void addBuyVestEvent() {
        buyVestButton.setOnAction(e -> {
            String soundFile = ("Games/BUY.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer3 = new MediaPlayer(sound);
            String soundFile2 = ("Games/BUZZER.mp3");
            Media sound2 = new Media(new File(soundFile2).toURI().toString());
            MediaPlayer mediaPlayer4 = new MediaPlayer(sound2);

            for (AdventureObject object: shop.objectsForSale.keySet()) {
                if (object.getName().equals("VEST")) {
                    if (model.player.buyObject(object, shop)) {
                        mediaPlayer3.play();
                    } else {
                        mediaPlayer4.play();
                    }
                }
            }
        });
    }

    /**
     * This method handles the event of player purchasing a stim in shop
     */
    public void addBuyStimEvent() {
        buyStimButton.setOnAction(e -> {
            String soundFile = ("Games/BUY.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer3 = new MediaPlayer(sound);
            String soundFile2 = ("Games/BUZZER.mp3");
            Media sound2 = new Media(new File(soundFile2).toURI().toString());
            MediaPlayer mediaPlayer4 = new MediaPlayer(sound2);

            for (AdventureObject object: shop.objectsForSale.keySet()) {
                if (object.getName().equals("STIM")) {
                    if (model.player.buyObject(object, shop)) {
                        mediaPlayer3.play();
                    } else {
                        mediaPlayer4.play();
                    }
                }
            }
        });
    }
    /**
     * This method handles the event of player purchasing a medkit in shop
     */
    public void addBuyMedkitEvent() {
        buyMedkitButton.setOnAction(e -> {
            String soundFile = ("Games/BUY.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer3 = new MediaPlayer(sound);
            String soundFile2 = ("Games/BUZZER.mp3");
            Media sound2 = new Media(new File(soundFile2).toURI().toString());
            MediaPlayer mediaPlayer4 = new MediaPlayer(sound2);

            for (AdventureObject object: shop.objectsForSale.keySet()) {
                if (object.getName().equals("MEDKIT")) {
                    if (model.player.buyObject(object, shop)) {
                        mediaPlayer3.play();
                    } else {
                        mediaPlayer4.play();
                    }
                }
            }
        });
    }
    /**
     * This method handles the event of player purchasing a mask in shop
     */
    public void addBuyMaskEvent() {
        buyMaskButton.setOnAction(e -> {
            String soundFile = ("Games/BUY.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer3 = new MediaPlayer(sound);
            String soundFile2 = ("Games/BUZZER.mp3");
            Media sound2 = new Media(new File(soundFile2).toURI().toString());
            MediaPlayer mediaPlayer4 = new MediaPlayer(sound2);

            for (AdventureObject object: shop.objectsForSale.keySet()) {
                if (object.getName().equals("MASK")) {
                    if (model.player.buyObject(object, shop)) {
                        mediaPlayer3.play();
                    } else {
                        mediaPlayer4.play();
                    }
                }
            }
        });
    }
    /**
     * This method handles the event of player purchasing a skip in shop
     */
    public void addBuySkipEvent() {
        buySkipButton.setOnAction(e -> {
            String soundFile = ("Games/BUY.mp3");
            Media sound = new Media(new File(soundFile).toURI().toString());
            MediaPlayer mediaPlayer3 = new MediaPlayer(sound);
            String soundFile2 = ("Games/BUZZER.mp3");
            Media sound2 = new Media(new File(soundFile2).toURI().toString());
            MediaPlayer mediaPlayer4 = new MediaPlayer(sound2);

            for (AdventureObject object: shop.objectsForSale.keySet()) {
                if (object.getName().equals("SKIP")) {
                    if (model.player.buyObject(object, shop)) {
                        mediaPlayer3.play();
                    } else {
                        mediaPlayer4.play();
                    }
                }
            }
        });
    }
    /**
     * This method creates an effect when a button is hovered over.
     */
    private void addHoverEvent(Button button) {
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: #7698ef; -fx-text-fill: #000000;"); // Change button colour
        });

        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: #184ac9; -fx-text-fill: white;");
        });
    }
    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisitedNormal()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");
        Media sound = new Media(new File(musicFile).toURI().toString());

        audioPlayer = new MediaPlayer(sound);
        audioPlayer.play();
        audioPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (audioPlaying) {
            audioPlayer.stop(); //shush!
            audioPlaying = false;
        }
    }

    /**
     * This method stops music
     * (useful when loading a new game)
     */
    public void stopMusic() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
    /**
    * This method implements the interface of Shop, then sends to addShopEvent()
    *
    */
    public void showShop() {
        Image shopImage = new Image("Games/Shop.png");
        BackgroundImage backgroundImage = new BackgroundImage(shopImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BorderPane shopPane = new BorderPane();
        gridPane.getChildren().clear();
        gridPane.setBackground(new Background(backgroundImage));
        gridPane.add(backButton, 2, 0);


        Image vest = new Image("Games/EasyGame/objectImages/VEST.png");
        ImageView vest_iv = new ImageView(vest);
        vest_iv.setFitHeight(150);
        vest_iv.setFitWidth(150);
        buyVestButton = new Button("Buy Vest", vest_iv);
        addBuyVestEvent();
        buyVestButton.setId("Buy Vest");
        buyVestButton.setContentDisplay(ContentDisplay.TOP);
        customizeButton(buyVestButton, 150, 150);
        makeButtonAccessible(buyVestButton, "Buy Vest Button", "This button allows the player to purchase a vest", "This button allows the player to purchase a vest");
        buyVestButton.setTooltip(new Tooltip("A bulletproof vest crafted from durable kevlar.\nThis gives the player 10 defense when consumed."));
        VBox vestV = new VBox();
        Button vestQuant = new Button();
        customizeButton(vestQuant, 150, 50);
        Button vestPrice = new Button("$10");
        customizeButton(vestPrice, 150, 50);
        buyVestButton.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        vestQuant.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        vestPrice.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("VEST")) {
                vestQuant.setText(shop.objectsForSale.get(object).toString() + " left");
            }
        }
        vestV.getChildren().addAll(vestPrice, buyVestButton, vestQuant);
        vestV.setAlignment(Pos.CENTER);
        vestV.setSpacing(30);

        Image stim = new Image("Games/EasyGame/objectImages/STIM.png");
        ImageView stim_iv = new ImageView(stim);
        stim_iv.setFitHeight(150);
        stim_iv.setFitWidth(150);
        buyStimButton = new Button("Buy Stim", stim_iv);
        addBuyStimEvent();
        buyStimButton.setId("Buy Stim");
        buyStimButton.setContentDisplay(ContentDisplay.TOP);
        customizeButton(buyStimButton, 150, 150);
        makeButtonAccessible(buyStimButton, "Buy Stim Button", "This button allows the player to purchase a stimulant", "This button allows the player to purchase a stimulant");
        buyStimButton.setTooltip(new Tooltip("A slender syringe containing a potent cocktail of stimulants.\nThis increases player's damage by 20 when consumed"));
        VBox stimV = new VBox();
        Button stimQuant = new Button();
        customizeButton(stimQuant, 150, 50);
        Button stimPrice = new Button("$10");
        customizeButton(stimPrice, 150, 50);
        buyStimButton.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        stimQuant.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        stimPrice.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("STIM")) {
                stimQuant.setText(shop.objectsForSale.get(object).toString() + " left");
            }
        }
        stimV.getChildren().addAll(stimPrice, buyStimButton, stimQuant);
        stimV.setAlignment(Pos.CENTER);
        stimV.setSpacing(30);

        Image med = new Image("Games/EasyGame/objectImages/MEDKIT.png");
        ImageView med_iv = new ImageView(med);
        med_iv.setFitHeight(150);
        med_iv.setFitWidth(150);
        buyMedkitButton = new Button("Buy Medkit", med_iv);
        addBuyMedkitEvent();
        buyMedkitButton.setId("Buy Medkit");
        buyMedkitButton.setContentDisplay(ContentDisplay.TOP);
        customizeButton(buyMedkitButton, 150, 150);
        makeButtonAccessible(buyMedkitButton, "Buy Medkit Button", "This button allows the player to purchase a medkit", "This button allows the player to purchase a medkit");
        buyMedkitButton.setTooltip(new Tooltip("A state-of-the-art medical kit equipped with advanced healing technology.\nThis heals the player for 20HP when consumed."));
        VBox medV = new VBox();
        Button medQuant = new Button();
        customizeButton(medQuant, 150, 50);
        Button medPrice = new Button("$5");
        customizeButton(medPrice, 150, 50);
        buyMedkitButton.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        medQuant.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        medPrice.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("MEDKIT")) {
                medQuant.setText(shop.objectsForSale.get(object).toString() + " left");
            }
        }
        medV.getChildren().addAll(medPrice, buyMedkitButton, medQuant);
        medV.setAlignment(Pos.CENTER);
        medV.setSpacing(30);

        Image mask = new Image("Games/EasyGame/objectImages/MASK.png");
        ImageView mask_iv = new ImageView(mask);
        mask_iv.setFitHeight(150);
        mask_iv.setFitWidth(150);
        buyMaskButton = new Button("Buy Mask", mask_iv);
        addBuyMaskEvent();
        buyMaskButton.setId("Buy Mask");
        buyMaskButton.setContentDisplay(ContentDisplay.TOP);
        customizeButton(buyMaskButton, 150, 150);
        makeButtonAccessible(buyMaskButton, "Buy Mask Button", "This button allows the player to purchase a gas mask", "This button allows the player to purchase a gas mask");
        buyMaskButton.setTooltip(new Tooltip("A gas mask blocks off noxious fumes. Perfect for exploring hazardous environments.\nThis makes the player immune to toxic gas when consumed."));
        VBox maskV = new VBox();
        Button maskQuant = new Button();
        customizeButton(maskQuant, 150, 50);
        Button maskPrice = new Button("$10");
        customizeButton(maskPrice, 150, 50);
        buyMaskButton.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        maskQuant.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        maskPrice.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("MASK")) {
                maskQuant.setText(shop.objectsForSale.get(object).toString() + " left");
            }
        }
        maskV.getChildren().addAll(maskPrice, buyMaskButton, maskQuant);
        maskV.setAlignment(Pos.CENTER);
        maskV.setSpacing(30);

        Image skip = new Image("Games/EasyGame/objectImages/SKIP.png");
        ImageView skip_iv = new ImageView(skip);
        skip_iv.setFitHeight(150);
        skip_iv.setFitWidth(150);
        buySkipButton = new Button("Buy Gun", skip_iv);
        addBuySkipEvent();
        buySkipButton.setId("Buy Skip");
        buySkipButton.setContentDisplay(ContentDisplay.TOP);
        customizeButton(buySkipButton, 150, 150);
        makeButtonAccessible(buySkipButton, "Buy Skip Button", "This button allows the player to purchase a skip", "This button allows the player to purchase a skip");
        buySkipButton.setTooltip(new Tooltip("The Glock. One shot, one kill.\nThis item skips the current fight when consumed."));
        VBox skipV = new VBox();
        Button skipQuant = new Button();
        customizeButton(skipQuant, 150, 50);
        Button skipPrice = new Button("$10");
        customizeButton(skipPrice, 150, 50);
        buySkipButton.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        skipQuant.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        skipPrice.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("SKIP")) {
                skipQuant.setText(shop.objectsForSale.get(object).toString() + " left");
            }
        }
        skipV.getChildren().addAll(skipPrice, buySkipButton, skipQuant);
        skipV.setAlignment(Pos.CENTER);
        skipV.setSpacing(30);

        HBox forSale = new HBox();
        if (adventureName.equals("EasyGame")) {
            forSale.getChildren().addAll(vestV, stimV, medV);
        } else if (adventureName.equals("MediumGame")) {
            forSale.getChildren().addAll(vestV, stimV, medV, maskV);
        } else {
            forSale.getChildren().addAll(vestV, stimV, medV, maskV, skipV);
        }
        forSale.setSpacing(30);
        forSale.setAlignment(Pos.CENTER);
        gridPane.add(forSale, 0, 1);

        Button funds = new Button("Funds: $" + Integer.toString(model.player.getFunds()));
        customizeButton(funds, 200, 50);
        funds.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
        gridPane.add(funds, 1, 2);
//        ImageView robot = new ImageView(new Image("Games/ROBOT.png"));
//        robot.setFitWidth(200);
//        robot.setFitHeight(200);
//        Button helper = new Button("Welcome to the shop!\nHere, you can spend any loose change you find around the ship\nto buy equipment that will aid your journey.", robot);
//        customizeButton(helper, 450, 250);
//        helper.setStyle("-fx-background-color: #0e90ed; -fx-text-fill: #ffffff;");
//        helper.setContentDisplay(ContentDisplay.RIGHT);
//        gridPane.add(helper, 2, 2);
    }
}
