package main;
/**
 * @Description: The main window of the program
 * @author: QingYu
 * @date: 2023/12/9
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.controller.MainWindowController;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainWindowInterface.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Scientific Calculator");
        stage.show();
        Image image = new Image("/images/icon.png");
        stage.getIcons().add(image);
        ((MainWindowController)loader.getController()).init(stage);
    }
    public void run(){launch();}
}
