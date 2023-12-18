package model;
import java.util.HashMap;
import java.util.Map;
/**
 * @author ：kiyotaka
 * @description：
 * Model类， 该类接受inputExpression作为计算输入，计算结果保存于outputMap中
 * 需要实现count()计算方法，getOutPutMap()输出结果方法，checkIllegal()检查输入合法性方法。
 * @date ：2023/11/28 17:41
 */
public abstract class CalculatorModel {
    String inputExpression;
    Map<String, String> outputMap = new HashMap<>();

    public abstract void count();
    public abstract Map<String, String> getOutPutMap();
    public abstract boolean checkIllegal();
}
