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

    public static void main(String[] args) {
        MatrixController testControl = new MatrixController();
        String input = "0|1 2 3/4 5 6/7 8 9";
        String input2 = "1|1 2 3/4 5 6/7 8 9|9 8 7/6 5 4/3 2 1";

        //标志0：一元运算样例
        testControl.transmitData(input);
        testControl.count();
        String DeterminationAns = testControl.getOutputInformation("matrixDeterminantAnswer");
        String InverseAns = testControl.getOutputInformation("matrixInverseAnswer");
        System.out.println("行列式值： " + DeterminationAns);
        System.out.println("逆矩阵为： " + InverseAns);

        //标志1：二元运算样例
        testControl.transmitData(input2);
        testControl.count();
        String addAns = testControl.getOutputInformation( "matrixAddAnswer");
        String crossAns = testControl.getOutputInformation("matrixCrossProductAnswer");
        System.out.println("矩阵加： " +addAns);
        System.out.println("矩阵叉乘：" + crossAns);



    }

    @Override
    public void count() {
        currentModel.count();
    }

}


