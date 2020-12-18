package GUI.newPlaylistView;

import BE.Playlist;
import BLL.SongManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewPlaylistController {

    private SongManager sM;

    @FXML
    public Button cancelBttn;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtPlaylistName;

    private GUI.Controller mainController;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setManager(SongManager sM) {
        this.sM = sM;
    }

    public void savePlaylist(ActionEvent actionEvent)
    {
        try {
            sM.addPlaylist(new Playlist(txtPlaylistName.getText().toString()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        doClose();
    }

    private void doClose()
    {
        mainController.reloadPlaylistsTable();
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    public void close(ActionEvent actionEvent) {
        doClose();
    }

}
