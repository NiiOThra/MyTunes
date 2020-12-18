package GUI.deleteSongView;

import BE.Song;
import BLL.SongManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteSongController
{
    @FXML
    Button btnYes;

    @FXML
    Button btnNo;

    private Song selectedSong;

    private SongManager sM;
    private GUI.Controller mainController;

    public void setManager(SongManager sM) {
        this.sM = sM;
    }
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setSelectedSong(Song song) {
        this.selectedSong = song;
    }

    public void doDelete(ActionEvent actionEvent) {
        try {

            sM.deleteSongByID(selectedSong.getId());
            doClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent)
    {
        doClose();
    }

    private void doClose()
    {
        mainController.reloadSongTable();
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }
}
