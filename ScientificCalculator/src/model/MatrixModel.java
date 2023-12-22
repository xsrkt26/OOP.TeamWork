package model;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：kiyotaka
 * @description：Matrix模块中,输入字符串的形式如下：“identifier(0 or 1, repersent 1 matrix or 2 matrix)
 * | 19 124 141 12 / 12 214 214 213" 表示一个2 * 4 的矩阵，可以进行一元运算。
 * @date ：2023/11/28 17:41
 */
public class MatrixModel extends CalculatorModel{
    final double EP = 1e-10;
    final String NaN = "invalid syntax";

    // 判断该矩阵Model 是一元运算还是二元运算，0是一元；
    int matrixOperation;

    class Matrix{
        int row;
        int col;
        double[][] data;

        @Override
        public String toString(){
            /**
            * @author: hirmy

            * @description: 矩阵格式化输出
            * @date: 2023/12/3 13:00
            * @return String
            */
            if (this.row == 0 && this.col == 0) {
                return "0|" + NaN;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1|");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++ ){
                    stringBuilder.append(String.valueOf(data[i][j])).append(' ');
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }

        public void setRowCol(int row, int col) {
            this.row = row;
            this.col = col;
            data = new double[row][col];
        }

        public Matrix() {
            this.row = 0;
            this.col = 0;
        }

        public Matrix(int row, int col) {
            this.row = row;
            this.col = col;
            data = new double[row][col];
        }


        public Matrix(String[] matrixString) {
           /**
           * @author: hirmy
           * @description: 构造矩阵，参数为格式化矩阵
           * @date: 2023/12/3 13:00
           */
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

        public boolean equalSize(Matrix A) {
            return this.row == A.row && this.col == A.col;
        }
        
        public boolean crossEqualSize(Matrix A) {
            return this.col == A.row;
        }

        public boolean isPhalanx() {
            return this.col == this.row;
        }
    }

    // 进行运算的矩阵A
    private Matrix matrixA;
    // 进行运算的矩阵B
    private Matrix matrixB;

    // A + B
    private String matrixAddAnswer;

    // A - B
    private String matrixSubAnswer;
    // A CrossProduct B
    private String matrixCrossProductAnswer;
    // A dotProduct B
    private String matrixDotProductAnswer;
    // A transposeMatrix
    private String matrixTransposeAnswer;
    // A 行列式
    private String matrixDeterminantAnswer;
    // A 伴随矩阵
    private String matrixAdjointAnswer;
    // A 逆矩阵
    private String matrixInverseAnswer;
    // A 迹
    private String matrixTraceAnswer;
    // A rank
    private String matrixRankAnswer;
    // A 特征值
    private String matrixEigValueAnswer;

    public MatrixModel(String inputExpression) {
        outputMap = new HashMap<>();
        this.inputExpression = inputExpression;
    }

    public MatrixModel(){
        outputMap = new HashMap<>();
    }

    public void setInputExpression(String s){
        outputMap.clear();
        this.inputExpression = s;
    }
    @Override
    public void count() {
        /**
        * @author: hirmy
        * @description: 进行运算
        * @date: 2023/12/4
        * @return void
        */
        if (checkIllegal()) {
            if (matrixOperation == 0) {
                countDataForOne();
            }
            else {
                countDataForTwo();
            }
            saveDataToMap();
        }
    }

    private void saveDataToMap(){
        /**
        * @author: hirmy
        * @description: 答案保存到Map里
        * @date: 2023/12/4
        * @return void
        */
        if (matrixOperation == 1) {
            outputMap.put("matrixAddAnswer", matrixAddAnswer);
            outputMap.put("matrixSubAnswer", matrixSubAnswer);
            outputMap.put("matrixCrossProductAnswer", matrixCrossProductAnswer);
            outputMap.put("matrixDotProductAnswer", matrixDotProductAnswer);
        }
        else {
            outputMap.put("matrixTransposeAnswer", matrixTransposeAnswer);
            outputMap.put("matrixDeterminantAnswer", matrixDeterminantAnswer);
            outputMap.put("matrixAdjointAnswer", matrixAdjointAnswer);
            outputMap.put("matrixInverseAnswer", matrixInverseAnswer);
            outputMap.put("matrixTraceAnswer", matrixTraceAnswer);
            outputMap.put("matrixRankAnswer", matrixRankAnswer);
            outputMap.put("matrixEigValueAnswer", matrixEigValueAnswer);
        }
    }

    @Override
    public Map<String, String> getOutPutMap() {
        return this.outputMap;
    }

    private void countDataForTwo() {
        /**
        * @author: hirmy
        * @description: 二元运算
        * @date: 2023/12/4
        * @return void
        */
        matrixAdd(matrixA, matrixB);
        matrixSub(matrixA, matrixB);
        matrixCrossProduct(matrixA, matrixB);
        matrixDotProduct(matrixA, matrixB);
    }

    private void countDataForOne() {
        /**
        * @author: hirmy
        * @description: 一元运算
        * @date: 2023/12/4
        * @return void
        */
        matrixTranspose(matrixA);
        matrixDeterminant(matrixA);
        matrixAdjoint(matrixA);
        matrixInverse(matrixA);
        matrixTrace(matrixA);
        Rank(matrixA);
        matrixEigValue(matrixA);
    }


    private void matrixAdd(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵加法；要求：两矩阵i，j相等
        * @date: 2023/12/9 13:00
        * @return void
        */
        Matrix result = new Matrix();
        if (ope1.equalSize(ope2)) {
            result.setRowCol(ope1.row, ope1.col);
            for(int i = 0; i < result.row; i++){
                for(int j = 0; j < result.col; j++){
                    result.data[i][j] = ope1.data[i][j] + ope2.data[i][j];
                }
            }
        }
        matrixAddAnswer = result.toString();
    }

    private void matrixSub(Matrix ope1, Matrix ope2){
        /**
        * @author: hirmy
        * @description: 矩阵减法；要求：两矩阵i，j相等
        * @date: 2023/12/9 15:53
        * @return void
        */
        // ！注意运算顺序：ope1 - ope2
        Matrix result = new Matrix();
        if (ope1.equalSize(ope2)) {
            result.setRowCol(ope1.row, ope1.col);
            for(int i = 0; i < result.row; i++){
                for(int j = 0; j < result.col; j++){
                    result.data[i][j] = ope1.data[i][j] - ope2.data[i][j];
                }
            }
        }
        matrixSubAnswer = result.toString();
    }

    private void matrixCrossProduct(Matrix ope1, Matrix ope2){
        /**
         * @author: hirmy
         * @description: 矩阵叉乘；要求：ope1.col == ope2.row
         * @date: 2023/12/9 16:20
         * @return void
         */
        Matrix result = new Matrix();
        if (ope1.crossEqualSize(ope2)) {
            int i, j, k;
            int rowA = ope1.row;
            int total = ope1.col;//total == ope1.col == ope2.row
            int colB = ope2.col;
            result.setRowCol(rowA, colB);
            for (i = 0; i < rowA; i++) {
                for (j = 0; j < colB; j++) {
                    double temp = 0;
                    for (k = 0; k < total; k++) {
                        temp += ope1.data[i][k] * ope2.data[k][j];
                    }
                    result.data[i][j] = temp;
                }
            }
        }
        matrixCrossProductAnswer = result.toString();
    }

    private double[][] matrixCrossProductTool(double[][] ope1, double[][] ope2){
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
        Matrix result = new Matrix();
        if (ope1.equalSize(ope2)) {
            result.setRowCol(ope1.row, ope1.col);
            for(int i = 0; i < result.row; i++){
                for(int j = 0; j < result.col; j++){
                    result.data[i][j] = ope1.data[i][j] * ope2.data[i][j];
                }
            }
        }
        matrixDotProductAnswer = result.toString();
    }

    private void matrixTranspose(Matrix ope){
        /**
        * @author: hirmy
        * @description: 矩阵转置；要求：暂无
        * @date: 2023/12/9 16:35
        * @return void
        */
        int row = ope.row;
        int col = ope.col;
        Matrix result = new Matrix(col, row);
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                result.data[j][i] = ope.data[i][j];
            }
        }
        matrixTransposeAnswer = result.toString();
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
        double result = 0;
        if (!ope.isPhalanx()) {
            matrixDeterminantAnswer = "0|" + NaN;
            return result;
        }
        int rank = ope.row;

