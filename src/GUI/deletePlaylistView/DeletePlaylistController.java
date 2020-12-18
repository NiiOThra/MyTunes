package GUI.deletePlaylistView;

import BE.Playlist;
import BLL.SongManager;
import GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeletePlaylistController {

    @FXML
    Button btnYes;

    @FXML
    Button btnNo;



    private Playlist selectedPlaylist;

    private SongManager sM;
    private GUI.Controller mainController;


    public void setManager(SongManager sM) {
        this.sM = sM;
    }
    public void setSelectedPlaylist(Playlist selectedPlaylist) {

        this.selectedPlaylist = selectedPlaylist;
    }

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void close(ActionEvent actionEvent) {

        doClose();
    }

    public void doDelete(ActionEvent actionEvent) {
        try {

            sM.deletePlaylistByID(selectedPlaylist.id);
            doClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doClose()
    {
        mainController.reloadPlaylistsTable();
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }

}
