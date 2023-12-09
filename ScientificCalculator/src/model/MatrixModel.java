package model;

import java.util.ArrayList;

/**
 * @author ：kiyotaka
 * @description：Matrix模块中,输入字符串的形式如下：“identifier(0 or 1, repersent 1 matrix or 2 matrix)
 * | 19 124 141 12 / 12 214 214 213" 表示一个2 * 4 的矩阵，可以进行一元运算。
 * @date ：2023/11/28 17:41
 */
public class MatrixModel extends CalculatorModel{
    final double EP = 1e-10;

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

    private Matrix matrixC;

    public MatrixModel(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    @Override
    public void count() {

    }


    private void matrixAdd(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵加法；要求：两矩阵i，j相等
        * @date: 2023/12/9 13:00
        * @return void
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
        matrixC = ans;
    }

    private void matrixSub(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵减法；要求：两矩阵i，j相等
        * @date: 2023/12/9 15:53
        * @return void
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
        matrixC = ans;
    }

    private void matrixInnerProduct(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵内积；要求：两矩阵i，j相等
        * @date: 2023/12/9 16:13
        * @return void
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
        matrixC = res;
    }



    private void matrixCrossProduct(Matrix ope1, Matrix ope2){
        /**
         * @author: hirmy
         * @description: 矩阵叉乘；要求：ope1.col == ope2.row
         * @date: 2023/12/9 16:20
         * @return void
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
        matrixC = res;
    }

    private double[][] matrixCrossProduct_tool(double[][] ope1, double[][] ope2){
        /**
        * @author: hirmy
        * @description: 工具用方法，返回矩阵叉乘
        * @date: 2023/12/9 21:37
        * @return Matrix
        */
        int i,j,k;
        int rowA = ope1.length;
        int total = ope1[0].length;//total == ope1.N == ope2.M
        int colB = ope2[0].length;
        double[][] res = new double[rowA][colB];
        for(i = 0; i < rowA; i++){
            for(j = 0; j < colB; j++){
                double temp = 0;
                for(k = 0; k < total; k++){
                    temp += ope1[i][k] * ope2[k][j];
                }
                res[i][j] = temp;
            }
        }
        return res;
    }

    private void matrixDotProduct(Matrix ope1, Matrix ope2){
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
        matrixC = res;
    }

    private void matrixTranspose(Matrix ope){
        /**
        * @author: hirmy
        * @description: 矩阵转置；要求：暂无
        * @date: 2023/12/9 16:35
        * @return void
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
        matrixC = res;
    }

    private double[][] matrixTranspose(double[][] ope){
        /**
        * @author: hirmy
        * @description: 工具用方法，求矩阵转置
        * @date: 2023/12/9 22:21
        * @return double[][]
        */
        int i,j;
        int m = ope.length;
        int n = ope[0].length;
        double[][] res = new double[n][m];
        for(i = 0; i < m; i++){
            for(j = 0; j < n; j++){
                res[j][i] = ope[i][j];
            }
        }
        return res;
    }
    private double matrixDeterminant(Matrix ope){
        /**
        * @author: hirmy
        * @description: 求行列式det(A)；要求：i == j,方阵
        * @date: 2023/12/9 16:42
        * @return double
        */

        int rank = ope.row;
        if(rank == 1){
            //若一阶矩阵，直接返回
            return ope.data[0][0];
        }
        else if(rank == 2){
            //二阶矩阵，直接计算
            return ope.data[0][0] * ope.data[1][1] - ope.data[0][1] * ope.data[1][0];
        }
        double result = 0;

        for (int k = 0; k < rank; k++)
        {
            // 取出余子式
            double[][] newArr=new double[rank-1][rank-1];
            int index = 0;
            // 对数组进行赋值
            for (int i = 0; i < rank; i++)
            {
                if (k != i)
                {
                    for (int j = 1; j < rank; j++)
                    {
                        // 如果该行不等于所在行
                        newArr[index][j-1]=ope.data[i][j];
                    }
                    // 到第下一行赋值
                    index++;
                }
            }
            Matrix newMatrix = new Matrix(rank-1,rank-1);
            newMatrix.row = rank-1;
            newMatrix.col = rank-1;
            newMatrix.data = newArr;
            // 重点注意  由于此处取的是  a11 所以 (-1)^(1+1) = 1  可以进行处理
            result += Math.pow(-1,(k+1+1)) * ope.data[k][0] * matrixDeterminant(newMatrix);
        }
        return result;

    }

    private Matrix minorMatrix(int row, int col, Matrix ope){
        /**
        * @author: hirmy
        * @description: 工具用方法，返回去掉i行j列的矩阵，用于求伴随矩阵;要求：方阵
        * @date: 2023/12/9 17:46
        * @return Matrix
        */
        int rank = ope.row - 1;
        double[][] arr = new double[rank][rank];
        Matrix ans = new Matrix(rank,rank);
        ans.row = rank;
        ans.col = rank;
        int realI = 0, realJ = 0;
        for(int i = 0; i < row; i++){
            if(i != row){
                for(int j = 0; j < col; j++){
                    if(j != col){
                        arr[realI][realJ] = ope.data[i][j];
                        realJ++;
                    }
                }
                realI++;
            }
        }
        ans.data = arr;
        return ans;
    }

    private void matrixAdjoint(Matrix ope){
        /**
        * @author: hirmy
        * @description: 求伴随矩阵；要求：i == j,方阵
        * @date: 2023/12/9 16:49
        * @return void
        */
        int i,j;
        int rank = ope.row;
        Matrix res = new Matrix(rank,rank);
        for(i = 0; i < rank; i++){
            for(j = 0; j < rank; j++){
                res.data[j][i] = Math.pow(-1,i+j) * matrixDeterminant(minorMatrix(i,j,ope));
            }
        }
        matrixC = res;
    }

    private Matrix matrixAdjoint_tool(Matrix ope){
        /**
         * @author: hirmy
         * @description: 工具用方法，求伴随矩阵（需要返回时用到）
         * @date: 2023/12/9 16:49
         * @return Matrix
         */
        int i,j;
        int rank = ope.row;
        Matrix res = new Matrix(rank,rank);
        for(i = 0; i < rank; i++){
            for(j = 0; j < rank; j++){
                res.data[j][i] = Math.pow(-1,i+j) * matrixDeterminant(minorMatrix(i,j,ope));
            }
        }
        return res;
    }

    private Matrix matrixDiv(Matrix ope, double num){
        /**
        * @author: hirmy
        * @description: 工具用方法，矩阵数除，用于计算逆矩阵
        * @date: 2023/12/9 18:00
        * @return Matrix
        */
        int row = ope.row;
        int col = ope.col;
        int i,j;
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                ope.data[i][j] /= num;
            }
        }
        return ope;
    }
    private void matrixInverse(Matrix ope){
        /**
        * @author: hirmy
        * @description: 求逆矩阵；要求：i == j
        * @date: 2023/12/9 17:08
        * @return void
        */
        double det = matrixDeterminant(ope);
        Matrix adj = matrixAdjoint_tool(ope);
        if(Math.abs(det-0) < EP){
            outputAnswer = "NaN";
            outputAns();
        }
        matrixC = matrixDiv(adj,det);
    }

    private double matrixTrace(Matrix ope){
        /**
        * @author: hirmy
        * @description: 矩阵求迹；要求：方阵
        * @date: 2023/12/9 18:19
        * @return double
        */
        int rank = ope.row;
        double res = 0;
        for(int i = 0; i < rank; i++){
            res += ope.data[i][i];
        }
        return res;
    }

    private int Rank(Matrix ope){
    /**
    * @author: hirmy
    * @description: 矩阵求秩；要求：暂无
    * @date: 2023/12/9 19:50
    * @return int
    */
        int n = ope.col;
        int m = ope.row ;
        int i = 0;
        int j = 0;
        int i1, j1;
        double temp1;

        if(m > n)
        {
            i = m;
            m = n;
            n = i;
            i = 1;
        }

        m -= 1;
        n -= 1;

        double[][] temp = new double[m+1][n+1];

        if(i == 0)
        {
            for(i = 0; i <= m; i++)
            {
                for(j = 0; j <= n; j++)
                {
                    temp[i][j] = ope.data[i][j];
                }
            }
        } else
        {
            for(i = 0; i <= m; i++)
            {
                for(j = 0; j <= n; j++)
                {
                    temp[i][j] = ope.data[j][i];
                }
            }
        }

        if(m == 0)
        {
            i = 0;
            while(i <= n)
            {
                if(ope.data[0][i] != 0)
                {
                    return 1;
                }
                i += 1;
            }
            return 0;
        }

        double error0;
        error0 = Math.pow(0.1, 10);

        i = 0;

        while(i <= m)
        {
            j = 0;
            while(j <= n)
            {
                if(temp[i][j] != 0)
                {
                    error0 *= temp[i][j];
                    i = m;
                    break;
                }
                j += 1;
            }
            i += 1;
        }

        double error1;
        for(i = 0; i <= m; i++)
        {
            j = 0;
            while(j <= n)
            {
                if(temp[i][j] != 0)
                {
                    break;
                }
                j += 1;
            }

            if(j <= n)
            {
                i1 = 0;
                while(i1 <= m)
                {
                    if(temp[i1][j] != 0 && i1 != i)
                    {
                        temp1 = temp[i][j]/temp[i1][j];
                        error1 = Math.abs((temp[i][j] - temp[i1][j]*temp1))*100;
                        error1 += error0;
                        for(j1 = 0; j1 <= n; j1++)
                        {
                            temp[i1][j1] = temp[i][j1] - temp[i1][j1]*temp1;
                            if(Math.abs(temp[i1][j1]) < error1)
                            {
                                temp[i1][j1] = 0;
                            }
                        }
                    }
                    i1 += 1;
                }
            }
        }

        i1 = 0;
        for(i = 0; i <= m; i++)
        {
            for(j = 0; j <= n; j++)
            {
                if(temp[i][j] != 0)
                {
                    i1 += 1;
                    break;
                }
            }
        }
        return i1;
    }

    private void matrixEigValue(Matrix ope) {
        /**
        * @author: hirmy
        * @description: 求矩阵特征值；要求：矩阵行列式非0
        * @date: 2023/12/3 13:00
        * @return void
        */

        double[][] paraMatrix = ope.data;
        int tempM = ope.row;
        int tempN = ope.col;
        Matrix res = new Matrix(tempM,tempN);
        res.row = tempM;
        res.col = tempN;
        int[] tempIndexQ = arrayIndexAuto(tempM);
        int[] tempIndexR = arrayIndexAuto(tempM, tempM + tempN);
        for (int i = 0; i < 1000; i++) {
            double[][] tempSummary = matrixQrDecomposition(paraMatrix);
            double[][] tempMatrixQ = arrayRowValue(tempSummary, tempIndexQ);
            double[][] tempMatrixR = arrayRowValue(tempSummary, tempIndexR);
            paraMatrix = matrixCrossProduct_tool(tempMatrixR, tempMatrixQ);
        } // Of for i
        res.data = paraMatrix;
        matrixC = res;
    }// Of matrixEigValue

    private double[][] matrixQrDecomposition(double[][] paraMatrix) {
        /**
        * @author: hirmy
        * @description: 工具用方法，矩阵QR分解
        * @date: 2023/12/9 21:47
        * @return double[][]
        */
        double[][] tempOrthogonalMatrix = matrixTranspose(matrixGramSchimidt(paraMatrix));
        int tempM = tempOrthogonalMatrix.length;
        int tempN = tempOrthogonalMatrix[0].length;

        double[][] tempMatrixQ = new double[tempM][tempN];
        for (int i = 0; i < tempM; i++) {
            double tempMag = magnitude(tempOrthogonalMatrix[i]);
            for (int j = 0; j < tempN; j++) {
                tempMatrixQ[i][j] = tempOrthogonalMatrix[i][j] / tempMag;
            } // Of for j
        } // Of for i

        double[][] tempMatrixR = matrixCrossProduct_tool(tempMatrixQ, paraMatrix);
        double[][] resultSummary = new double[tempM + tempN][tempM];
        for (int i = 0; i < tempN; i++) {
            for (int j = 0; j < tempM; j++) {
                resultSummary[i][j] = tempMatrixQ[j][i];
            } // Of for j
        } // Of for i

        for (int i = tempN; i < resultSummary.length; i++) {
            for (int j = 0; j < tempM; j++) {
                resultSummary[i][j] = tempMatrixR[i - tempN][j];
            } // Of for j
        } // Of for i

        return resultSummary;
    }

    private double[][] matrixGramSchimidt(double[][] paraMatrix) {

        double[][] tempTransposedMatrix = matrixTranspose(paraMatrix);
        int tempM = tempTransposedMatrix.length;
        int tempN = tempTransposedMatrix[0].length;

        double[][] resultMatrix = new double[tempM][tempN];
        double tempValue = 0;
        double tempFactor = 0;
        for (int i = 0; i < tempM; i++) {
            for (int j = 0; j < tempN; j++) {
                tempValue = tempTransposedMatrix[i][j];
                for (int k = 0; k < i; k++) {
                    tempFactor = (1. * arrayMultiplyAndAdd(tempTransposedMatrix[i], resultMatrix[k]))
                            / arrayMultiplyAndAdd(resultMatrix[k], resultMatrix[k]);
                    tempValue -= tempFactor * resultMatrix[k][j];
                } // Of for k
                resultMatrix[i][j] = tempValue;
            } // Of for j
        } // Of for i

        return matrixTranspose(resultMatrix);
    }
    private double arrayMultiplyAndAdd(double[] paraFirstArray, double[] paraSecondArray) {
        /**
        * @author: hirmy
        * @description: 工具用方法向量的点乘
        * @date: 2023/12/3 13:00
        * @return double
        */
        int tempM = paraFirstArray.length;
        double resultMultipliedArray = 0;

        for (int i = 0; i < tempM; i++) {
            resultMultipliedArray += paraFirstArray[i] * paraSecondArray[i];
        } // Of for i

        return resultMultipliedArray;
    }
    private double magnitude(double[] paraMatrix) {
        /**
        * @author: hirmy
        * @description: 工具用方法，求向量的模
        * @date: 2023/12/9 22:11
        * @return double
        */
        return Math.sqrt(arrayMultiplyAndAdd(paraMatrix, paraMatrix));
    }

    private double[][] arrayRowValue(double[][] paraArray, int[] paraIndex) {
        /**
         * @author: hirmy
         * @description: 工具用方法，用于求特征值
         * @date: 2023/12/9 21:30
         * @return int[]
         */
        int tempParaIndex = paraIndex.length;
        double[][] returnRowValue = new double[tempParaIndex][];

        for (int i = 0; i < returnRowValue.length; i++) {
            returnRowValue[i] = paraArray[paraIndex[i]];
        } // Of for i

        return returnRowValue;
    }
    private  int[] arrayIndexAuto(int paraLen) {
        /**
        * @author: hirmy
        * @description: 工具用方法，用于求特征值
        * @date: 2023/12/9 21:30
        * @return int[]
        */
        int[] returnArray = new int[paraLen];
        for (int i = 0; i < paraLen; i++) {
            returnArray[i] = i;
        }
        return returnArray;
    }

    public static int[] arrayIndexAuto(int paraStrat, int paraEnd) {
        /**
         * @author: hirmy
         * @description: 工具用方法，用于求特征值
         * @date: 2023/12/9 21:30
         * @return int[]
         */
        int[] returnArray = new int[paraEnd - paraStrat];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = i + paraStrat;
        }
        return returnArray;
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



}


