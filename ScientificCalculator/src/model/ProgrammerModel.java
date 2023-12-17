package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammerModel extends CalculatorModel{
	/**
	 * @author: huihui-ux
	 * @description: 程序员计算器
	 * @date: 2023/12/12 23:18
	 */
	 int numberSystem=10;//目前的进制,默认为10
	 private ArrayList<String> postfixExpression = new ArrayList<>();
	 private static HashMap<String, Integer> operationPriority = new HashMap<>();//运算符优先级
	 static {
	        operationPriority.put("-", 4);
	        operationPriority.put("+", 4);
	        operationPriority.put("*", 5);
	        operationPriority.put("/", 5);
	        operationPriority.put("<<", 5);
	        operationPriority.put(">>", 5);
	        operationPriority.put("&", 3);
	        operationPriority.put("|", 1);
	        operationPriority.put("~", 7);
	        operationPriority.put(".", 2);//NAND
	        operationPriority.put("NOR", 2);//NOR
	        operationPriority.put("^", 1);//XOR
	        operationPriority.put("(", 0);
	    }
	public void count() {
		transToPostfix();
        Stack<Long> stack = new Stack<Long>();
        for(String o : postfixExpression){
            if(isLong(o)){
            	stack.push(Long.parseLong(o));
            }
            else{
            	 if(o.equals("~")){
	                    try{
	                        long ope = stack.pop();
	                        stack.push(~ope);
	                    }catch(ArithmeticException e){
	                        outputAnswer = "NaN";
	                        outputAns();
	                        return;
	                    }
	                }
	                else  {
	                    try{
	                        long ope2 = stack.pop();
	                        long ope1 = stack.pop();
	                        long ans = 0;
	                        ans = calculate(o,ope1,ope2);
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
	private long calculate(String op, long ope1, long ope2){
        long ans = 0;
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
            	 if(ope2==0){
            		 throw new ArithmeticException("除数不能为0");
     	        }
                ans = ope1 / ope2;
                break;
            case "%": 
            	if(ope2==0){
           		 throw new ArithmeticException("结果未定义");
    	        }
            	ans = ope1%ope2;
                break;
        	case "<<":
    			ans = ope1 << ope2;
    			break;
    		case ">>":
    			ans = ope1 >> ope2;
    			break;
    		case "&":
    			ans = ope1 & ope2;
    			break;
    		case "|":
    			ans = ope1 | ope2;
    			break;
    		case ".":
    			ans = ~(ope1 & ope2);
    			break;
    		case "NOR":
    			ans = ~(ope1 | ope2);
    			break;
    		case "^":
    			ans = ope1 ^ ope2;
    			break;
            default:
                break;
        }
        return ans;
    }

	public String outputAns() {
		return outputAnswer;
	}

	public boolean checkIllegal() {
		return false;
	}

	public void setNumberSystem(int numberSystem) {//设置进制 numberSystem=2,8,10,16
		this.numberSystem = numberSystem;
	}
	 private void transToPostfix() {
    	if(inputExpression.length()>300) {
    		//输入处理
    	}
   
    	String replaceInput = inputExpression.replaceAll("\\s", "");
 
    	StringBuilder sb = new StringBuilder(replaceInput);
    	int insertIndex ; // 减号后插入空格位置的索引
    	char charToInsert = ' '; // 要插入的空格
    	int k = 0;
    	if(replaceInput.charAt(0)=='-') {//首位为负号补0
    		insertIndex = 0;
    		k++;
    		sb.insert(insertIndex, '0');
    		insertIndex = 2;
    		k++;
    		sb.insert(insertIndex, ' ');
    	}
    	for(int i=1;i<replaceInput.length();i++) {
    		if(replaceInput.charAt(i)=='-'&&(replaceInput.charAt(i-1)==')'||Character.isDigit(replaceInput.charAt(i-1)))) {// 减号则后面插入空格
    			insertIndex = i+1+k;
    			k++;
    			sb.insert(insertIndex, charToInsert);
    		}
    		else if(replaceInput.charAt(i)=='-'&&(replaceInput.charAt(i-1)=='(')){//前补0后补空格
    			insertIndex = i+k;
	    		k++;
	    		sb.insert(insertIndex, '0');
	    		insertIndex = i+k+1;
	    		k++;
	    		sb.insert(insertIndex, ' ');
    		}
    	}
    	String modifiedString = sb.toString();
    	Pattern pattern = Pattern.compile("-?\\d+|[-+*/%><&|~\\\\.^()N()]");
    	Matcher matcher = pattern.matcher(modifiedString);
    	
    	ArrayList<String> infixExpression = new ArrayList<>();

    	while (matcher.find()) {
    		String tmp = matcher.group();
    		if(tmp.equals("<")) tmp="<<";
    		else if(tmp.equals(">")) tmp=">>";
    		else if(tmp.equals("N")) tmp = "NOR";
    		infixExpression.add(tmp);
    	}
  
    	Stack<String> opStack = new Stack<>();
    	
    	for(String o : infixExpression) {

    		if( isLong(o)){
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
    			while(opStack.size()>0 && operationPriority.get(o)<=operationPriority.get(opStack.peek())) {  //符号栈为空，并且运算符小于等于栈顶的运算符优先级
    				postfixExpression.add(opStack.pop());
    			}
    			opStack.push((String)o);
    		}
    	}
    	while (opStack.size() != 0){
        	postfixExpression.add(opStack.pop());
        }
    	for(String o:postfixExpression)
    		System.out.print(o+" ");
    	
    }
	 
	 public static boolean isLong(String input) { // 判断字符串是否是整数
	        return input.matches("-?\\d+");
	 }

	
	@Override
	public Map<String, String> getOutPutMap() {
		// TODO 自动生成的方法存根
		return null;
	}


}