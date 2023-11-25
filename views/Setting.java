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

public class Setting {
    private double brightness = 0;
    GridPane p;

    public Setting(GridPane p){
        this.p = p;
    }

    public double increaseBrightness(){
        brightness += 0.2;
        return brightness;

    }

    public double decreaseBrightness(){
        brightness -= 0.2;
        return brightness;


    }



}
