package model;

import java.util.ArrayList;

/**
 * @author ：kiyotaka
 * @description：Matrix模块中,输入字符串的形式如下：“identifier(0 or 1, repersent 1 matrix or 2 matrix)
 * | 19 124 141 12 / 12 214 214 213" 表示一个2 * 4 的矩阵，可以进行一元运算。
 * @date ：2023/11/28 17:41
 */
public class MatrixModel extends CalculatorModel{
    // 判断该矩阵Model 是一元运算还是二元运算，0是一元；
    int matrixOperation;

    class Matrix{
        int row;
        int col;
        double[][] data;

        public Matrix(String[] matrixString) {
            row = matrixString.length;
            col = matrixString[0].split("\\s+").length;
            data = new double[row][col];
            for (int i = 0; i < row; i++) {
                String[] rowString = matrixString[i].split("\\s+");
                for (int j = 0; j < col; j++) {
                    data[i][j] = Double.parseDouble(rowString[j]);
                }
            }
        }
    }

    private Matrix matrixA;
    private Matrix matrixB;

    public MatrixModel(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    @Override
    public void count() {

    }

    @Override
    public String outputAns() {
        return null;
    }

    @Override
    public boolean checkIllegal() {
        return splitInputExpression();
    }

    /**
     * @author: kiyotaka
     * @description: 分割输入字符串的辅助函数
     * @date: 2023/12/9 16:22
     * @return
     */
    private boolean splitInputExpression() {
        String[] temp = inputExpression.split("\\|");
        if (temp[0].equals("0")) {
            matrixOperation = 0;
        }
        else {
            matrixOperation = 1;
        }
        if (matrixOperation == 0) {
            String[] matrixString = temp[1].split("/");
            if (!checkMatrixString(matrixString)) {
                return false;
            }
            matrixA = new Matrix(matrixString);
            matrixB = null;
        }
        else {
            String[] matrixStringA = temp[1].split("/");
            String[] matrixStringB = temp[2].split("/");
            if (!checkMatrixString(matrixStringA) || !checkMatrixString(matrixStringB)){
                return false;
            }
            matrixA = new Matrix(matrixStringA);
            matrixB = new Matrix(matrixStringB);
            if (matrixA.row != matrixB.col && matrixB.row != matrixA.col) {
                return false;
            }
        }
        return true;
    }

    /**
     * @author: kiyotaka
     * @description: 判断组成矩阵的字符串是否合法
     * @date: 2023/12/9 16:40
     * @return
     */
    private boolean checkMatrixString(String[] matrixString) {
        int col = matrixString[0].split("\\s+").length;
        for (String test: matrixString) {
            int temp = test.split("\\s+").length;
            if (temp != col) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        String A = "0|120 123 124/123 2141 124/41 124 141";
        MatrixModel testModel = new MatrixModel(A);
        testModel.checkIllegal();
        System.out.println(testModel.checkIllegal());
    }

}


