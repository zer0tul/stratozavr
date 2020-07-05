package stratozavr;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private ObservableList<UserAccount> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        primaryStage.setTitle("Hello World");
        Scene myScene = new Scene(root);
        primaryStage.setScene(myScene);



        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<UserAccount> getPersonData() {
        return personData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
