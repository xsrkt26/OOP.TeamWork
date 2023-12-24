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
    /**
     * @author: kiyotaka
     *  该参数存储输入表达式
     * @date: 2023/12/23 11:44
     */
    String inputExpression;

    /**
     * @author: kiyotaka
     *  答案存储的该map中，需要调用时使用Controller中的函数调用
     * @date: 2023/12/23 11:45
     */
    Map<String, String> outputMap = new HashMap<>();

    /**
     * @author: kiyotaka
     *  核心运算部分
     * @date: 2023/12/23 11:50
     * @return Void
     */
    public abstract void count();

    /**
     * @author: kiyotaka
     *  Controller调用该函数得到答案
     * @date: 2023/12/23 11:50
     * @return Map
     */
    public abstract Map<String, String> getOutPutMap();

    /**
     * @author: kiyotaka
     *  检查输入字符串合法性。
     * @date: 2023/12/23 11:55
     * @return boolean
     */
    public abstract boolean checkIllegal();
}