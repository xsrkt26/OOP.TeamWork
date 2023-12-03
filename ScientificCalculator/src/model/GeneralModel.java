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

    static  {
        
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
