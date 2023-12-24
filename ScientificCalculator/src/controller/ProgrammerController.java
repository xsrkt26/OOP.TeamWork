package controller;

import model.ProgrammerModel;

/**
 * 程序员运算方法接口
 * @author ：kiyotaka
 * @date ：2023/12/18 16:00
 */
public class ProgrammerController implements Controller{
    public ProgrammerModel currentModel;
    @Override
    /**
     * @author: kiyotaka
     *  数据输入函数
     * @date: 2023/12/23 11:20
     * @return void
     */
    public void transmitData(String inputInformation) {
        currentModel = new ProgrammerModel(inputInformation);
    }

    @Override
    /**
     * @author: kiyotaka
     *  与GeneralModel类似；
     * @date: 2023/12/23 11:17
     * @return java.lang.String
     */
    public String getOutputInformation(String key) {
        return currentModel.getOutPutMap().get(key);
    }

    @Override
    /**
     * @author: kiyotaka
     *  计算函数
     * @date: 2023/12/23 11:19
     * @return void
     */
    public void count() {
        currentModel.count();
    }
}

