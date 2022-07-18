import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlaylistHandler {
    static Collections playlistNames = new Collections();
    //Constructor initialises the linkedlist PlaylistNames using data from Playlists.txt
    PlaylistHandler() throws FileNotFoundException {
        File read = new File("Files/Playlists.txt");
        Scanner sc = new Scanner(read);
        while(sc.hasNextLine()){
            String name = sc.nextLine();
            playlistNames.add(name);
        }
    }
    //adds new Playlist to playlist names
    static void whenAdded(String name){
        if(!playlistNames.search(name)){playlistNames.add(name);}
    }
}
