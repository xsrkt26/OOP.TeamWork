package main.view;
/**
 * @Description: The superclass of all controllers
 * @author: QingYu
 * @date: 2023/12/9
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class viewController {
    private double x, y;
    @FXML
    protected Pane titlePane;
    @FXML protected ImageView btnMinimize, btnClose, binMenu;
    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
        binMenu.setOnMouseClicked(mouseEvent -> {
            try {
                switchToMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void switchToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MenuInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MenuWindowViewController)loader.getController()).init(stage);
        stage.show();
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        ((Pane)event.getSource()).setOpacity(0.5);
    }
    @FXML
    void onMouseExited(MouseEvent event) {
        ((Pane)event.getSource()).setOpacity(1);
    }
}
