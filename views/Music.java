package views;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Music {
    public static boolean isPlaying = true;

    public Music(){

    }

    public static void stop(MediaPlayer m){
        m.stop();
        isPlaying = false;
    }
    public static void play(MediaPlayer m){
        m.play();
        isPlaying = true;
    }
    public boolean isPlaying(MediaPlayer m){
        return isPlaying;
    }
    public void setMusic(MediaPlayer m, Media media){
        m = new MediaPlayer(media);
        m.setAutoPlay(true);
        Setting.setVolume(m);
        m.setCycleCount(MediaPlayer.INDEFINITE);
        isPlaying = true;
    }

}
