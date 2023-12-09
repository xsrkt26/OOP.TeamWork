package model;

import java.util.ArrayList;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class LinearRegressionModel extends CalculatorModel {
    // inputExpression 参数包含线性回归的自变量X与因变量Y， 中间以 | 分开；

    private ArrayList<Double> xList = new ArrayList<>();
    private ArrayList<Double> yList = new ArrayList<>();

    // 系数长度
    private int coefficientLength;

    // x * y
    private ArrayList<Double> xyList = new ArrayList<>();
    // x ^ 2
    private ArrayList<Double> xSquareList = new ArrayList<>();
    // y ^ 2
    private ArrayList<Double> ySquareList = new ArrayList<>();

    // x 平均数
    private Double xAverage;
    // y 平均数
    private Double yAverage;
    // x * y 平均数；
    private Double xyAverage;

    // x 平均数的平方
    private Double xAverageSquare;
    // y 平均数的平方
    private Double yAverageSquare;

    // x 平方的平均数
    private Double xSquareAverage;
    // y 平方的平均数
    private Double ySquareAverage;

    // B 回归系数
    private Double regressionCoefficientB;
    // A 回归系数
    private Double regressionCoefficientA;

    // R 相关系数
    private Double correlationCoefficientR;

    /**
     * @author: kiyotaka
     * @description: get inputExpression;
     * @date: 2023/12/3 11:06
     */
    public LinearRegressionModel(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    /**
     * @return
     * @author: kiyotake
     * @description: 输入发生变化时，更新输入数据
     * @date: 2023/12/9 14:47
     */
    public void upDateInputExpression(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    @Override
    /**
     * @author: kiyotaka
     * @description: 综合逻辑：1.
     * @date: 2023/12/3 13:00
     * @return void
     */
    public void count() {
        splitInputExpression();
        countData();
        transDataIntoString();
    }

    private void transDataIntoString() {

    }

    /**
     * @return
     * @author: kiyotaka
     * @description: 计算数据
     * @date: 2023/12/9 14:57
     */
    private void countData() {
        coefficientLength = xList.size();
        double xSum = 0, ySum = 0, xSquareSum = 0, ySquareSum = 0, xySum = 0;
        for (int i = 0; i < coefficientLength; i++) {
            double x = xList.get(i);
            double y = yList.get(i);
            xyList.add(x * y);
            xSquareList.add(x * x);
            ySquareList.add(y * y);

            xSum += x;
            ySum += y;
            xySum += xyList.get(i);
            xSquareSum += xSquareList.get(i);
            ySquareSum += ySquareList.get(i);
        }
        xAverage = xSum / coefficientLength;
        yAverage = ySum / coefficientLength;
        xyAverage = xySum / coefficientLength;
        xSquareAverage = xSquareSum / coefficientLength;
        ySquareAverage = ySquareSum / coefficientLength;
        xAverageSquare = xAverage * xAverage;
        yAverageSquare = yAverage * yAverage;

        regressionCoefficientB = (xAverage * yAverage - xyAverage) / (xAverageSquare - xSquareAverage);
        regressionCoefficientA = yAverage - regressionCoefficientB * xAverage;
        correlationCoefficientR = (xyAverage - xAverage * yAverage) /
                Math.sqrt((xSquareAverage - xAverageSquare) * (ySquareAverage - yAverageSquare));
    }

    @Override
    public String outputAns() {
        return outputAnswer;
    }


    /**
     * @return
     * @author: kiyotaka
     * @description: 当自变量的数量与因变量的数量不一致时，输入不合法
     * @date: 2023/12/9 13:10
     */
    @Override
    public boolean checkIllegal() {
        return xList.size() == yList.size();
    }

    /**
     * @return
     * @author: kiyotaka
     * @description: 把输入字符串转化为 XY两个字符串，然后分割转化为数字存储在xList ， yList 中
     * @date: 2023/12/9 13:03
     */
    private void splitInputExpression() {
        String[] temp = inputExpression.split("\\|");
        String[] tempX = temp[0].split("\\s+");
        String[] tempY = temp[1].split("\\s+");
        for (String numberX : tempX) {
            double value = Double.parseDouble(numberX);
            xList.add(value);
        }
        for (String numberY : tempY) {
            double value = Double.parseDouble(numberY);
            yList.add(value);
        }
        coefficientLength = xList.size();
    }

    public static void main(String[] args) {
        String A = "100 200 300 400 500 600|0.383 0.420 0.455 0.490 0.524 0.559";
        LinearRegressionModel testModel = new LinearRegressionModel(A);
        testModel.count();
        System.out.println(testModel.checkIllegal());
    }
}