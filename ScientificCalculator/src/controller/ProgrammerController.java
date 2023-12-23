package controller;

import model.ProgrammerModel;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/12/18 16:00
 */
public class ProgrammerController implements Controller{
    ProgrammerModel currentModel;
    @Override
    public void transmitData(String inputInformation) {
        currentModel = new ProgrammerModel(inputInformation);
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


