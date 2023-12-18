package main.view;

import controller.MatrixController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * @author:
 * @Description:
 * @date:
 */
public class MatrixTwoWindowViewController extends viewController {
    @FXML
    TextArea inputA;
    @FXML
    TextArea inputB;
    @FXML
    TextArea addAnswer;
    @FXML
    TextArea subAnswer;
    @FXML
    TextArea crossProduct;
    @FXML
    TextArea dotProduct;
    private StringBuffer inputMatrixA;
    private StringBuffer inputMatrixB;
    private MatrixController matrixController = new MatrixController();
    private StringBuffer processInput(StringBuffer inputA, StringBuffer inputB) {
        int index = 0;
        while ((index = inputA.indexOf("\n", index)) != -1) {
            inputA.replace(index, index + 1, "/");
            index += 2; // 移动到下一个位置，避免无限循环
        }
        index = 0;
        while ((index = inputB.indexOf("\n", index)) != -1) {
            inputB.replace(index, index + 1, "/");
            index += 2; // 移动到下一个位置，避免无限循环
        }
        inputA.insert(0, "1|");
        return inputA.append("|").append(inputB);
    }
    @FXML
    void compute(MouseEvent event) {
        inputMatrixA = new StringBuffer(inputA.getText());
        inputMatrixB = new StringBuffer(inputB.getText());
        matrixController.transmitData(processInput(inputMatrixA, inputMatrixB).toString());
        matrixController.count();
        addAnswer.setText(matrixController.getOutputInformation("matrixAddAnswer").substring(2));
        subAnswer.setText(matrixController.getOutputInformation("matrixSubAnswer").substring(2));
        crossProduct.setText(matrixController.getOutputInformation("matrixCrossProductAnswer").substring(2));
        dotProduct.setText(matrixController.getOutputInformation("matrixDotProductAnswer").substring(2));
    }

}
