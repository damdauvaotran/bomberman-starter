package uet.oop.bomberman.sound.effect;


import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.sound.SoundThread;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    EXPLODE("explode"),  // explosion
    GHOST("ghost");


    // Nested class for specifying volume


    // Each sounds effects has its own clip, loaded with its own sounds file.
    private Clip _clip;
    public String _name;

    SoundEffect(String soundFileName) {
        try {
            _name = soundFileName;

            URL url = this.getClass().getResource("/sounds/effects/" + soundFileName+".wav");

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            _clip = (Clip) AudioSystem.getLine(info);
            _clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        _clip.setLoopPoints(0, -1);
    }

    // Play or Re-play the sounds effects from the beginning, by rewinding.
    public void play() {
        Thread t = new Thread(new SoundThread(_name));
        t.start();
    }

    public void loopInf() {
        if (_clip.isActive()) {
            return;
        }
        _clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        _clip.stop();
    }

    // Optional static method to pre-load all the sounds files.
    public static void init() {
        values(); // calls the constructor for all the elements
    }
}