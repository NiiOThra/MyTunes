package GUI.editSongView;

import BE.Song;
import BLL.SongManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditSongController {

    @FXML
    public Button cancelBttn;
    @FXML
    public TextField txtSongTitle;
    @FXML
    public TextField txtSongArtist;
    @FXML
    public TextField txtSongCategory;
    @FXML
    public TextField txtSongTime;
    @FXML
    public TextField txtSongFile;

    private Song selectedSong;
    private SongManager sM;

    private GUI.Controller mainController;

    public void setManager(SongManager sM) {
        this.sM = sM;
    }

    public void setSelectedSong(Song s) {
        this.selectedSong = s;
        txtSongTitle.setText(s.getTitle());
        txtSongArtist.setText(s.getArtist());
        txtSongCategory.setText(s.getCategory());
        txtSongTime.setText(s.getDurationString());
        txtSongFile.setText(s.getPath());
    }

    public void close(ActionEvent actionEvent) {

        doClose();
    }

    public void save() {
        try {
            if (selectedSong != null) {
                selectedSong.setTitle(txtSongTitle.getText());
                selectedSong.setPath(txtSongFile.getText());
                selectedSong.setArtist(txtSongArtist.getText());
                selectedSong.setCategory(txtSongCategory.getText());
                selectedSong.setDuration(Integer.parseInt(txtSongTime.getText()));
                sM.updateSong(selectedSong);
                doClose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void doClose()
    {
        mainController.reloadSongTable();
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }
}
