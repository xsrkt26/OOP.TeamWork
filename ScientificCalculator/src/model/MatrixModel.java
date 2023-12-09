package model;

import java.util.ArrayList;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class MatrixModel extends CalculatorModel{

    class Matrix{
        int row;
        int col;
        double[][] data;
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
        Matrix ans = new Matrix();
        ans.col = col;
        ans.row = row;
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
        Matrix ans = new Matrix();
        ans.col = col;
        ans.row = row;
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
        Matrix res = new Matrix();
        res.row = 1;
        res.col = col;
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
        Matrix res = new Matrix();
        res.row = rowA;
        res.col = colB;
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
        Matrix res = new Matrix();
        res.row = row;
        res.col = col;
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
        int m = ope.row;
        int n = ope.col;
        Matrix res = new Matrix();
        res.row = n;
        res.col = m;
        for(i = 0; i < m; i++){
            for(j = 0; j < n; j++){
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
        Matrix res = new Matrix();
        res.row = 
        int[][] a = new int[b.length - 1][b[0].length - 1];
        for (int x = 0, y = 0; x < b.length; x++) {
            if (x == i) {
                continue;
            }
            for (int m = 0,n = 0; m < b[0].length; m++) {
                if (m == j) {
                    continue;
                }
                a[y][n] = b[x][m];
                n++;
            }
            y++;
        }
        return a;
    }
}


