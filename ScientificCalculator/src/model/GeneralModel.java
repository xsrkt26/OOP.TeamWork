package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */
public class GeneralModel extends CalculatorModel{
	    final double EP = 1e-10;//用于进行浮点数相等比较
		private ArrayList<String> postfixExpression = new ArrayList<>();
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
	        operationPriority.put("(", 0);
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

	    public void count() {
	        /**
	        * @author: hirmy
	        * @description: 根据transToPostfix()修改后的list，进行后缀表达式的计算
	        * @date: 2023/12/9 14:50
	        * @return void
	        */
	        transToPostfix();
	        Stack<Double> stack = new Stack<Double>();
	        for(String o : postfixExpression){
	 
	            if(isDouble(o)|| isInteger(o)){
	                stack.push(Double.parseDouble(o));
	            }
	            else{
	                String op = o;
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
	            case "%":
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

	    public String outputAns() {
	        return outputAnswer;
	    }

	    public boolean checkIllegal() {
	        return false;
	    }
	    
	    private void transToPostfix() {
	    	 /**
	         * @author: huihui-ux
	         * @description: 输入转后缀表达式
	         * @date: 2023/12/9 23:08
	         * @return void
	         */
	    	String[] s1 = new String[300];
	    	if(inputExpression.length()>300) {
	    		System.out.println("无效输入");
	    	}
	    	String replaceInput = inputExpression.replaceAll("\\s", "");
	    	StringBuilder sb = new StringBuilder(replaceInput);
	    	int insertIndex ; // 减号后插入空格位置的索引
	    	char charToInsert = ' '; // 要插入的字符
	    	for(int i=1;i<replaceInput.length();i++) {
	    		if(replaceInput.charAt(i)=='-'&&(replaceInput.charAt(i-1)==')'||Character.isDigit(replaceInput.charAt(i-1)))) {// 减号则后面插入空格
	    			insertIndex = i+1;
	    			sb.insert(insertIndex, charToInsert);
	    		}
	    	}
	    	String modifiedString = sb.toString();
	    	Pattern pattern = Pattern.compile("-?\\d+\\.\\d+|-?\\d+|[-+*/%^()t()s()o()l()n()d()a()]");
	    	Matcher matcher = pattern.matcher(modifiedString);
	    	
	    	ArrayList<String> infixExpression = new ArrayList<>();
	    	while (matcher.find()) {
	    		String tmp = matcher.group();
	    		if(tmp.equals("t")) tmp="tan";
	    		else if(tmp.equals("s")) tmp="sin";
	    		else if(tmp.equals("o")) tmp="cos";
	    		else if(tmp.equals("l")) tmp="log";
	    		else if(tmp.equals("n")) tmp="ln";
	    		else if(tmp.equals("d")) tmp="mod";
	    		else if(tmp.equals("a")) tmp="abs";
	    		infixExpression.add(tmp);
	    	}
	    	
	    	Stack<String> opStack = new Stack<>();
	    	
	    	for(String o : infixExpression) {
	    		
	    		if( isDouble(o)|| isInteger(o)){
	                 postfixExpression.add(o);
	            }
	    		else if(o.equals("(")){
	    			opStack.push((String)o);
	    		}
	    		else if(o.equals(")")) {
	    			while(!opStack.peek().equals("(")) {// 到上一个左括号
	    				postfixExpression.add(opStack.pop());
	    			}
	    			opStack.pop();
	    		}
	    		else {// 运算符
	    			while(opStack.size()>0 && operationPriority.get(o)<=operationPriority.get(opStack.peek())) {           //符号栈为空，并且运算符小于等于栈顶的运算符优先级
	    				postfixExpression.add(opStack.pop());
	    			}
	    			opStack.push((String)o);
	    		}
	    	}
	    	while (opStack.size() != 0){
	        	postfixExpression.add(opStack.pop());
	        }
	    }
	   
	    public static boolean isDouble(String input) {  // 判断字符串是否是浮点数
	        return input.matches("-?\\d+(\\.\\d+)?");
	    }

	    
	    public static boolean isInteger(String input) { // 判断字符串是否是整数
	        return input.matches("-?\\d+");
	    }
	    private static double readNumber() {
	        return 0;
	    }
	}

