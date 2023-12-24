package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The superclass of all controllers
 * @author: QingYu
 * @date: 2023/12/9
 */
public class viewController {
    private double x, y;
    @FXML
    protected Pane titlePane;
    @FXML protected ImageView btnMinimize, btnClose, binMenu;
    /**
     * 程序入口
     * @author: QingYu
     * @date: 2023/12/9
     */
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
    /**
     * 切换至主菜单
     * @author: QingYu
     * @date: 2023/12/9
     */
    public void switchToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MenuInterface.fxml"));
        Stage stage = (Stage)binMenu.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((MenuWindowViewController)loader.getController()).init(stage);
        stage.show();
    }
    /**
     * 鼠标拖动事件
     * @author: QingYu
     * @date: 2023/12/9
     */
    @FXML
    void onMouseEntered(MouseEvent event) {
        ((Pane)event.getSource()).setOpacity(0.5);
    }
    /**
     * 鼠标悬停提示
     * @author: QingYu
     * @date: 2023/12/9
     */
    @FXML
    void onMouseExited(MouseEvent event) {
        ((Pane)event.getSource()).setOpacity(1);
    }
}
