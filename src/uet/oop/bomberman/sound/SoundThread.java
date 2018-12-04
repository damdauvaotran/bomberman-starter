package uet.oop.bomberman.sound;

public class SoundThread implements Runnable {
    String _fileName;

    public SoundThread(String fileName){
        _fileName = fileName;
    }

    @Override
    public void run() {
        Sound s= new Sound(_fileName);
        s.play();
    }
}
