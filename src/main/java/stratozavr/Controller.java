package stratozavr;

import com.imperva.ddc.core.query.ConnectionResponse;
import com.imperva.ddc.core.query.Endpoint;
import com.imperva.ddc.core.query.FieldType;
import com.imperva.ddc.core.query.ObjectType;
import com.imperva.ddc.service.DirectoryConnectorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import org.graalvm.compiler.lir.LIRInstruction;

import javax.naming.ldap.LdapContext;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.imperva.ddc.service.DirectoryConnectorService.resolveDistinguishedName;

public class Controller {

    @FXML private Button btn;
    @FXML private TextField serverIP;
    @FXML private TextField loginName;
    @FXML private PasswordField loginPass;
    @FXML private TextField domainName;
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
    @FXML private Button loadFromDekanatButton;
    @FXML private Label loadCount;
    private String sqlInstanceName;
    private String sqlDataBaseName;
    private String sqlUsername;
    private String sqlUserPass;


    private Main main;

    public Controller() {

    }

    @FXML private void initialize() {
        table.setEditable(true);
        //System.out.println(table.isEditable());
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
        loadJSONSettings();
    }

    public void setMain(Main main) {
        this.main = main;

        table.setItems(this.main.getPersonData());
    }

    @FXML
    private void click(ActionEvent event) {
        Endpoint endpoint = Connection.createEndpoint(serverIP.getText(),389,loginName.getText() + "@" + domainName.getText(),loginPass.getText(),false);
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
        //String DN = resolveDistinguishedName("Administrator", FieldType.LOGON_NAME, ObjectType.USER, endpoint);
        //System.out.println(DN);
        //System.out.println(Connection.getEndpoint());


        try{
            LdapContext ctx = ActiveDirectoryNew.getConnection(loginName.getText(), loginPass.getText(), domainName.getText(), serverIP.getText());
            ActiveDirectoryNew.User user = ActiveDirectoryNew.getUser("a.a.abramovich", ctx);

           // ctx.close();
            //System.out.println(ActiveDirectoryNew.getContext());
            System.out.println(user);
        }
        catch(Exception e){
            //Failed to authenticate user!
            e.printStackTrace();
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

    @FXML private void clickLoadFromDekanatButton(ActionEvent event) {
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;user=%3$s;password=%4$s;";
        String connectionString = String.format(connectionUrl, this.sqlInstanceName, this.sqlDataBaseName, this.sqlUsername, this.sqlUserPass);
        try {
            // Подключение к базе данных
            java.sql.Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT TOP 4000 Код, Фамилия, Имя, Отчество, Статус, Год_Поступления FROM Деканат.dbo.Все_Студенты WHERE Статус=4");
            // Обход результатов выборки
            int loadedCount = 0;
            while (executeQuery.next()) {
                loadedCount++;
                String lastName = executeQuery.getString("Фамилия").trim();
                String firstName = executeQuery.getString("Имя").trim();
                String middleName = executeQuery.getString("Отчество");
                String name;
                if (middleName == null) name = lastName + " " + firstName;
                else name = lastName + " " + firstName + " " + middleName.trim();
                main.getPersonData().add(new UserAccount(name));
                System.out.println(executeQuery.getString("Код") + " " + name);
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
            loadCount.setText(String.valueOf(loadedCount));
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadJSONSettings() {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            Object obj = jsonParser.parse(new FileReader("c:\\Temp\\server.json"));
            jsonObject = (JSONObject) obj;
            String server = (String) jsonObject.get("server");
            String username = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            String domain = (String) jsonObject.get("domain");
            this.sqlInstanceName = (String) jsonObject.get("instancename");
            this.sqlDataBaseName = (String) jsonObject.get("databasename");
            this.sqlUsername = (String) jsonObject.get("sqlusername");
            this.sqlUserPass = (String) jsonObject.get("sqluserpass");
           serverIP.setText(server);
           loginName.setText(username);
           loginPass.setText(password);
           this.domainName.setText(domain);
        }
        catch (IOException e) {e.printStackTrace();}
        catch (ParseException e) {e.printStackTrace();}
    }


}
