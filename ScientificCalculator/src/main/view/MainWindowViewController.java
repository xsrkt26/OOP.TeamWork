package main.view;
/**
 * @Description: The Controller of main window
 * @author: QingYu
 * @date: 2023/12/9
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.*;

import java.util.ArrayList;

public class MainWindowViewController extends viewController {
    @FXML private Label lblResult;
    @FXML private Label lblResultAlways;
    private StringBuffer calculateExpression = new StringBuffer("");
    private StringBuffer showExpression = new StringBuffer("");
    private GeneralModel generalModel = new GeneralModel();
    private String operator = "+";
    private String showOperator = "";
    private int lastShowExpressionIndex;
    private ArrayList<String> stack = new ArrayList<>();
    private void count(){
        try {
            generalModel.count();
        } catch (Exception e){

        }

    }
    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn",""));
        calculateExpression.append(value);
        showExpression.append(value);
        lblResult.setText(showExpression.toString());
        generalModel.setInputExpression(calculateExpression.toString());
        count();
        lblResultAlways.setText(generalModel.outputAns());
        stack.add(String.valueOf(value));
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn","");

        if(symbol.equals("Equals")) {
            generalModel.setInputExpression(calculateExpression.toString());
            count();
            lblResult.setText(generalModel.outputAns());
        }
        else if(symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0.0));
            calculateExpression.setLength(0);
            showExpression.setLength(0);
            operator = ".";
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
                    lblResultAlways.setText(String.valueOf(0.0));
                    generalModel.setInputExpression("0");
                    count();
                    return;
                }
            } else {
                lblResultAlways.setText(String.valueOf(0.0));
                lblResult.setText(String.valueOf(0.0));
                generalModel.setInputExpression("0");
                count();
                return;
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
                case "Point" -> {
                    operator = ".";
                    showOperator = ".";
                }
                case "Power" -> {
                    operator = "^";
                    showOperator = "^";
                }
                case "Cos" -> {
                    operator = "o";
                    showOperator = "cos";
                }
                case "Sin" -> {
                    operator = "s";
                    showOperator = "sin";
                }
                case "Tan" -> {
                    operator = "t";
                    showOperator = "tan";
                }
                case "Cot" -> {
                    operator = "j";
                    showOperator = "cot";
                }
                case "Sec" -> {
                    operator = "u";
                    showOperator = "sec";
                }
                case "Csc" -> {
                    operator = "i";
                    showOperator = "csc";
                }
                case "Asin" -> {
                    operator = "S";
                    showOperator = "arcsin";
                }
                case "Acos" -> {
                    operator = "O";
                    showOperator = "arccos";
                }
                case "Atan" -> {
                    operator = "T";
                    showOperator = "arctan";
                }
                case "Acot" -> {
                    operator = "J";
                    showOperator = "arccot";
                }
                case "Asec" -> {
                    operator = "U";
                    showOperator = "arcsec";
                }
                case "Acsc" -> {
                    operator = "I";
                    showOperator = "arccsc";
                }
                case "Ln" -> {
                    operator = "n";
                    showOperator = "ln";
                }
                case "Mod" -> {
                    operator = "d";
                    showOperator = "mod";
                }
                case "Abs" -> {
                    operator = "a";
                    showOperator = "abs";
                }
                case "Log" -> {
                    operator = "l";
                    showOperator = "log";
                }
                case "Pi" -> {
                    operator = "p";
                    showOperator = "π";
                }
                case "E" -> {
                    operator = "e";
                    showOperator = "e";
                }
                case "Lbracket" -> {
                    operator = "(";
                    showOperator = "(";
                }
                case "Rbracket" -> {
                    operator = ")";
                    showOperator = ")";
                }
                case "Fact" -> {
                    operator = "!";
                    showOperator = "!";
                }
            }
            stack.add(showOperator);
            calculateExpression.append(operator);
            showExpression.append(showOperator);
            lblResult.setText(String.valueOf(showExpression));

        }
        generalModel.setInputExpression(calculateExpression.toString());
        count();
        lblResultAlways.setText(generalModel.outputAns());
    }
}