package main.view;

import controller.LinearRegressionController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * @Description: The Controller of linear regression window
 * @author: QingYu
 * @date: 2023/12/9
 */
public class LinearWindowViewController extends viewController {
    @FXML
    private Label lblResult;
    @FXML
    private Label lblResultY;
    @FXML
    private Label xyList;
    @FXML
    private Label xSquareList;
    @FXML
    private Label ySquareList;
    @FXML
    private Label xAverage;
    @FXML
    private Label yAverage;
    @FXML
    private Label xyAverage;
    @FXML
    private Label xAverageSquare;
    @FXML
    private Label yAverageSquare;
    @FXML
    private Label xSquareAverage;
    @FXML
    private Label ySquareAverage;
    @FXML
    private Label regressionB;
    @FXML
    private Label regressionA;
    @FXML
    private Label regressionR;

    private StringBuffer showExpression;
    private StringBuffer xExpression = new StringBuffer("");
    private StringBuffer yExpression = new StringBuffer("");
    private boolean XClicked;
    private boolean YClicked;
    private LinearRegressionController linearRegressionController = new LinearRegressionController();
    private String showOperator = "";
    private int lastShowExpressionIndex;
    private ArrayList<String> stack = new ArrayList<>();
    public LinearWindowViewController() {
        XClicked = true;
        YClicked = false;
        showExpression = xExpression;
    }
    private void setText(String expression) {
        if (XClicked && !YClicked) {
            lblResult.setText(expression);
        } else {
            lblResultY.setText(expression);
        }
    }
    @FXML
    void onTextClickedX(MouseEvent event) {
        showExpression = xExpression;
        lblResult.setStyle("-fx-opacity: 1;");
        lblResultY.setStyle("-fx-opacity: 0.7;");
        XClicked = true;
        YClicked = false;
    }
    @FXML
    void onTextClickedY(MouseEvent event) {
        showExpression = yExpression;
        lblResultY.setStyle("-fx-opacity: 1;");
        lblResult.setStyle("-fx-opacity: 0.7;");
        XClicked = false;
        YClicked = true;
    }

    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn",""));
        showExpression.append(value);
        setText(showExpression.toString());
        stack.add(String.valueOf(value));
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn","");
        if(symbol.equals("Equals")) {
            linearRegressionController.transmitData(xExpression.toString() + "|" + yExpression.toString());
            linearRegressionController.count();
            xyList.setText(linearRegressionController.getOutputInformation("xyList"));
            xSquareList.setText(linearRegressionController.getOutputInformation("xSquareList"));
            ySquareList.setText(linearRegressionController.getOutputInformation("ySquareList"));
            xAverage.setText(linearRegressionController.getOutputInformation("xAverage"));
            yAverage.setText(linearRegressionController.getOutputInformation("yAverage"));
            xyAverage.setText(linearRegressionController.getOutputInformation("xyAverage"));
            xAverageSquare.setText(linearRegressionController.getOutputInformation("xAverageSquare"));
            xSquareAverage.setText(linearRegressionController.getOutputInformation("xSquareAverage"));
            ySquareAverage.setText(linearRegressionController.getOutputInformation("ySquareAverage"));
            regressionB.setText(linearRegressionController.getOutputInformation("regressionCoefficientB"));
            regressionA.setText(linearRegressionController.getOutputInformation("regressionCoefficientA"));
            regressionR.setText(linearRegressionController.getOutputInformation("correlationCoefficientR"));
        }
        else if(symbol.equals("Clear")) {
            setText(String.valueOf(0.0));
            showExpression.setLength(0);
            stack.clear();
        }
        else if(symbol.equals("BackSpace")) {
            if (!showExpression.isEmpty()) {
                lastShowExpressionIndex = showExpression.length() - 1;
                if (XClicked && !YClicked) {
                    xExpression = new StringBuffer(showExpression.substring(0, lastShowExpressionIndex));
                    showExpression = xExpression;
                } else {
                    yExpression = new StringBuffer(showExpression.substring(0, lastShowExpressionIndex));
                    showExpression = yExpression;
                }
                if (!showExpression.isEmpty()) {
                    setText(String.valueOf(showExpression));
                } else {
                    setText(String.valueOf(0.0));
                }
            } else {
                setText(String.valueOf(0.0));
            }
        }
        else {
            switch (symbol) {
                case "Plus" -> {
                    showOperator = "+";
                }
                case "Minus" -> {
                    showOperator = "-";
                }
                case "Multiply" -> {
                    showOperator = "*";
                }
                case "Divide" -> {
                    showOperator = "/";
                }
                case "Space" -> {
                    showOperator = " ";
                }
                case "Point" -> {
                    showOperator = ".";
                }
            }
            stack.add(showOperator);
            showExpression.append(showOperator);
            setText(String.valueOf(showExpression));
        }
    }
}