        if(rank == 1){
            //若一阶矩阵，直接返回
            result = ope.data[0][0];
        }
        else if(rank == 2){
            //二阶矩阵，直接计算
            result =  ope.data[0][0] * ope.data[1][1] - ope.data[0][1] * ope.data[1][0];
        }
        else {
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
        }
        matrixDeterminantAnswer = "1|" + String.valueOf(result);
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
        for(int i = 0; i < ope.row; i++){
            if(i != row){
                realJ = 0;
                for(int j = 0; j < ope.col; j++){
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
        Matrix result = new Matrix();
        if (ope.isPhalanx()) {
            int rank = ope.row;
            result.setRowCol(rank,rank);
            for(int i = 0; i < rank; i++){
                for(int j = 0; j < rank; j++){
                    result.data[j][i] = Math.pow(-1,i+j) * matrixDeterminant(minorMatrix(i,j,ope));
                }
            }
        }
        matrixAdjointAnswer = result.toString();
    }

    private Matrix matrixAdjointTool(Matrix ope){
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

    /**
     * @author: hirmy
     * @description: 求逆矩阵；要求：i == j
     * @date: 2023/12/9 17:08
     * @return void
     */
    private void matrixInverse(Matrix ope){
        /**
        * @author: hirmy
        * @description: 矩阵求逆
        * @date: 2023/12/9
        * @return void
        */
        Matrix result = new Matrix();
        if (ope.isPhalanx() && Math.abs(matrixDeterminant(ope) - 0) > EP) {
            double det = matrixDeterminant(ope);
            Matrix adj = matrixAdjointTool(ope);
            if(Math.abs(det-0) < EP){
                // TODO outputMap = "NaN";
                matrixInverseAnswer = "0|" + NaN;
                return;
            }
            Matrix temp = matrixDiv(adj,det);
            for(int i = 0; i < temp.row; i++){//保留三位小数
                for(int j = 0; j < temp.col; j++){
                    temp.data[i][j] = getDoubleApproximation(temp.data[i][j],3);
                    if(Math.abs(temp.data[i][j] - 0) < EP){
                        temp.data[i][j] = 0;
                    }
                }
            }
        }
        matrixInverseAnswer = result.toString();
    }

    private void matrixTrace(Matrix ope){
        /**
        * @author: hirmy
        * @description: 矩阵求迹；要求：方阵
        * @date: 2023/12/9 18:19
        * @return double
        */
        if(!ope.isPhalanx()){
            matrixTraceAnswer = "0|" + NaN;
        }
        else {
            int rank = ope.row;
            double res = 0;
            for(int i = 0; i < rank; i++){
                res += ope.data[i][i];
            }
            matrixTraceAnswer = "1|" + String.valueOf(res);
        }
    }

    private void Rank(Matrix ope){
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
        StringBuilder stringBuilder = new StringBuilder("1|");
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
                    stringBuilder.append("1");
                    matrixRankAnswer = stringBuilder.toString();
                    return;
                    //return 1;
                }
                i += 1;
            }
            stringBuilder.append("0");
            matrixRankAnswer = stringBuilder.toString();
            return;
            //return 0;
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
        stringBuilder.append(String.valueOf(i1));
        matrixRankAnswer = stringBuilder.toString();
    }

    private int Hessenberg(double[][] Matrix,int n,double[][]ret)
    {
        /**
        * @author: hirmy
        * @description: 工具用，计算矩阵 特征值
        * @date: 2023/12/5
        * @return void

        */

        int i;
        int j;
        int k;
        double temp;
        int MaxNu;
        n-=1;
        for(k=1;k<=n-1;k++)
        {
            i=k-1;
            MaxNu=k;
            temp=Math.abs(Matrix[k][i]);
            for(j=k+1;j<=n;j++)
            {
                if(Math.abs(Matrix[j][i])>temp)
                {
                    MaxNu=j;
                }
            }
            ret[0][0]=Matrix[MaxNu][i];
            i=MaxNu;
            if(ret[0][0]!=0)
            {
                if(i!=k)
                {
                    for(j=k-1;j<=n;j++)
                    {
                        temp=Matrix[i][j];
                        Matrix[i][j]=Matrix[k][j];
                        Matrix[k][j]=temp;
                    }
                    for(j=0;j<=n;j++)
                    {
                        temp=Matrix[j][i];
                        Matrix[j][i]=Matrix[j][k];
                        Matrix[j][k]=temp;
                    }
                }
                for(i=k+1;i<=n;i++)
                {
                    temp=Matrix[i][k-1]/ret[0][0];
                    Matrix[i][k-1]=0;
                    for(j=k;j<=n;j++)
                    {
                        Matrix[i][j]-=temp*Matrix[k][j];
                    }
                    for(j=0;j<=n;j++)
                    {
                        Matrix[j][k]+=temp*Matrix[j][i];
                    }
                }
            }
        }
        for(i=0;i<=n;i++)
        {
            for(j=0;j<=n;j++)
            {
                ret[i][j]=Matrix[i][j];
            }
        }
        return n+1;
    }

    private boolean EigenValue(Matrix ope,double[][] Ret)
    {
        /**
        * @author: hirmy
        * @description: 工具用，计算矩阵特征值
        * @date: 2023/12/5
        * @return boolean
        */
        double[][] Matrix = ope.data;
        int n = ope.row;
        int Erro = 4;
        int LoopNu = 400;
        int i=Matrix.length;
        if(i!=n)
        {
            return false;
        }
        int j;
        int k;
        int t;
        int m;
        double[][]A=new double[n][n];
        double erro=Math.pow(0.1, Erro);
        double b;
        double c;
        double d;
        double g;
        double xy;
        double p;
        double q;
        double r;
        double x;
        double s;
        double e;
        double f;
        double z;
        double y;
        int loop1=LoopNu;
        Hessenberg(Matrix,n,A);
        m=n;
        while(m!=0)
        {
            t=m-1;
            while(t>0)
            {
                if(Math.abs(A[t][t-1])>erro*(Math.abs(A[t-1][t-1])+Math.abs(A[t][t])))
                {
                    t-=1;
                }
                else
                {
                    break;
                }
            }
            if(t==m-1)
            {
                Ret[m-1][0]=A[m-1][m-1];
                Ret[m-1][1]=0;
                m-=1;
                loop1=LoopNu;

            }
            else if(t==m-2)
            {
                b=-(A[m-1][m-1]+A[m-2][m-2]);
                c=A[m-1][m-1]*A[m-2][m-2]-A[m-1][m-2]*A[m-2][m-1];
                d=b*b-4*c;
                y=Math.pow(Math.abs(d), 0.5);
                if(d>0)
                {
                    xy=1;
                    if(b<0)
                    {
                        xy=-1;
                    }
                    Ret[m-1][0]=-(b+xy*y)/2;
                    Ret[m-1][1]=0;
                    Ret[m-2][0]=c/Ret[m-1][0];
                    Ret[m-2][1]=0;
                }
                else
                {
                    Ret[m-1][0]=-b/2;
                    Ret[m-2][0]=Ret[m-1][0];
                    Ret[m-1][1]=y/2;
                    Ret[m-2][1]=-Ret[m-1][1];
                }
                m-=2;
                loop1=LoopNu;
            }
            else
            {
                if(loop1<1)
                {
                    return false;
                }
                loop1-=1;
                j=t+2;
                while(j<m)
                {
                    A[j][j-2]=0;
                    j+=1;
                }
                j=t+3;
                while(j<m)
                {
                    A[j][j-3]=0;
                    j+=1;
                }
                k=t;
                while(k<m-1)
                {
                    if(k!=t)
                    {
                        p=A[k][k-1];
                        q=A[k+1][k-1];
                        if(k!=m-2)
                        {
                            r=A[k+2][k-1];
                        }
                        else
                        {
                            r=0;
                        }
                    }
                    else
                    {
                        b=A[m-1][m-1];
                        c=A[m-2][m-2];
                        x=b+c;
                        y=c*b-A[m-2][m-1]*A[m-1][m-2];
                        p=A[t][t]*(A[t][t]-x)+A[t][t+1]*A[t+1][t]+y;
                        q=A[t+1][t]*(A[t][t]+A[t+1][t+1]-x);
                        r=A[t+1][t]*A[t+2][t+1];
                    }
                    if(p!=0||q!=0||r!=0)
                    {
                        if(p<0)
                        {
                            xy=-1;
                        }
                        else
                        {
                            xy=1;
                        }
                        s=xy*Math.pow(p*p+q*q+r*r, 0.5);
                        if(k!=t)
                        {
                            A[k][k-1]=-s;
                        }
                        e=-q/s;
                        f=-r/s;
                        x=-p/s;
                        y=-x-f*r/(p+s);
                        g=e*r/(p+s);
                        z=-x-e*q/(p+s);
                        for(j=k;j<=m-1;j++)
                        {
                            b=A[k][j];
                            c=A[k+1][j];
                            p=x*b+e*c;
                            q=e*b+y*c;
                            r=f*b+g*c;
                            if(k!=m-2)
                            {
                                b=A[k+2][j];
                                p+=f*b;
                                q+=g*b;
                                r+=z*b;
                                A[k+2][j]=r;
                            }
                            A[k+1][j]=q;
                            A[k][j]=p;

                        }
                        j=k+3;
                        if(j>=m-1)
                        {
                            j=m-1;
                        }
                        for(i=t;i<=j;i++)
                        {
                            b=A[i][k];
                            c=A[i][k+1];
                            p=x*b+e*c;
                            q=e*b+y*c;
                            r=f*b+g*c;
                            if(k!=m-2)
                            {
                                b=A[i][k+2];
                                p+=f*b;
                                q+=g*b;
                                r+=z*b;
                                A[i][k+2]=r;
                            }
                            A[i][k+1]=q;
                            A[i][k]=p;
                        }
                    }
                    k+=1;
                }

            }

        }
        return true;
    }


    private void matrixEigValue(Matrix ope) {
        /**
        * @author: hirmy
        * @description: 求矩阵特征值,返回矩阵的对角线元素即为答案；要求：矩阵行列式非0
        * @date: 2023/12/9 23:47
        * @return void
        */
        if(!ope.isPhalanx()){
            matrixEigValueAnswer = "0|" + NaN;
        }
        else if (Math.abs(matrixDeterminant(ope) - 0) < EP) {
            int rank = ope.row;
            double[][] temp = new double[rank][rank];
            EigenValue(ope,temp);
            for(int i = 0; i < rank; i++){
                for(int j = 0; j < rank; j++){
                    temp[i][j] = getDoubleApproximation(temp[i][j],3);
                    if(Math.abs(temp[i][j] - 0) < EP){
                        temp[i][j] = 0;
                    }
                }
            }
            Matrix tempMatrix = new Matrix(rank,rank);
            tempMatrix.data = temp;
            matrixEigValueAnswer = "1|";
            for (int i = 0; i < rank; i++) {
                matrixEigValueAnswer += String.valueOf(tempMatrix.data[i][0]) + " ";
            }
        }
        else{
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
                paraMatrix = matrixCrossProductTool(tempMatrixR, tempMatrixQ);
            }
            for(int i = 0; i < tempM; i++){//进行四舍五入
                for(int j = 0; j < tempN; j++){
                    paraMatrix[i][j] = getDoubleApproximation(paraMatrix[i][j],3);
                    if(Math.abs(paraMatrix[i][j] - 0) < EP){
                        paraMatrix[i][j] = 0;
                    }
                }
            }
            for(int i = 0; i < tempM; i++){
                for(int j = 0; j < tempN; j++){
                    if(i != j){
                        paraMatrix[i][j] = 0;
                    }
                }
            }
            res.data = paraMatrix;
            matrixEigValueAnswer = "1|";
            for (int i = 0; i < tempM; i++) {
                matrixEigValueAnswer += String.valueOf(res.data[i][i]) + " ";
            };
        }

    }

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

