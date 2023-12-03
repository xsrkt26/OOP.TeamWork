package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class GeneralModel extends CalculatorModel{
    private ArrayList<Object> postfixExpression = new ArrayList<>();
    private static HashMap<String, Integer> operationPriority = new HashMap<>();

    static {
        operationPriority.put("-", 1);
        operationPriority.put("+", 1);
        operationPriority.put("*", 2);
        operationPriority.put("/", 2);
        operationPriority.put("mod", 2);
        operationPriority.put("^", 3);
        operationPriority.put("log", 4);
        operationPriority.put("ln", 4);
        operationPriority.put("sin", 4);
        operationPriority.put("cos", 4);
        operationPriority.put("tan", 4);
        operationPriority.put("!", 4);
        operationPriority.put("abs", 4);
        operationPriority.put("%", 4);
    }


    @Override
    public void count() {
        transToPostfix();

    }

    @Override
    public String outputAns() {
        return null;
    }

    @Override
    public boolean checkIllegal() {
        return false;
    }

    private static void transToPostfix() {

    }

    private static double readNumber() {
        
    }
}
