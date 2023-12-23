package controller;

import model.MatrixModel;

/**
 * @author ：kiyotaka
 * @description：MatrixModel的控制器及其测试函数
 * @date ：2023/12/12 20:45
 */
public class MatrixController implements Controller{
    MatrixModel currentModel;
    public MatrixController() {
        currentModel = new MatrixModel();
    }

    @Override
    /**
     * @author: kiyotaka
     * @description: 数据输入函数
     * @date: 2023/12/23 11:20
     * @return void
     */
    public void transmitData(String inputInformation) {
        currentModel.setInputExpression(inputInformation);
    }

    @Override
    /**
     * @author: kiyotaka
     * @description: MatrixModel的答案输出比较多，对应的key可以在MatrixModel的参数中找到；
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

    public static void main(String[] args) {
        MatrixController testControl = new MatrixController();
        String input = "0|1 2 3/4 5 6/7 8 9";
        String input2 = "1|1 2 3/4 5 6/7 8 9|9 8 7/6 5 4/3 2 1";

        //标志0：一元运算样例
//        testControl.transmitData(input);
//        testControl.count();
//        String DeterminationAns = testControl.getOutputInformation("matrixDeterminantAnswer");
//        String InverseAns = testControl.getOutputInformation("matrixInverseAnswer");
//        System.out.println("行列式值： " + DeterminationAns);
//        System.out.println("逆矩阵为： " + InverseAns);

        //标志1：二元运算样例
        testControl.transmitData(input2);
        testControl.count();
        String addAns = testControl.getOutputInformation( "matrixSubAnswer");
        String crossAns = testControl.getOutputInformation("matrixCrossProductAnswer");
        System.out.println("矩阵减： " +addAns);
        System.out.println("矩阵叉乘：" + crossAns);



    }


}


