package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class GeneralModel extends CalculatorModel{
    final double EP = 1e-10;//用于进行浮点数相等比较
    private ArrayList<Object> postfixExpression = new ArrayList<>();
    private static HashMap<String, Integer> operationPriority = new HashMap<>();
    private static HashMap<String, Integer> operationAry_N = new HashMap<>();
    //op为 1或2 元运算符
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

    static {
        /**
        * @author: hirmy
        * @description: 某计算符为 1或2 元计算符
        * @date: 2023/12/9 14:49
        */
        operationAry_N.put("-",2);
        operationAry_N.put("+",2);
        operationAry_N.put("*",2);
        operationAry_N.put("/",2);
        operationAry_N.put("mod",2);
        operationAry_N.put("^",2);
        operationAry_N.put("log",1);
        operationAry_N.put("ln",1);
        operationAry_N.put("sin",1);
        operationAry_N.put("cos",1);
        operationAry_N.put("tan",1);
        operationAry_N.put("!",1);
        operationAry_N.put("abs",1);
        operationAry_N.put("%",1);
    }

    @Override
    public void count() {
        /**
        * @author: hirmy
        * @description: 根据transToPostfix()修改后的list，进行后缀表达式的计算
        * @date: 2023/12/9 14:50
        * @return void
        */
        transToPostfix();
        Stack<Double> stack = new Stack<Double>();
        for(Object o : postfixExpression){
            if(o instanceof Double){
                stack.push((double)o);
            }
            else{
                String op = (String)o;
                if(operationAry_N.get(op) == 1){
                    try{
                        double ope = stack.pop();
                        double ans = 0;
                        ans = calculate(op,ope);
                        stack.push(ans);
                    }catch(ArithmeticException e){
                        outputAnswer = "NaN";
                        outputAns();
                        return;
                    }
                }
                else if(operationAry_N.get(op) == 2) {
                    try{
                        double ope2 = stack.pop();
                        double ope1 = stack.pop();
                        double ans = 0;
                        ans = calculate(op,ope1,ope2);
                        stack.push(ans);
                    }catch (ArithmeticException e){//捕获异常，如:除0
                        outputAnswer = "NaN";
                        outputAns();
                        return;
                    }
                }
            }
        }
        outputAnswer = String.valueOf(stack.pop());
        outputAns();
        return;
    }

    private double calculate(String op, double ope){
        /**
        * @author: hirmy
        * @description: 进行一元运算
        * @date: 2023/12/9 14:50
        * @return double
        */
        double ans = 0;
        switch(op){
            case "log":
                ans = Math.log(ope) / Math.log(10);
                break;
            case "ln":
                ans = Math.log(ope);
                break;
            case "sin":
                ans = Math.sin(ope);
                break;
            case "cos":
                ans = Math.cos(ope);
                break;
            case "tan":
                ans = Math.tan(ope);
                break;
            case "!":
                ans = factorial(ope);
                break;
            case "abs":
                ans = Math.abs(ope);
                break;
            case "%":
                ans = ope * 0.01;
                break;
            default:
                break;
        }
        return ans;
    }

    private double calculate(String op, double ope1, double ope2){
        /**
         * @author: hirmy
         * @description: 进行二元运算
         * @date: 2023/12/9 14:50
         * @return double
         */
        double ans = 0;
        switch(op){
            case "+":
                ans = ope1 + ope2;
                break;
            case "-":
                ans = ope1 - ope2;
                break;
            case "*":
                ans = ope1 * ope2;
                break;
            case "/":
                ans = ope1 / ope2;
                break;
            case "mod":
                ans = ope1 % ope2;
                break;
            case "^":
                ans = Math.pow(ope1,ope2);
                break;
            default:
                break;
        }
        return ans;
    }

    private double factorial(double ope){
        /**
         * @author: hirmy
         * @description: 进行ope的阶乘运算
         * @date: 2023/12/9 14:50
         * @return double
         */
        if(Math.abs(ope - 0) < EP){
            return 1;
        }
        else if(Math.abs(ope - 1) < EP){
            return 1;
        }
        else{
            return ope * factorial(ope-1);
        }
    }

    @Override
    public String outputAns() {
        return outputAnswer;
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
