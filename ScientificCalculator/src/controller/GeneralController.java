package controller;

import model.GeneralModel;

/**
 * GeneralController接口
 * @author ：kiyotaka
 * @date ：2023/12/12 20:56
 */
public class GeneralController implements Controller {
    GeneralModel currentModel;
    public GeneralController() {
        currentModel = new GeneralModel();
    }

    @Override
    /**
     * @author: kiyotaka
     *  数据输入函数
     * @date: 2023/12/23 11:20
     * @return void
     */
    public void transmitData(String inputInformation) {
        currentModel.setInputExpression(inputInformation);
    }

    @Override
    /**
     * @author: kiyotaka
     *  GeneralModel的答案输出只有一个，对应的key为：answer；
     * @date: 2023/12/23 11:17
     * @return java.lang.String
     */
    public String getOutputInformation(String key) {
        return currentModel.outputAns();
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

