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
    private double contrast = 0;

    public static double volume = 0.5;
    GridPane p;

    public Setting(GridPane p){
        this.p = p;
    }

    public void increaseBrightness(GridPane p){
        if (brightness < 0.7) {
            brightness += 0.1;
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(brightness);
            p.setEffect(colorAdjust);
        }
    }

    public void decreaseBrightness(GridPane p){
        if (brightness > -0.7) {
            brightness -= 0.1;
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(brightness);
            p.setEffect(colorAdjust);
        }
    }

    public void increaseContrast(GridPane p){
        if (contrast < 0.7) {
            contrast += 0.1;
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(contrast);
            p.setEffect(colorAdjust);
        }
    }

    public void decreaseContrast(GridPane p){
        if (contrast > -0.7) {
            contrast -= 0.1;
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(contrast);
            p.setEffect(colorAdjust);
        }
    }

    public static void setVolume(MediaPlayer m){
        m.setVolume(volume);
    }

    public static void increaseVolume(MediaPlayer m){
        if(volume < 1){
            volume += 0.1;
            m.setVolume(volume);
        }
    }
    public static void decreaseVolume(MediaPlayer m){
        if(volume > 0){
            volume -= 0.1;
            m.setVolume(volume);
        }
    }





}
