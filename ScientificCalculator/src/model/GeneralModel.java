package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author ：kiyotaka
 * @description：TODO
 * @date ：2023/11/28 17:41
 */

public class GeneralModel extends CalculatorModel{
	boolean isRadians = false;
    final double EP = 1e-16;//用于进行浮点数相等比较
	private ArrayList<String> postfixExpression = new ArrayList<>();
	private static HashMap<String, Integer> operationPriority = new HashMap<>();
	private static HashMap<String, Integer> operationAry_N = new HashMap<>();
	//op为 1或2 元运算符
	/*	键盘快捷键输入
	 * 
	 *  t->tan
	 *  s->sin
	 *  o->cos
	 *  u->sec
	 *  i->csc
	 *  j->cot
	 *  shift+t->atan
	 *  shift+s->asin
	 *  shift+o->acos
	 *  shift+u->asec
	 *  shift+i->acsc
	 *  shift+j->acot
	 *  p->3.141592653589793
	 *  e->2.718281828459045
	 */

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
		operationPriority.put("sec", 4);
		operationPriority.put("cot", 4);
		operationPriority.put("csc", 4);
		operationPriority.put("asin", 4);
		operationPriority.put("acos", 4);
		operationPriority.put("atan", 4);
		operationPriority.put("asec", 4);
		operationPriority.put("acot", 4);
		operationPriority.put("acsc", 4);
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
		operationAry_N.put("sec",1);
		operationAry_N.put("csc",1);
		operationAry_N.put("cot",1);
		operationAry_N.put("asin",1);
		operationAry_N.put("acos",1);
		operationAry_N.put("atan",1);
		operationAry_N.put("asec",1);
		operationAry_N.put("acsc",1);
		operationAry_N.put("acot",1);
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
		countPostFix();
	}

	private void countPostFix(){
		/**
		 * @author: hirmy
		 * @description: 根据transToPostfix()修改后的list，进行后缀表达式的计算
		 * @date: 2023/12/12 19:06
		 * @return void
		 */
		outputMap = new HashMap<>();
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
						outputMap.put("answer", e.getMessage());
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
						outputMap.put("answer", e.getMessage());
						return;
					}
				}
			}
		}
		outputMap.put("answer", String.valueOf(stack.pop()));
	}



    /**
     * @author: kiyotaka
     * @description: 构造函数
     * @date: 2023/12/9 22:13
     * @return
     */
    public GeneralModel(String inputExpression) {
        this.inputExpression = inputExpression;
    }

	public GeneralModel(boolean isRadians){
		if(isRadians){
			this.isRadians = true;
		}
	}

	    private double calculate(String op, double ope){
	        /**
	        * @author: hirmy
	        * @description: 进行一元运算
	        * @date: 2023/12/9 14:50
	        * @return double
	        */
	    	double ans = 0;
			if(!isRadians){
				//角度制算法
				switch(op){
					case "log":
						if(ope < EP){
							 throw new ArithmeticException("真数必须大于0");
						}
						ans = Math.log(ope) / Math.log(10);
						break;
					case "ln":
						if(ope < EP){
							 throw new ArithmeticException("真数必须大于0");
						}
						ans = Math.log(ope);
						break;
					case "sin":
						ans = Math.sin(Math.toRadians(ope));
						break;
					case "cos":
						ans = Math.cos(Math.toRadians(ope));
						break;
					case "tan":
						if(isPiDiv2(ope)) {
							throw new ArithmeticException("tan无效输入");
							
						}
						ans = Math.tan(Math.toRadians(ope));
						break;
					case "csc":
						if(isKMultPi(ope)) {
							throw new ArithmeticException("csc无效输入");
							
						}
						ans =1/ Math.sin(Math.toRadians(ope));
						break;
					case "sec":
						if(isPiDiv2(ope)) {
							throw new ArithmeticException("sec无效输入");
							
						}
						ans =1/ Math.cos(Math.toRadians(ope));
						break;
					case "cot":
						if(isKMultPi(ope)) {
							throw new ArithmeticException("cot无效输入");
							
						}
						else if(isPiDiv2(ope)) {
							ans = 0;
						}
						else ans =1/ Math.tan(Math.toRadians(ope));
						break;
					case "asin":
						if(ope>1+EP||ope<-1-EP) {
							throw new ArithmeticException("asin无效输入");
						
						}
						ans = Math.toDegrees(Math.asin(ope));
						break;
					case "acos":
						if(ope>1+EP||ope<-1-EP) {
							throw new ArithmeticException("acos无效输入");
							
						}
						ans = Math.toDegrees(Math.acos(ope));
						break;
					case "atan":
						ans = Math.toDegrees(Math.atan(ope));
						break;
					case "acsc":
						if(ope>=-1+EP&&ope<=1-EP) {
							throw new ArithmeticException("acsc无效输入");
							
						}
						ans = Math.toDegrees(Math.asin(1/ope));
						break;
					case "asec":
						if(ope>=-1+EP&&ope<=1-EP) {
							throw new ArithmeticException("asec无效输入");
						
						}
						ans = Math.toDegrees(Math.acos(1/ope));
						break;
					case "acot":
						if(Math.abs(ope)<EP) {
							ans = 90;
						}
						else ans = Math.toDegrees(Math.atan(1/ope));
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
			}
	        else{
				//弧度制算法
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
					case "csc":
						ans =1/ Math.sin(ope);
						break;
					case "sec":
						ans =1/ Math.cos(ope);
						break;
					case "cot":
						ans =1/ Math.tan(ope);
						break;
					case "asin":
						if(ope>1+EP||ope<-1-EP) {
							throw new ArithmeticException("asin无效输入");
						
						}
						ans = Math.toDegrees(Math.asin(ope));
						break;
					case "acos":
						if(ope>1+EP||ope<-1-EP) {
							throw new ArithmeticException("acos无效输入");
							
						}
						ans = Math.toDegrees(Math.acos(ope));
						break;
					case "atan":
						ans = Math.toDegrees(Math.atan(ope));
						break;
					case "acsc":
						if(ope>=-1+EP&&ope<=1-EP) {
							throw new ArithmeticException("acsc无效输入");
							
						}
						ans = Math.toDegrees(Math.asin(1/ope));
						break;
					case "asec":
						if(ope>=-1+EP&&ope<=1-EP) {
							throw new ArithmeticException("asec无效输入");
						
						}
						ans = Math.toDegrees(Math.acos(1/ope));
						break;
					case "acot":
						if(Math.abs(ope)<EP) {
							ans = 90;
						}
						else ans = Math.toDegrees(Math.atan(1/ope));
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
	            	 if(Math.abs(ope2 - 0) < EP){
	            		 throw new ArithmeticException("除数不能为0");
	     	        }
	                ans = ope1 / ope2;
	                break;
	            case "mod": 
	            	if(Math.abs(ope2)<EP) {
	            		ans = ope1;
	            	}
	            	else {
	            		ans = (ope1%ope2+ope2)%ope2;
	            	}
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
	    	if(ope<-EP||ope-(int)ope>=EP) {
	    		throw new ArithmeticException("无法计算该阶乘");
	    	}
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

	    public Map<String, String> getOutPutMap() {
	        return outputMap;
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
	    	if(inputExpression.length()>300) {
	    		return;
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
	    	Pattern pattern = Pattern.compile("-?\\d+\\.\\d+|-?\\d+|[-+*/%!^()t()s()o()l()n()d()a()e()p()u()i()j()T()S()O()U()I()J()]");
	    	Matcher matcher = pattern.matcher(modifiedString);
	    	
	    	ArrayList<String> infixExpression = new ArrayList<>();
	    	while (matcher.find()) {
	    		String tmp = matcher.group();
	    		if(tmp.equals("t")) tmp="tan";
	    		else if(tmp.equals("s")) tmp="sin";
	    		else if(tmp.equals("o")) tmp="cos";
	    		else if(tmp.equals("u")) tmp="sec";
	    		else if(tmp.equals("i")) tmp="csc";
	    		else if(tmp.equals("j")) tmp="cot";
	    		else if(tmp.equals("T")) tmp="atan";
	    		else if(tmp.equals("S")) tmp="asin";
	    		else if(tmp.equals("O")) tmp="acos";
	    		else if(tmp.equals("U")) tmp="asec";
	    		else if(tmp.equals("I")) tmp="acsc";
	    		else if(tmp.equals("J")) tmp="acot";
	    		else if(tmp.equals("l")) tmp="log";
	    		else if(tmp.equals("n")) tmp="ln";
	    		else if(tmp.equals("d")) tmp="mod";
	    		else if(tmp.equals("a")) tmp="abs";
	    		else if(tmp.equals("p")) tmp= Double.toString(Math.PI);
	    		else if(tmp.equals("e")) tmp= Double.toString(Math.E);
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
	    public boolean isPiDiv2 (double num) {//判断是否为k*180+90
	    	if(Math.abs((num+90)%180)<EP||Math.abs((num+90)%180)>180-EP) {
	    		return true;
	    	}
	    	return false;
	    }
	    public boolean isKMultPi (double num) {//判断是否为k*180
	    	if(Math.abs(num%180)<EP||Math.abs(num%180)>180-EP) {
	    		return true;
	    	}
	    	return false;
	    }

    public static void main(String[] args) {//测试
       //String A = "-(-(-1! / 2 + t 45 - l 100.00 - 3! * s (-90) * o(180)/ n 2.732 d 6.54^1.2086 + a(-2)*a(3.04) %)%)*10000.0000^(1.768596)*T6.42/S 0.6024+O(-0.4523)-U7.002*I56.54-J0.23";
        String A = "3/0+l 2";
        GeneralModel testModel = new GeneralModel(A);
        testModel.count();
        System.out.println(testModel.outputMap);
    }

	public void setInputExpression(String inputInformation) {
		outputMap.clear();
		this.inputExpression = inputInformation;
	}


}
