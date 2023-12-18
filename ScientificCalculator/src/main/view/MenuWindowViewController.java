package main.view;
/**
 * @Description: The Controller of menu window
 * @author: QingYu
 * @date: 2023/12/9
 */
import controller.FunctionGraphController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuWindowViewController extends viewController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MainWindowViewController)loader.getController()).init(stage);
        stage.show();
    }

    public void switchToLinearWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/LinearRegressionInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((LinearWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    public void switchToMatrixOneWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MatrixOneWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MatrixOneWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    public void switchToMatrixTwoWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MatrixTwoWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MatrixTwoWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    public void switchToFunctionGraph() throws Exception {
        FunctionGraphController functionGraphController = new FunctionGraphController();
        functionGraphController.draw();
    }
}
