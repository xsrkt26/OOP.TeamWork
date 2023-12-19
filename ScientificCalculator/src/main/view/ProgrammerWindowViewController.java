package main.view;

import controller.ProgrammerController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * @author:
 * @Description:
 * @date:
 */
public class ProgrammerWindowViewController extends viewController{
    @FXML
    private Label lblResult;
    private StringBuffer calculateExpression = new StringBuffer("");
    private StringBuffer showExpression = new StringBuffer("");
    private ProgrammerController controller = new ProgrammerController();
    private String operator = "+";
    private String showOperator = "";
    private int lastShowExpressionIndex;
    private ArrayList<String> stack = new ArrayList<>();
    private int baseMode;
    private List<Integer> binList = new ArrayList<>(List.of(0, 1));
    private List<Integer> octList = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7));
    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn",""));
        if (baseMode == 2 && !binList.contains(value)) {
            return;
        }
        if (baseMode == 8 && !octList.contains(value)) {
            return;
        }
        calculateExpression.append(value);
        showExpression.append(value);
        lblResult.setText(showExpression.toString());
        stack.add(String.valueOf(value));
    }
    @FXML
    void onHexAlphaClicked(MouseEvent event) {
        if (baseMode != 16) {
            return;
        }
        String res = ((Pane)event.getSource()).getId().replace("btn","");
        calculateExpression.append(res);
        showExpression.append(res);
        lblResult.setText(showExpression.toString());
        stack.add(res);
    }
    @FXML
    void onModeClicked(MouseEvent event) {
        baseMode = Integer.parseInt(((Pane)event.getSource()).getId().replace("base",""));
        controller.transmitData("0");
        controller.currentModel.numberSystem = baseMode;
        controller.count();
        controller.currentModel.setNumberSystem(baseMode);
        lblResult.setText(controller.getOutputInformation("answer"));
        calculateExpression.setLength(0);
        showExpression.setLength(0);
        operator = "";
        stack.clear();
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn","");

        if(symbol.equals("Equals")) {
            controller.transmitData(calculateExpression.toString());
            controller.currentModel.numberSystem = baseMode;
            controller.count();
            controller.currentModel.setNumberSystem(baseMode);
            lblResult.setText(controller.getOutputInformation("answer"));
        }
        else if(symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0.0));
            calculateExpression.setLength(0);
            showExpression.setLength(0);
            operator = "";
            stack.clear();
        }
        else if(symbol.equals("BackSpace")) {
            if (!stack.isEmpty()) {
                calculateExpression = new StringBuffer(calculateExpression.substring(0, calculateExpression.length() - 1));
                lastShowExpressionIndex = stack.get(stack.size() - 1).length();
                showExpression = new StringBuffer(showExpression.substring(0, showExpression.length() - lastShowExpressionIndex));
                stack.remove(stack.size() - 1);
                if (!showExpression.isEmpty()) {
                    lblResult.setText(String.valueOf(showExpression));
                } else {
                    lblResult.setText(String.valueOf(0.0));
                }
            } else {
                lblResult.setText(String.valueOf(0.0));
            }
        }
        else {
            switch (symbol) {
                case "Plus" -> {
                    operator = "+";
                    showOperator = "+";
                }
                case "Minus" -> {
                    operator = "-";
                    showOperator = "-";
                }
                case "Multiply" -> {
                    operator = "*";
                    showOperator = "*";
                }
                case "Divide" -> {
                    operator = "/";
                    showOperator = "/";
                }
                case "AND" -> {
                    operator = "&";
                    showOperator = "&";
                }
                case "OR" -> {
                    operator = "|";
                    showOperator = "|";
                }
                case "NOT" -> {
                    operator = "~";
                    showOperator = "~";
                }
                case "XOR" -> {
                    operator = "^";
                    showOperator = "^";
                }
                case "NOR" -> {
                    operator = "NOR";
                    showOperator = "nor";
                }
                case "NAND" -> {
                    operator = ".";
                    showOperator = "nand";
                }
                case "LShift" -> {
                    operator = "<<";
                    showOperator = "<<";
                }
                case "RShift" -> {
                    operator = ">>";
                    showOperator = ">>";
                }
                case "Lbracket" -> {
                    operator = "(";
                    showOperator = "(";
                }
                case "Rbracket" -> {
                    operator = ")";
                    showOperator = ")";
                }
            }
            stack.add(showOperator);
            calculateExpression.append(operator);
            showExpression.append(showOperator);
            lblResult.setText(String.valueOf(showExpression));
        }
    }
}
