package controller;

import model.LinearRegressionModel;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/12/12 18:30
 */
public class LinearRegressionController implements Controller{
    LinearRegressionModel currentModel = null;
    public LinearRegressionController() {
        currentModel = new LinearRegressionModel();
    }

    @Override
    public void transmitData(String inputInformation) {
        currentModel.setInputExpression(inputInformation);
    }

    @Override
    public void count() {
        currentModel.count();
    }

    @Override
    public String getOutputInformation(String key) {
        return currentModel.getOutPutMap().get(key);
    }





    public static void main(String[] arg) {
        Controller newController = new LinearRegressionController();
        newController.transmitData("100 200 300 400 500 600|0.383 0.420 0.455 0.490 0.524 0.559");
        newController.count();
        System.out.println(newController.getOutputInformation("xyList"));
    }




}


