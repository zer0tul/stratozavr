package stratozavr;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main extends Application {
    private TableView<UserAccount> table = new TableView<UserAccount>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        TableColumn<UserAccount, String> fullRussNameCol = new TableColumn<UserAccount, String>("ФИО");
        TableColumn<UserAccount, String> firstNameCol = new TableColumn<UserAccount, String>("Имя");
        TableColumn<UserAccount, String> middleNameCol = new TableColumn<UserAccount, String>("Отчество");
        TableColumn<UserAccount, String> lastNameCol = new TableColumn<UserAccount, String>("Фамилия");
        table.getColumns().addAll(fullRussNameCol,firstNameCol,middleNameCol,lastNameCol);


        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