        double[][] tempMatrixR = matrixCrossProductTool(tempMatrixQ, paraMatrix);
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

    private double getDoubleApproximation(double input, int digits) {
        /**
        * @author: hirmy
        * @description: 工具用方法，求double的近似值
        * @date: 2023/12/9 22:36
        * @return double
        */
        double result = 0;
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(digits);//指定四舍五入的位数

        String temp = format.format(input);
        result = Double.parseDouble(temp);

        return result;
    }
    private double[][] matrixGramSchimidt(double[][] paraMatrix) {
        /**
        * @author: hirmy
        * @description: 工具用方法,用于QR分解
        * @date: 2023/12/9 22:53
        * @return double
        */

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
                }
                resultMatrix[i][j] = tempValue;
            }
        }

        return matrixTranspose(resultMatrix);
    }
    private double arrayMultiplyAndAdd(double[] paraFirstArray, double[] paraSecondArray) {
        /**
        * @author: hirmy
        * @description: 工具用方法,向量的点乘
        * @date: 2023/12/3 13:00
        * @return double
        */
        int tempM = paraFirstArray.length;
        double resultMultipliedArray = 0;

        for (int i = 0; i < tempM; i++) {
            resultMultipliedArray += paraFirstArray[i] * paraSecondArray[i];
        }

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
        }

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
            //0:一元矩阵运算
            String[] matrixString = temp[1].split("/");
            if (!checkMatrixString(matrixString)) {
                return false;
            }
            matrixA = new Matrix(matrixString);
            matrixB = null;
        }
        else {
            //1：二元矩阵运算
            String[] matrixStringA = temp[1].split("/");
            String[] matrixStringB = temp[2].split("/");
            if (!checkMatrixString(matrixStringA) || !checkMatrixString(matrixStringB)){
                return false;
            }
            matrixA = new Matrix(matrixStringA);
            matrixB = new Matrix(matrixStringB);
        }
        return true;
    }


    private boolean checkMatrixString(String[] matrixString) {
        /**
         * @author: kiyotaka
         * @description: 判断组成矩阵的字符串是否合法
         * @date: 2023/12/9 16:40
         * @return
         */
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


