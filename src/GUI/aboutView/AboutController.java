package GUI.aboutView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController
{
    @FXML
    Button btnClose;

    public void close(ActionEvent actionEvent) {

        doClose();
    }

    private void doClose()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
