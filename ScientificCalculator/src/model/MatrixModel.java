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

        public Matrix(){}

        public Matrix(int row, int col) {
            this.row = row;
            this.col = col;
            data = new double[row][col];
        }

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




    private Matrix matrixAdd(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵加法；要求：两矩阵i，j相等
        * @date: 2023/12/9 13:00
        * @return Matrix
        */
        int i,j;
        int row = ope1.row;
        int col = ope2.col;
        Matrix ans = new Matrix(row, col);
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                ans.data[i][j] = ope1.data[i][j] + ope2.data[i][j];
            }
        }
        return ans;
    }

    private Matrix matrixSub(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵减法；要求：两矩阵i，j相等
        * @date: 2023/12/9 15:53
        * @return Matrix
        */
        // ！注意运算顺序：ope1 - ope2
        int i,j;
        int row = ope1.row;
        int col = ope2.col;
        Matrix ans = new Matrix(row, col);
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                ans.data[i][j] = ope1.data[i][j] - ope2.data[i][j];
            }
        }
        return ans;
    }

    private Matrix matrixInnerProduct(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵内积；要求：两矩阵i，j相等
        * @date: 2023/12/9 16:13
        * @return Matrix(1行n列)
        */
        int i,j;
        int row = ope1.row;
        int col = ope2.col;
        Matrix res = new Matrix(1, col);
        for(j = 0; j < col; j++){
            double temp = 0;
            for(i = 0; i < row; i++){
                temp += ope1.data[i][j] * ope2.data[i][j];
            }
            res.data[0][j] = temp;
        }
        return res;
    }

    private Matrix matrixCrossProduct(Matrix ope1, Matrix ope2){
        /**
         * @author: hirmy
         * @description: 矩阵叉乘；要求：ope1.col == ope2.row
         * @date: 2023/12/9 16:20
         * @return Matrix
         */
        int i,j,k;
        int rowA = ope1.row;
        int total = ope1.col;//total == ope1.col == ope2.row
        int colB = ope2.col;
        Matrix res = new Matrix(rowA, colB);
        for(i = 0; i < rowA; i++){
            for(j = 0; j < colB; j++){
                double temp = 0;
                for(k = 0; k < total; k++){
                    temp += ope1.data[i][k] * ope2.data[k][j];
                }
                res.data[i][j] = temp;
            }
        }
        return res;
    }

    private Matrix matrixDotProduct(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵点乘；要求：两矩阵i，j相等
        * @date: 2023/12/9 16:31
        * @return Matrix
        */
        int i,j;
        int row = ope1.row;
        int col = ope1.col;
        Matrix res = new Matrix(row, col);
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                res.data[i][j] = ope1.data[i][j] * ope2.data[i][j];
            }
        }
        return res;
    }

    private Matrix matrixTranspose(Matrix ope){
        /**
        * @author: hirmy
        * @description: 矩阵转置；要求：暂无
        * @date: 2023/12/9 16:35
        * @return Matrix
        */
        int i,j;
        int row = ope.row;
        int col = ope.col;
        Matrix res = new Matrix(row, col);
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                res.data[j][i] = ope.data[i][j];
            }
        }
        return res;
    }

    private double matrixDeterminant(Matrix ope){
        /**
        * @author: hirmy
        * @description: 求行列式det(A)；要求：暂无
        * @date: 2023/12/9 16:42
        * @return double
        */
        int i,j;
        int row = ope.row;
        int col = ope.col;
        if(row == 1 && col == 1){
            //若一阶矩阵，直接返回
            return ope.data[0][0];
        }

    }

    private Matrix matrixAdjoint(Matrix ope, int i, int j){
        /**
        * @author: hirmy
        * @description: 求伴随矩阵；要求：暂无
        * @date: 2023/12/9 16:49
        * @return Matrix
        */

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


