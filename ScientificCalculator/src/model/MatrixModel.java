package model;

import java.util.ArrayList;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class MatrixModel extends CalculatorModel{
    String[] realExpression;
    ArrayList<Object> itemsInExpression;
    class Matrix{
        int row;
        int col;
        double[][] data;
    }




    private boolean isMatrix(String expression){
        /**
        * @author: hirmy
        * @description: 判断realExpression中的某字符串是否是矩阵
        * @date: 2023/12/9 15:24
        * @return bool
        */
        if(expression.contains(" ")){
            return true;
        }
        return false;
    }
    private Matrix getMatrix(){
        /**
        * @author: hirmy
        * @description: 从inputexpression中获取矩阵
        * @date: 2023/12/9 15:14
        * @return Matrix
        */

    }

    private Matrix matrixAdd(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵加法
        * @date: 2023/12/9 13:00
        * @return void
        */
        int row = ope1.row;
        int col = ope2.col;
        Matrix ans = new Matrix();
        ans.col = col;
        ans.row = row;

    }


}


