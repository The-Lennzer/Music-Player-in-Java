import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player
{
    Long currentFrame;// to store current position
    Clip clip;
    String status;// current status of clip
    AudioInputStream audioInputStream;
    static String filePath;
    public Player() throws UnsupportedAudioFileException, IOException, LineUnavailableException // constructor to initialize streams and clip
    {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();// create clip reference
        clip.open(audioInputStream);// open audioInputStream to the clip
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void PlaySongs() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        if(!Playlists.songQueue.isEmpty()) {
        try {
            filePath = Playlists.songQueue.peek();//gets the current song to be played from the Queue
            System.out.println("Now Playing: "+ filePath.substring(6, filePath.indexOf(".")));
            Playlists.songQueue.remove();//Updates the queue
            Player audioPlayer = new Player();
            audioPlayer.play();//starts the player
            Scanner sc = new Scanner(System.in);

            //MENU
            while (true) {
                System.out.println("    1. Pause");
                System.out.println("    2. Resume");
                System.out.println("    3. Restart");
                System.out.println("    4. Stop");
                System.out.println("    5. Add song to Queue");
                System.out.println("    6. Next");
                int c = sc.nextInt();
                audioPlayer.gotoChoice(c);
                if (c == 4)
                    break;
            }
            sc.close();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    else{
        System.out.println("Song Queue has ended or is empty!");
    }
    }
    //function for menu
    private void gotoChoice(int c)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        switch (c) {
            case 1 -> pause();
            case 2 -> resumeAudio();
            case 3 -> restart();
            case 4 -> {stop();
                System.out.println("Stopping playlist...\n");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Player STOP");
                TimeUnit.SECONDS.sleep(1);
                StartInterface.First();//returns to homepage
            }
            case 5 -> {System.out.println("Add songs to playlist");
                      addSongsToQueue();}
            case 6 -> next();
        }
    }
    // Method to play the audio
    public void play() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        clip.start();
        status = "play";
    }

    // Method to pause the audio
    public void pause()
    {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        if (status.equals("play")) {
            System.out.println("Audio is already being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }
    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }
    public void addSongsToQueue(){
        String songName =  Playlists.next_line("Enter the name of the song you want to add: ");
        SongHandler.SongCollection.printList();
        if(!SongHandler.SongCollection.search(songName+".wav")){
            System.out.println("Song doesn't exist!");
        }
        else{
            Playlists.songQueue.add("Songs/"+songName+".wav");
            System.out.println("Song added to Queue!");
        }
    }
    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    // Method to jump over a specific part
    public void next() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        stop();
        Player.PlaySongs();
    }
    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}