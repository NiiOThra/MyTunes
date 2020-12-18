package BE;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String category;
    private Integer duration;
    private String filePath;
    private String timeString;



    public Song(int id, Song song)
    {
        this( id,
                song.getTitle(),
                song.getArtist(),
                song.getCategory(),
                song.getPath(),
                song.getDuration() );
    }

    /**
     * Constructor for the Song class. This constructor is used when adding a
     * new song to the database.
     *
     * @param title Title of the song.
     * @param artist Artist of the song.
     * @param category Category of the song.
     * @param filename Filename of the song.
     * @param durationInSeconds Duration of the song.
     */
    public Song( String title, String artist, String category, String filename, int durationInSeconds ) {
        this( -1, title, artist, category, filename, durationInSeconds );
        this.timeString = "" +durationInSeconds;
    }

    /**
     * Constructor for the Song class. This constructor is never really used,
     * since the other two cover most situations.
     *
     * @param id ID of the song in the database.
     * @param title Title of the song.
     * @param artist Artist of the song.
     * @param category Category of the song.
     * @param filepath Filename of the song.
     * @param durationInSeconds Duration of the song.
     */
    public Song( int id, String title, String artist, String category, String filepath,
                 int durationInSeconds ) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.filePath = filepath;
        this.duration = durationInSeconds;
        this.timeString = "" +durationInSeconds;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleProperty() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getArtistProperty() {
        return artist;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryProperty() {
        return category;
    }

    public Integer getTimeProperty() {
        return duration;
    }

    public String getPath() {
        return filePath;
    }

    public void setPath(String path) {
        this.filePath = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist =artist;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() { return this.duration; }
    public String getDurationString() { return this.timeString; }
    /**
     * New toString method for Track, used for formatting the view in the ListView in GUI
     * @return "{Track Title} - {Track Artist}
     */
    @Override
    public String toString() {
        return this.getTitle() + " - " + this.getArtist();
    }
}
