package GUI;

import BE.Playlist;
import BE.Song;
import BLL.SongManager;
import GUI.aboutView.AboutController;
import GUI.deletePlaylistView.DeletePlaylistController;
import GUI.deleteSongView.DeleteSongController;
import GUI.editPlaylistView.EditPlaylistController;
import GUI.editSongView.EditSongController;
import GUI.newPlaylistView.NewPlaylistController;
import GUI.newSongView.NewSongController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import util.MusicPlayer;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {

    @FXML
    public Button closeBttn;
    @FXML
    private Slider volumeSlider;
    @FXML
    private TextField txtFilter;
    @FXML
    private Text lblSongPlaying;
    @FXML
    private TableView playlistTable;
    @FXML
    private TableColumn<Playlist, String> playlistNameColumn;
    @FXML
    private TableColumn<Playlist, String> playlistAmountOfSongsColumn;
    @FXML
    private TableColumn<Playlist, String> playlistTimeColumn;
    @FXML
    private TableView songsOnPlaylistTable;
    @FXML
    private TableColumn<Song, String> playlistSongsColumn;
    @FXML
    private TableView songsTable;
    @FXML
    private TableColumn< Song,String> songTableTitleColumn;
    @FXML
    private TableColumn<Song, String> songTableArtistColumn;
    @FXML
    private TableColumn<Song, String> songTableCategoryColumn;
    @FXML
    private TableColumn<Song, String> songTableTimeColumn;



    private Song selectedSong;
    private Song selectedSongOnPlayList;
    private Playlist selectedPlaylist;
    private Stage windowStage = new Stage();
    private boolean playing = false;
    private boolean isMaximized = false;
    private boolean autoPlay=false;
    private static final SongManager  sM = SongManager.getInstance();
    private MusicPlayer musicPlayer = new MusicPlayer();
    private ObservableList<Song> SongList;
    private ObservableList<Playlist> Playlists;
    private boolean isPlayingFromPlaylist = false;

    public Controller() {

    }

    public void volumeScroll()
    {
        musicPlayer.setVolume(getVolumePercentage());
    }

    public void dialogNewSong() {
        startNewSongView("newSongView/NewSongView.fxml");
    }

    public void dialogEditSong() {
        if (selectedSong != null) {
            startEditSongView("editSongView/EditSongView.fxml");
        }
    }

    public void dialogEditPlaylist() {
        startEditPlaylistView("editPlaylistView/EditPlaylistView.fxml");
    }
    public void dialogDeletePlaylist() {
        startDeletePlaylistView("deletePlaylistView/DeletePlaylistView.fxml");
    }

    public void dialogDeleteSong() {
        startDeleteSongView("deleteSongView/DeleteSongView.fxml");
    }

    public void dialogRemoveSongFromPlaylistSong() {
        try {
            sM.deleteSongFromPlaylist(this.selectedSongOnPlayList.getId(),this.selectedPlaylist.getId());
            reloadPlaylistsTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dialogNewPlaylist() {
        startNewPlaylistView("newPlaylistView/NewPlaylistView.fxml");
    }




    public void aboutClicked()
    {startAboutView("aboutView/AboutView.fxml");

    }
    public void startAboutView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            AboutController aboutController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startNewSongView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewSongController newSongController = loader.getController();
            newSongController.setMainController(this);
            newSongController.setManager(this.sM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startDeleteSongView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            DeleteSongController deleteSongController = loader.getController();
            deleteSongController.setMainController(this);
            deleteSongController.setManager(this.sM);
            deleteSongController.setSelectedSong(this.selectedSong);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startEditSongView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            EditSongController editSongController = loader.getController();
            editSongController.setSelectedSong(this.selectedSong);
            editSongController.setMainController(this);
            editSongController.setManager(this.sM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startNewPlaylistView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewPlaylistController newPlaylistController = loader.getController();
            newPlaylistController.setMainController(this);
            newPlaylistController.setManager(this.sM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startEditPlaylistView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            EditPlaylistController editPlaylistController = loader.getController();
            editPlaylistController.setSelectedPlaylist(this.selectedPlaylist);
            editPlaylistController.setMainController(this);
            editPlaylistController.setManager(this.sM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startDeletePlaylistView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            DeletePlaylistController deletePlaylistController = loader.getController();
            deletePlaylistController.setMainController(this);
            deletePlaylistController.setManager(this.sM);
            deletePlaylistController.setSelectedPlaylist(this.selectedPlaylist);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double getVolumePercentage() {
        return volumeSlider.getValue() / 100;
    }

    private void changeSong()
    {
        playSongButton();
        playSongButton();
    }

    public void previousButton() {
        if (selectedSongOnPlayList != null) {
            if (this.songsOnPlaylistTable.getSelectionModel().getFocusedIndex() != 0)
                this.songsOnPlaylistTable.getSelectionModel().selectAboveCell();
            else
                this.songsOnPlaylistTable.getSelectionModel().selectLast();

            selectedSongOnPlayList = (Song)this.songsOnPlaylistTable.getSelectionModel().getSelectedItem();
            changeSong();
        }
        if (selectedSong != null) {
            if (this.songsTable.getSelectionModel().getFocusedIndex() != 0)
                this.songsTable.getSelectionModel().selectAboveCell();
            else
                this.songsTable.getSelectionModel().selectLast();

            selectedSong = (Song)this.songsTable.getSelectionModel().getSelectedItem();
            changeSong();
        }
    }

    public void nextButton() {
        if (selectedSongOnPlayList != null) {
            if (this.songsOnPlaylistTable.getSelectionModel().getFocusedIndex() != this.songsOnPlaylistTable.getItems().size() - 1)
                this.songsOnPlaylistTable.getSelectionModel().selectBelowCell();
            else
                this.songsOnPlaylistTable.getSelectionModel().selectFirst();

            selectedSongOnPlayList = (Song)this.songsOnPlaylistTable.getSelectionModel().getSelectedItem();
            changeSong();
        }
        if (selectedSong != null) {
            if (this.songsTable.getSelectionModel().getFocusedIndex() != this.songsTable.getItems().size() - 1)
                this.songsTable.getSelectionModel().selectBelowCell();
            else
                this.songsTable.getSelectionModel().selectFirst();

            selectedSong = (Song)this.songsTable.getSelectionModel().getSelectedItem();
            changeSong();
        }
    }

    private void showSongPlaying(Song song)
    {
        lblSongPlaying.setText(song.toString() + " is playing.");
    }

    public void playSongButton() {

        if (selectedSongOnPlayList != null && !playing && isPlayingFromPlaylist) {
            musicPlayer.setSong(selectedSongOnPlayList);
            musicPlayer.setVolume(getVolumePercentage());
            musicPlayer.play();
            playing = !playing;
            showSongPlaying(selectedSongOnPlayList);
        } else if (selectedSong != null && !playing && !isPlayingFromPlaylist) {
            musicPlayer.setSong(selectedSong);
            musicPlayer.setVolume(getVolumePercentage());
            musicPlayer.play();
            playing = !playing;
            showSongPlaying(selectedSong);
        } else if (selectedSong != null || selectedSongOnPlayList != null) {
            musicPlayer.pause();
            playing = !playing;
        }
        musicPlayer.getMediaPlayer().setOnEndOfMedia( () ->{
            if(selectedSongOnPlayList!=null && playing){
                if(!autoPlay)
                    nextButton();
                playing=!playing;
                musicPlayer.stop();
                playSongButton();
                return;
            }
            if(selectedSong!=null && playing){
                if(!autoPlay)
                    nextButton();
                playing=!playing;
                musicPlayer.stop();
                playSongButton();
                return;
            }
        });
    }

    public void setSelectedSongFromPlaylist()
    {
        Song s = (Song)this.songsOnPlaylistTable.getSelectionModel().getSelectedItem();

        if(s != null)
        {
            selectedSongOnPlayList = s;
            isPlayingFromPlaylist = true;
        }
    }

    public void displayPlaylist(Playlist playList)
    {
        this.songsOnPlaylistTable.setItems(FXCollections.observableList(selectedPlaylist.getSongs()));

        playlistSongsColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        if(songsOnPlaylistTable.getItems().size() > 0)
        {
            songsOnPlaylistTable.getSelectionModel().select(0);
            selectedSongOnPlayList = (Song)songsOnPlaylistTable.getSelectionModel().getSelectedItem();
        }
    }

    public void reloadPlaylistsTable() {
        try {
            Playlists = FXCollections.observableList(sM.getAllPlaylists());

            int index = playlistTable.getSelectionModel().getFocusedIndex();
            this.playlistTable.setItems(Playlists);

            playlistNameColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            playlistAmountOfSongsColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getSongsCount()));
            playlistTimeColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getTotalTime()));

            playlistTable.getSelectionModel().select(0);
            selectedPlaylist = (Playlist) playlistTable.getSelectionModel().getSelectedItem();
            displayPlaylist(selectedPlaylist);
        }
        catch (Exception exception) {
            System.out.println("No playlists loaded");
        }
    }

    public void reloadSongTable() {
        try {
            SongList = FXCollections.observableList(sM.getAllSongs());

            int index = songsTable.getSelectionModel().getFocusedIndex();
            this.songsTable.setItems(SongList);

            songTableTitleColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            songTableArtistColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getArtist()));
            songTableTimeColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getDurationString()));
            songTableCategoryColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

            songsTable.getSelectionModel().select(index);
        } catch (Exception exception) {
            System.out.println("No songs loaded");
        }
    }

    public void tblSongsClicked()
    {
        Song s = (Song)songsTable.getSelectionModel().getSelectedItem();
        if(s != null)
        {
            selectedSong = s;
            isPlayingFromPlaylist = false;
        }
    }

    public void tblPlaylistsClicked()
    {
        selectedPlaylist = (Playlist)playlistTable.getSelectionModel().getSelectedItem();
        displayPlaylist(selectedPlaylist);
    }

    public void addSongToPlaylist()
    {
        try {
            if(selectedPlaylist != null && selectedSong != null){
                sM.addSongToPlaylist(selectedSong, selectedPlaylist);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        refreshUI();
    }

    public void filterSongs()
    {
        this.songsTable.setItems(SongList.filtered(x -> x.getTitle().contains(txtFilter.getText())));
    }



    public void refreshUI()
    {
        reloadSongTable();
        reloadPlaylistsTable();
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBttn.getScene().getWindow();
        stage.close();
    }
}



