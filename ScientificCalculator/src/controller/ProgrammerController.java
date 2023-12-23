package controller;

import model.ProgrammerModel;

/**
 * @author ：kiyotaka
 * @description：ProgrammerModel的控制器
 * @date ：2023/12/18 16:00
 */
public class ProgrammerController implements Controller{
    ProgrammerModel currentModel;
    @Override
    /**
     * @author: kiyotaka
     * @description: 数据输入函数
     * @date: 2023/12/23 11:20
     * @return void
     */
    public void transmitData(String inputInformation) {
        currentModel = new ProgrammerModel(inputInformation);
    }

    @Override
    /**
     * @author: kiyotaka
     * @description: 与GeneralModel类似；
     * @date: 2023/12/23 11:17
     * @return java.lang.String
     */
    public String getOutputInformation(String key) {
        return currentModel.getOutPutMap().get(key);
    }

    @Override
    /**
     * @author: kiyotaka
     * @description: 计算函数
     * @date: 2023/12/23 11:19
     * @return void
     */
    public void count() {
        currentModel.count();
    }
}


