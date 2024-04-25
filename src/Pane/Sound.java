package Pane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {

    public String file;
    public Media sound;
    public MediaPlayer player;

    public Sound(String file) {
        this.file = file;
        this.sound = new Media(new File(file).toURI().toString());
        this.player = new MediaPlayer(sound);
    }

    public void play(){
        player.play();
    }

    public void stop(){
        player.stop();
    }

    public void setVolume(double value){
        player.setVolume(value);
    }


}
