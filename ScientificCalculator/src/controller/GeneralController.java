package controller;

import model.FunctionGraphModel;
import model.GeneralModel;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/12/12 20:56
 */
public class GeneralController implements Controller {
    GeneralModel currentModel;
    public GeneralController() {
        currentModel = new GeneralModel();
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


