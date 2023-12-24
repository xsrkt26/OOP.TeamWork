package main.view;

import controller.FunctionGraphController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * 菜单窗口
 * @author: QingYu
 * @date: 2023/12/9
 */
public class MenuWindowViewController extends viewController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    /**
     * 切换至常规科学计算器窗口
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MainWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    /**
     * 切换至线性回归计算窗口
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToLinearWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/LinearRegressionInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((LinearWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    /**
     * 切换至一元矩阵运算窗口
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToMatrixOneWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MatrixOneWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MatrixOneWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    /**
     * 切换至二院矩阵运算窗口
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToMatrixTwoWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MatrixTwoWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MatrixTwoWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    /**
     * 切换至程序员计算器窗口
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToProgrammerWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ProgrammerWindowInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((ProgrammerWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    /**
     * 切换至函数绘图窗口
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToFunctionGraph() throws Exception {
        FunctionGraphController functionGraphController = new FunctionGraphController();
        functionGraphController.draw();
    }
}
