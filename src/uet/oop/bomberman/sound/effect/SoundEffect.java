package uet.oop.bomberman.sound.effect;


import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    EXPLODE("explode.wav"),  // explosion
    GHOST("ghost.wav");


    // Nested class for specifying volume


    // Each sounds effects has its own clip, loaded with its own sounds file.
    private Clip clip;

    // Constructor to construct each element of the enum with its own sounds file.
    SoundEffect(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getResource("/sounds/effects/" + soundFileName);

            // Set up an audio input stream piped from the sounds file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            // Get a lip resource.
            clip = (Clip) AudioSystem.getLine(info);
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.setLoopPoints(0, -1);
    }

    // Play or Re-play the sounds effects from the beginning, by rewinding.
    public void play() {
        System.out.println("SoundEffect " + clip.isActive());
        if (clip.isActive()) {
            clip.stop();
            clip.setFramePosition(0);
        }


        clip.start();
    }

    public void loopInf (){
        if (clip.isActive()){
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    // Optional static method to pre-load all the sounds files.
    public static void init() {
        values(); // calls the constructor for all the elements
    }
}