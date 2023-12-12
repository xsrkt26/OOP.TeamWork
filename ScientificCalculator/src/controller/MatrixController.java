package controller;

import model.MatrixModel;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/12/12 20:45
 */
public class MatrixController implements Controller{
    MatrixModel currentModel;
    public MatrixController() {
        currentModel = new MatrixModel();
    }

    @Override
    public void transmitData(String inputInformation) {
        currentModel.setInputExpression(inputInformation);
    }

    @Override
    public String getOutputInformation(String key) {
        return currentModel.getOutPutMap().get(key);

    }

    @Override
    public void count() {
        currentModel.count();
    }




}


