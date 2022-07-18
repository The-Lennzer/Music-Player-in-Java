import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class StartInterface {
    //HOMEPAGE
    static void First() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        int choose;
        do {
            System.out.println("\n");
            System.out.println("        -----------------");
            System.out.println("        SRR MUSIC PLAYER");
            System.out.println("        ------------------\n");
            System.out.println(
                    "   1.Create Playlist");
            System.out.println(
                    "   2.Add Song To Playlist");
            System.out.println(
                    "   3.View Playlists");
            System.out.println(
                    "   4.View All Songs");
            System.out.println(
                    "   5.View Songs in Playlist");
            System.out.println(
                    "   6.Play Songs");
            System.out.println(
                    "   7.Delete Songs From Playlist");
            System.out.println(
                    "   8.Undo a delete Operation on Playlist");
            System.out.println(
                    "   Enter 0 to Exit");
            System.out.print(
                    "  \n Enter your Choice: ");

            Scanner sc = new Scanner(System.in);
            choose = sc.nextInt();
            if(!(choose == 1||choose == 2||choose == 3|| choose == 4|| choose ==5||choose == 6||choose ==7||choose==8||choose ==0)){
                System.out.println("Invalid Choice, Please revisit the menu for clarifications!");
                continue;
            }
            System.out.println();
            Playlists pls = new Playlists();

            switch (choose) {
                case 1 -> {
                    System.out.println("Create New Playlist"); pls.makePlaylist();}
                case 2 -> {
                    System.out.println("Add Songs To Playlist");
                    String name = Playlists.next_line("Enter Playlist Name: ");
                    if (PlaylistHandler.playlistNames.search(name)){
                        pls.addSongToPlaylist(name);
                    }
                    else {
                        System.out.println("Playlist doesn't exist!");
                    }
                }
                case 3 -> {
                    System.out.println("All Playlists Ever Made: "); pls.displayList();}
                case 5 -> {
                    String name = Playlists.next_line("Enter the Playlist Name: ");
                    SongHandler.DisplaySongsInPlaylist(name);
                }
                case 6 -> {
                    String pname = Playlists.next_line("Enter the name of the playlist you want to play: ");
                    System.out.println("\n\nPlaylist: " + pname);
                    System.out.println("Initiating Player...\n");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Player START");
                    pls.startPlaylist(pname);
                    SongHandler shd = new SongHandler();
                    System.out.println();
                    shd.Play();
                    TimeUnit.SECONDS.sleep(1);
                }
                case 4 -> {
                    System.out.println("All Songs in the System: "); SongHandler.DisplaySongs();}
                case 7 -> {
                    System.out.println("Delete Song");
                    String Pname = Playlists.next_line("Enter the name of the Playlist:  ");
                    pls.deleteSongFromPlaylist(Pname);
                }
                case 8 -> pls.undoDeletePlaylist();
                case 0 -> {System.out.println("Byee!");
                    TimeUnit.SECONDS.sleep(2);
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice, Choose Wisely!");
            }
        }while(choose!=0);
    }
}
