package model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public abstract class CalculatorModel {
    String inputExpression;
    Map<String, String> outputMap = new HashMap<>();

    public CalculatorModel() {};
    public CalculatorModel(String inputExpression) {};

    public abstract void count();
    public abstract Map<String, String> getOutPutMap();
    public abstract boolean checkIllegal();


}
