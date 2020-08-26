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

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private ObservableList<UserAccount> personData = FXCollections.observableArrayList();

    public Main() {
        personData.add(new UserAccount("Иванов Петр Максимович"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Студенты и домен");
        showPerson();


    }

    public void showPerson() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main.fxml"));
            Parent root = loader.load();
            Scene myScene = new Scene(root);
            primaryStage.setScene(myScene);
            primaryStage.show();
            primaryStage.setResizable(false);
            Controller controller = loader.getController();
            controller.setMain(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
