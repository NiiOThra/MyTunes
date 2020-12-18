package DAL;

import BE.Playlist;
import BE.Song;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private SQLServerDataSource dataSource;
    private static DBManager instance = null;


    public DBManager()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MyTunesFMMM");
        dataSource.setUser("CSe20A_21");
        dataSource.setPassword("CSe20A_21");
        dataSource.setPortNumber(1433);
    }



    /**
     * Get-method for retrieving the DBManager instance.
     *
     * @throws NullPointerException Thrown if the DBManager is not
     *         initialized via the <i>init()</i> method.
     * @return The instance of the DBManager
     */
    public static DBManager getInstance(){
        if( instance == null ){
            throw new NullPointerException( "DBManager not yet initialized!" );
        }else{
            return instance;
        }
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * Returns all songs in a list
     *
     * @return a list of all songs
     * @throws SQLException
     */
    public List<Song> getAllSongs() throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM Song" );

            List<Song> songs = new ArrayList<>();
            while( rs.next() ){
                songs.add( getOneSong( rs ) );
            }
            return songs;
        }
    }

    /**
     * Returns a song based on the ID
     *
     * @param songID the id of the song
     * @return the given song
     * @throws SQLException
     */
    public Song getSongByID( int songID ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM Song WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, songID );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                return getOneSong( rs );
            }
        }
        return null;
    }
    /**
     * Adds a song to the database
     *
     * @param song the song you want to add
     * @return the song added
     * @throws SQLException
     */
    public Song addSong( Song song ) throws SQLException {
        try( Connection con = dataSource.getConnection() ){
            String sql = "INSERT INTO Song (Title, Artist, Category, Duration, FilePath) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
            ps.setString( 1, song.getTitle() );
            ps.setString( 2, song.getArtist() );
            ps.setString( 3, song.getCategory() );
            ps.setInt( 4, song.getDuration() );
            ps.setString( 5, song.getPath() );

            int id = executeAndGetID( ps );

            return new Song(id, song);
        }
    }
    /**
     * Delets a song based on the id - both from the "song" table and the
     * "playlistsong" table
     *
     * @param songID the id of the song
     * @throws SQLException
     */
    public void deleteSongByID( int songID ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "DELETE FROM PlayListSong WHERE SongID = ?;";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, songID );
            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
               }

            String sql2 = "DELETE FROM Song WHERE Id = ?";
            PreparedStatement ps1 = con.prepareStatement( sql2 );
            ps1.setInt( 1, songID );
            int affectedRows1 = ps1.executeUpdate();
            if( affectedRows1 == 0 ){
                throw new SQLException( "Unable to delete Song" );
            }
        }
    }
    /**
     * Updates a song in the database
     *
     * @param s the song you want to update
     * @throws SQLException
     */
    public void updateSong( Song s ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "UPDATE Song SET Title = ?, Artist = ?, Category = ?, FilePath = ?, Duration = ? WHERE Id = ?";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setString( 1, s.getTitle() );
            ps.setString( 2, s.getArtist() );
            ps.setString( 3, s.getCategory() );
            ps.setString( 4, s.getPath() );
            ps.setInt( 5, s.getDuration() );
            ps.setInt( 6, s.getId() );

            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to update song" );
            }
        }
    }

    /**
     * Updates information in a playlist
     *
     * @param p the playlist you want to update
     * @throws SQLException
     */
    public void updatePlaylist( Playlist p ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "UPDATE Playlist SET Name = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setString( 1, p.getName() );
            ps.setInt( 2, p.getId() );

            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to update playlist" );
            }
        }
    }


    /**
     * Returns all playlists in a list
     *
     * @return a list of all artists
     * @throws SQLException
     */
    public List<Playlist> getAllPlaylists() throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM Playlist" );

            List<Playlist> playlists = new ArrayList<>();
            while( rs.next() ){
                playlists.add( getOnePlaylist( rs ) );
            }
            return playlists;
        }
    }

    /**
     * @param rs the resultset from a SQL query
     * @return a playlist object
     * @throws SQLException
     */
    private Playlist getOnePlaylist( ResultSet rs ) throws SQLException{
        int id = rs.getInt( 1 );
        String name = rs.getString( 2 );
        List<Song> songs = getAllSongsFromPlaylist(id);
        return new Playlist( id, name, songs );
    }

    /**
     * Returns a playlist from the database, based on the ID
     *
     * @param playlistId the id of the playlist
     * @return the Playlist
     * @throws SQLException
     */
    public Playlist getPlaylistByID( int playlistId ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM Playlist WHERE id = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, playlistId );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                int id = rs.getInt( 1 );
                String name = rs.getString( 2 );
                return new Playlist( id, name );
            }
        }
        return null;
    }
    /**
     * Adds a playlist to the database
     *
     * @param playlist the playlist you want to add
     * @return the playlist you added
     * @throws SQLException
     */
    public Playlist addPlaylist( Playlist playlist ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "INSERT INTO Playlist (Name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
            ps.setString( 1, playlist.getName() );

            int id = executeAndGetID( ps );

            return new Playlist( id, playlist.getName() );
        }
    }

    /**
     * Deletes a playlist in the database based on the ID to make sure that
     * there can be only one
     *
     * @param playlistID the ID of the playlist
     * @throws SQLException if the playlistID does not exist
     */
    public void deletePlaylistByID( int playlistID ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "DELETE FROM PlayListSong WHERE PlayListID = ?;";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, playlistID );
            ps.executeUpdate();




            String sql1 = "DELETE FROM Playlist WHERE Id = ?";
            PreparedStatement ps1 = con.prepareStatement( sql1 );
            ps1.setInt( 1, playlistID );
            int affectedRows1 = ps1.executeUpdate();
            if( affectedRows1 == 0 ){
                throw new SQLException( "Unable to delete Playlist" );
            }
        }
    }

    /**
     * Remove a song from a playlist
     *
     * @param songID the id of the song
     * @param playlistID the id of the playlist
     * @throws SQLServerException
     * @throws SQLException
     */
    public void deleteSongFromPlaylist( int songID, int playlistID ) throws SQLServerException, SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "DELETE FROM PlayListSong WHERE PlayListID = ? AND SongID = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, playlistID );
            ps.setInt( 2, songID );
            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to delete song from playlist" );
            }
        }
    }
    /**
     * Adds a song to a playlist
     *
     * @param song the song you want to add
     * @param playlist the playlist the song is added to
     * @throws SQLException
     */
    public void addSongToPlaylist( Song song, Playlist playlist ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "INSERT INTO PlayListSong(PlayListID, SongID) VALUES (?, ?);";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, playlist.getId() );
            ps.setInt( 2, song.getId() );

            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to add song to playlist." );
            }
        }
    }

    /**
     * Returns the songs of a playlist in a list
     *
     * @param playlistID the id of the list
     * @return a list of songs linked to the same playlist
     * @throws SQLException
     */
    public List<Song> getAllSongsFromPlaylist(int playlistID ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM PlaylistSong WHERE PlaylistSong.PlaylistId = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, playlistID );

            ResultSet rs = ps.executeQuery();

            List<Song> songs = new ArrayList<Song>();
            while( rs.next() ){
                songs.add(getSongByID(rs.getInt(3)) );
            }
            return songs;
        }
    }
    /**
     * Creates ONE song based on a resultset
     *
     * @param rs the resultset from a SQL query
     * @return an song object
     * @throws SQLException
     */
    private Song getOneSong( ResultSet rs ) throws SQLException{
        int id = rs.getInt( 1 );
        String title = rs.getString( 2 );
        String artist = rs.getString( 3 );
        String category = rs.getString( 4 );
        int duration = rs.getInt( 5 );
        String filename = rs.getString( 6 );

        return new Song( id, title, artist, category, filename, duration );
    }

    /**
     * Executes a command to the sql-server and returns the generated keys
     *
     * @param ps
     * @return the key (ID) of the row created
     * @throws SQLException
     */
    private int executeAndGetID( PreparedStatement ps ) throws SQLException{
        int affectedRows = ps.executeUpdate();
        if( affectedRows == 0 ){
            throw new SQLException( "Unable to add element to database" );
        }
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();
        return keys.getInt( 1 );
    }
}
