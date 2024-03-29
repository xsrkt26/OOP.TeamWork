package controller;

import model.LinearRegressionModel;

/**
 * 线性回归方法接口
 * @author ：kiyotaka
 * @date ：2023/12/12 18:30
 */
public class LinearRegressionController implements Controller{
    LinearRegressionModel currentModel;
    public LinearRegressionController() {
        currentModel = new LinearRegressionModel();
    }

    @Override
    /**
     * @author: kiyotaka
     *  数据输入函数
     * @date: 2023/12/23 11:20
     * @return void
     */
    public void transmitData(String inputInformation) {
        if (currentModel != null) {
            currentModel = new LinearRegressionModel();
        }
        currentModel.setInputExpression(inputInformation);
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

    @Override
    /**
     * @author: kiyotaka
     *  LinearRegressionModel的答案输出比较多，对应的key可以在LinearRegressionModel的参数中找到；
     * @date: 2023/12/23 11:17
     * @return java.lang.String
     */
    public String getOutputInformation(String key) {
        return currentModel.getOutPutMap().get(key);
    }





    public static void main(String[] arg) {
        Controller newController = new LinearRegressionController();
        newController.transmitData("100 200 300 400 500 600|0.383 0.420 0.455 0.490 0.524 0.559");
        newController.count();
        System.out.println(newController.getOutputInformation("xyList"));
//        newController.count();
//        System.out.println(newController.getOutputInformation("xyList"));
//        newController.count();
//        System.out.println(newController.getOutputInformation("xyList"));
    }
}

