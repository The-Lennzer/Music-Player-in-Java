import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Driver {

    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        //creates object of PlaylistHandler to initiate Playlist names
        PlaylistHandler pr = new PlaylistHandler();
        //reads song file names into array readNow
        File[] readNow = new File("Songs/").listFiles();
        //This statements ensures the program that readNow will always have a value
        assert readNow != null;
        //Reads all song Names into a list using initiateSongs method
        SongHandler.initiateSongs(readNow);
        //Starts the menu of actual program
        StartInterface.First();
    }
}

