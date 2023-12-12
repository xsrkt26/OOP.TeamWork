package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class MainWindowController extends Controller{
    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnClose, binMenu;
    @FXML private Label lblResult;
    @FXML private Pane mainPane;

    private double x, y;
    private double num1 = 0;
    private StringBuffer expression = new StringBuffer("");
    private GeneralModel generalModel = new GeneralModel();
    private String operator = "+";
    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn",""));
        expression.append(value);
        lblResult.setText(expression.toString());
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn","");

        if(symbol.equals("Equals")) {
            generalModel.setInputExpression(expression.toString());
            generalModel.count();
            lblResult.setText(generalModel.outputAns());
            return;
        }
        else if(symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0.0));
            expression.setLength(0);
            operator = ".";
        }
        else {
            switch (symbol) {
                case "Plus" -> operator = "+";
                case "Minus" -> operator = "-";
                case "Multiply" -> operator = "*";
                case "Divide" -> operator = "/";
            }
            expression.append(operator);
            lblResult.setText(String.valueOf(expression));
        }
    }
}