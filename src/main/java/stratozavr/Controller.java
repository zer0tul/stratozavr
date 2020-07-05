package stratozavr;

import com.imperva.ddc.core.query.ConnectionResponse;
import com.imperva.ddc.core.query.Endpoint;
import com.imperva.ddc.service.DirectoryConnectorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML private Button btn;
    @FXML private TextField serverIP;
    @FXML private TextField loginName;
    @FXML private TextField loginPass;
    @FXML private ImageView icon1;



    @FXML
    private void click(ActionEvent event) {
        Endpoint endpoint = Connection.createEndpoint(serverIP.getText(),389,loginName.getText(),loginPass.getText(),false);
        ConnectionResponse connectionResponse = DirectoryConnectorService.authenticate(endpoint);
        boolean succeeded = !connectionResponse.isError();
        if (succeeded) {
            Image image = new Image("/icon.png");
            icon1.setImage(image);
            icon1.setVisible(true);
        }

        else {
            Image image = new Image("/icon1.png");
            icon1.setImage(image);
            icon1.setVisible(true);
        }
    }
}
