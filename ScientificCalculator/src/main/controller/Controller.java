package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
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
        ((MenuWindowController)loader.getController()).init(stage);
        stage.show();
    }
}
