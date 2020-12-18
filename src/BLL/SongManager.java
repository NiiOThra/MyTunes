package BLL;

import BE.Playlist;
import BE.Song;
import DAL.DBManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public class SongManager {

    private DBManager dbMgr = new DBManager();
    private static SongManager instance = null;

    /**
     * Get-method for retrieving the MusicManager instance.
     *
     * @throws NullPointerException Thrown if the MusicManager is not
     *         initialized via the <i>init()</i> method.
     * @return The instance of the MusicManager
     */
    public static SongManager getInstance(){
        if( instance == null ){
            return new SongManager();
        }else{
            return instance;
        }
    }
    /**
     * Gets a list of all songs in the database
     *
     * @return a list of all songs
     * @throws SQLException
     */
    public List<Song> getAllSongs() throws SQLException{
        return dbMgr.getAllSongs();
    }
    /**
     * Adds a song to the database - returns the song
     *
     * @param song the song you want to add
     * @return the song added
     * @throws SQLException
     */
    public Song addSong( Song song ) throws SQLException {
        return dbMgr.addSong( song );
    }
    /**
     * Deletes a song in the database, based on the id
     *
     * @param songID the id of the song
     * @throws SQLException
     */
    public void deleteSongByID( int songID ) throws SQLException{
        dbMgr.deleteSongByID( songID );
    }
    /**
     * Updates a song in the database
     *
     * @param song the song you want to update
     * @throws SQLException
     */
    public void updateSong( Song song ) throws SQLException{
        dbMgr.updateSong( song );
    }

    /**
     * Gets a list of all playlists in the database
     *
     * @return a list of all playlists
     * @throws SQLException
     */
    public List<Playlist> getAllPlaylists() throws SQLException{
        return dbMgr.getAllPlaylists();
    }
    /**
     * Adds a playlist to the database - returns the playlist
     *
     * @param playlist the playlist you want to add
     * @return the playlist added
     * @throws SQLException
     */
    public Playlist addPlaylist( Playlist playlist ) throws SQLException{
        return dbMgr.addPlaylist( playlist );
    }
    /**
     * Updates a playlist in the database
     *
     * @param playlist the playlist you want to update
     * @throws SQLException
     */
    public void updatePlaylist( Playlist playlist ) throws SQLException{
        dbMgr.updatePlaylist( playlist );
    }
    /**
     * returns a playlist based on the id of the playlist
     *
     * @param id the ID of the playlist
     * @return the playlist corresponding with the ID
     * @throws SQLException
     */
    public Playlist getPlaylistByID( int id ) throws SQLException{
        return dbMgr.getPlaylistByID( id );
    }
    /**
     * Deletes a playlist in the database, based on the id
     *
     * @param playlistID the id of the playlist
     * @throws SQLException
     */
    public void deletePlaylistByID( int playlistID ) throws SQLException{
        dbMgr.deletePlaylistByID( playlistID );
    }
    public void addSongToPlaylist(Song song, Playlist playlist) throws SQLException {
        dbMgr.addSongToPlaylist(song, playlist);
    }

    public void deleteSongFromPlaylist( int songID, int playlistID ) throws SQLServerException, SQLException{
        dbMgr.deleteSongFromPlaylist( songID, playlistID );
    }
}

