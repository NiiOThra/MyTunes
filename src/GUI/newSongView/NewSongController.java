package GUI.newSongView;

import BE.Song;
import BLL.SongManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;


public class NewSongController extends Component {
    private SongManager sM;


    @FXML
    public Button cancelBttn;
    @FXML
    TextField txtFile;
    @FXML
    TextField txtTitle;
    @FXML
    TextField txtArtist;
    @FXML
    TextField txtTime;
    @FXML
    TextField txtCategory;


    private GUI.Controller mainController;

    private SongManager songManager;


    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setManager(SongManager sM) {
        this.songManager = sM;
    }

    public void choose(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Sound files (*.wav , *.mp3)", "*.wav", "*.mp3");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            txtFile.setText(selectedFile.getAbsolutePath());
        }
    }
    public void save() {
        try {
            Song sentSong = songManager.addSong(new Song(txtTitle.getText(), txtArtist.getText(), txtCategory.getText(), txtFile.getText(), Integer.parseInt(txtTime.getText())));
            if(sentSong.getId() != -1)
            {
                doClose();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(ActionEvent actionEvent) {
        doClose();
    }

    private void doClose()
    {
        mainController.reloadSongTable();
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }
}
