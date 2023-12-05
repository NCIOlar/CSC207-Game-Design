package views;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Music {
    public static boolean isPlaying = true;

    /**
     * initiating class
     */
    public Music(){

    }
    /**
     * stop playing music
     */
    public static void stop(MediaPlayer m){
        m.stop();
        isPlaying = false;
    }
    /**
     * play music
     */
    public static void play(MediaPlayer m){
        m.play();
        isPlaying = true;
    }
    /**
     * check if music is playing
     */
    public boolean isPlaying(MediaPlayer m){
        return isPlaying;
    }
    /**
     * play music given media and mediaplayer
     */
    public void setMusic(MediaPlayer m, Media media){
        m = new MediaPlayer(media);
        m.setAutoPlay(true);
        Setting.setVolume(m);
        m.setCycleCount(MediaPlayer.INDEFINITE);
        isPlaying = true;
    }

}
