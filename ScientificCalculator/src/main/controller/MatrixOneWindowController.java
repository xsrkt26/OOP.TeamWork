package main.controller;
import controller.MatrixController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * @author:
 * @Description:
 * @date:
 */
public class MatrixOneWindowController extends Controller{
    @FXML
    TextArea input;

    @FXML
    TextArea transpose;

    @FXML
    TextArea adjoint;
    @FXML
    TextArea inverse;
    @FXML
    Label determinant;
    @FXML
    Label trace;
    @FXML
    Label rank;
    @FXML
    Label eigValue;
    private StringBuffer inputMatrix;
    private MatrixController matrixController = new MatrixController();
    private void processInput(StringBuffer input) {
        int index = 0;
        while ((index = input.indexOf("\n", index)) != -1) {
            input.replace(index, index + 1, "/");
            index += 2; // 移动到下一个位置，避免无限循环
        }
        input.insert(0, "0|");
    }
    public MatrixOneWindowController() {
    }
    @FXML
    void compute(MouseEvent event) {
        inputMatrix = new StringBuffer(input.getText());
        processInput(inputMatrix);
        matrixController.transmitData(inputMatrix.toString());
        matrixController.count();
        transpose.setText(matrixController.getOutputInformation("matrixTransposeAnswer").substring(2));
        adjoint.setText(matrixController.getOutputInformation("matrixAdjointAnswer").substring(2));
        inverse.setText(matrixController.getOutputInformation("matrixInverseAnswer").substring(2));
        determinant.setText(matrixController.getOutputInformation("matrixDeterminantAnswer").substring(2));
        trace.setText(matrixController.getOutputInformation("matrixTraceAnswer").substring(2));
        rank.setText(matrixController.getOutputInformation("matrixRankAnswer").substring(2));
        eigValue.setText(matrixController.getOutputInformation("matrixEigValueAnswer").substring(2));
    }
}
