package uet.oop.bomberman.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import sun.util.resources.cldr.pa.CurrencyNames_pa;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Sound {
    private Player _player;
    private boolean _stop = false;

    public Sound(String soundFileName) {
        try {

            String path = "/sounds/effects/" + soundFileName + ".mp3";
            InputStream in = this.getClass().getResourceAsStream(path);

            _player = new Player(in);

        } catch (JavaLayerException e) {

            e.printStackTrace();
        }

    }


    public void play() {
        try {
            _player.play();
            System.out.println("lol");
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void loopInf() {
        while (!_stop) {
            if (!_player.isComplete()) {
                try {
                    _player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        _stop = false;
    }
}
