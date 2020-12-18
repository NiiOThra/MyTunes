package BE;

import java.util.ArrayList;
import java.util.List;


public class Playlist {

    /* Instance variables */
    public int id;
    public String name;
    public List<Song> songs = new ArrayList<>();

    /**
     * Constructor for the playlist class. This constructor is used when pushing
     * playlist information to the database.
     *
     * @param name Name of the playlist.
     */
    public Playlist( String name ) {
        this( -1, name );
    }

    /**
     * Constructor for the playlist class. This constructor is used when pulling
     * playlist information from the database.
     *
     * @param id ID of the playlist in the database.
     * @param name Name of the playlist.
     */
    public Playlist( int id, String name ) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor for the playlist class. This constructor is used when pulling
     * playlist information from the database.
     *
     * @param id ID of the playlist in the database.
     * @param name Name of the playlist.
     * @param songs Songs on the playlist.
     */
    public Playlist( int id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }
    /**
     * Add a song to the playlist.
     *
     * @param song Song to add.
     */
    public void addSong( Song song ){
        songs.add( song );
    }

    /**
     * Remove a song from the playlist.
     *
     * @param song Song to remove.
     */
    public void removeSong( Song song ){
        songs.remove( song );
    }

    /**
     * Get-method for retrieving the name of the playlist.
     *
     * @return Name of the playlist as a String.
     */
    public String getName(){
        return name;
    }

    /**
     * Set-method for setting the name of the playlist.
     *
     * @param name New name of the playlist.
     */
    public void setName( String name ){
        this.name = name;
    }

    /**
     * Get-method for retrieving the ID of the playlist.
     *
     * @return ID of the playlist as an int.
     */
    public int getId(){
        return id;
    }

    /**
     * Get-method for retrieving all the songs in this playlist.
     *
     * @return All the songs in the playlist as a List of Songs.
     */
    public List<Song> getSongs(){
        return songs;
    }

    @Override
    public String toString(){
        return String.format( "%4d %15s", getId(), getName() );
    }

    public String getSongsCount() {
        return ""+songs.size();
    }

    public String getTotalTime()
    {
        int total = 0;
        for (Song s : songs) {
            total = total + s.getDuration();
        }
        return ""+ total;
    }
}
