package stratozavr;

import com.imperva.ddc.core.query.ConnectionResponse;
import com.imperva.ddc.core.query.Endpoint;
import com.imperva.ddc.service.DirectoryConnectorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.graalvm.compiler.lir.LIRInstruction;

public class Controller {

    @FXML private Button btn;
    @FXML private TextField serverIP;
    @FXML private TextField loginName;
    @FXML private TextField loginPass;
    @FXML private ImageView icon1;
    @FXML private TableView<UserAccount> table;
    @FXML private TableColumn<UserAccount, String> fullRussNameCol;
    @FXML private TableColumn<UserAccount, String> firstNameCol;
    @FXML private TableColumn<UserAccount, String> middleNameCol;
    @FXML private TableColumn<UserAccount, String> lastNameCol;
    @FXML private TableColumn<UserAccount, String> sAMAccountNameCol;
    @FXML private TableColumn<UserAccount, String> resultImgCol;
    @FXML private TextField newUser;
    @FXML private Button addButton;
    @FXML private Button deleteButton;


    private Main main;

    public Controller() {

    }

    @FXML private void initialize() {
        table.setEditable(true);
        System.out.println(table.isEditable());
        fullRussNameCol.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("fullRussName"));
        fullRussNameCol.setCellFactory(TextFieldTableCell.<UserAccount> forTableColumn());
        fullRussNameCol.setMinWidth(200);
        fullRussNameCol.setOnEditCommit((TableColumn.CellEditEvent<UserAccount, String> event) -> {
            TablePosition<UserAccount, String> pos = event.getTablePosition();
            String newFullRussName = event.getNewValue();
            int row = pos.getRow();
            UserAccount userAccount = event.getTableView().getItems().get(row);
            userAccount.resetUserAccount(newFullRussName);
            table.refresh();
        });
        lastNameCol.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("firstName"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("middleName"));
        sAMAccountNameCol.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("sAMAccountName"));
        resultImgCol.setCellValueFactory(new PropertyValueFactory<>("resultImg"));
        resultImgCol.setStyle("-fx-alignment: CENTER;");
    }

    public void setMain(Main main) {
        this.main = main;

        table.setItems(this.main.getPersonData());
    }

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
    @FXML private void clickAddButton(ActionEvent event) {
        if (!newUser.getText().equals("")) {
            main.getPersonData().add(new UserAccount(newUser.getText()));
            newUser.setText("");
        }
    }
    @FXML private void clickDeleteButton(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(selectedIndex);
    }

}
