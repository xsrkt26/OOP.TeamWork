package model;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class LinearRegressionModel extends CalculatorModel{
    private String argument;
    private String dependent;

    /**
     * @author: kiyotaka
     * @description: get inputExpression,and spilt it into two pieces by |;
     * @date: 2023/12/3 11:06
     */
    public LinearRegressionModel(String inputExpression){
        this.inputExpression = inputExpression;
    }

    @Override
    /**
     * @author: kiyotaka
     * @description: TODO
     * @date: 2023/12/3 13:00
     * @return void
     */
    public void count() {

    }

    @Override
    public String outputAns() {
        return outputAnswer;
    }

    @Override
    public boolean checkIllegal() {
        return false;
    }

    public void splitInputExpression() {

    }
}
