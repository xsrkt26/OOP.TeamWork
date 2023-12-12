package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;

public class MenuWindowController extends Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MainWindowController)loader.getController()).init(stage);
        stage.show();
    }

    public void switchToLinearWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/LinearRegressionInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((LinearWindowController)loader.getController()).init(stage);
        stage.show();
    }

}
