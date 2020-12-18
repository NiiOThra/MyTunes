package GUI.editPlaylistView;

import BE.Playlist;
import BLL.SongManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditPlaylistController {
    @FXML
    public Button cancelBttn;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtPlaylistName;

    private Playlist selectedPlaylist;

    private SongManager sM;

    private GUI.Controller mainController;

    public void setSelectedPlaylist(Playlist pl) {
        this.selectedPlaylist = pl;
        txtPlaylistName.setText(pl.getName());
    }

    public void setManager(SongManager sM) {
        this.sM = sM;
    }

    public void save(ActionEvent actionEvent) {

        try {
            if(selectedPlaylist != null)
            {
                selectedPlaylist.name = txtPlaylistName.getText();
                sM.updatePlaylist(selectedPlaylist);
            }
            doClose();

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public void cancelEdit(ActionEvent actionEvent)
    {
        doClose();
    }

    private void doClose()
    {
        mainController.reloadPlaylistsTable();
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }
}

