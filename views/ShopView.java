package views;

import AdventureModel.*;
import views.Map;
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
import java.util.ArrayList;

public class ShopView {
    AdventureGame game;
    BorderPane shopPane;
    Button backButton;

    public ShopView(AdventureGameView game) throws IOException {
        this.game = game.model;
        this.shopPane = new BorderPane();
        this.backButton = new Button("Back");
        backButton.setId("back");
    }

//    public void showShop() {
//        Image shopImage = new Image("Games/Shop.png");
//        BackgroundImage backgroundImage = new BackgroundImage(shopImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//        BorderPane shopPane = new BorderPane();
//        shopPane.setBackground(new Background(backgroundImage));
//        shopPane.getChildren().add(backButton, 2, 0);

//    }
}
