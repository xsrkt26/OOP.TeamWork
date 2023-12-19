package model;

import java.math.BigInteger;
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

    int numberSystem = 10;//目前的进制,默认为10
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
                stack.push(Long.parseLong(o,numberSystem));
            }
            else{
                if(o.equals("~")){
                    try{
                        long ope = stack.pop();
                        stack.push(~ope);
                    }catch(ArithmeticException e){
                        outputMap.put("answer", e.getMessage());
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
                        outputMap.put("answer", e.getMessage());
                        return;
                    }
                }
            }

        }
        outputMap.put("answer", transNum(String.valueOf(stack.pop()),10,numberSystem));
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
                if(ope2>64||ope2<0) {
                    throw new ArithmeticException("结果未定义");
                }
                ans = ope1 << ope2;
                break;
            case ">>":
                if(ope2>64||ope2<0) {
                    throw new ArithmeticException("结果未定义");
                }
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
    public ProgrammerModel(String inputExpression) {
        this.inputExpression = inputExpression;
    }
    public boolean checkIllegal() {
        return false;
    }

    public void setNumberSystem(int numberSystem) {//设置进制 numberSystem=2,8,10,16
        inputExpression=transAtoB(inputExpression,this.numberSystem,numberSystem);
        transAnswer(this.numberSystem,numberSystem);
        this.numberSystem=numberSystem;
    }
    private void transToPostfix() {
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
            if(replaceInput.charAt(i)=='-'&&(replaceInput.charAt(i-1)==')'||Character.isDigit(replaceInput.charAt(i-1)))||isAlpha(replaceInput.charAt(i-1))) {// 减号则后面插入空格
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
        Pattern pattern = Pattern.compile("[0-9a-f]+|[-+*/%><&|~\\\\.^()N()]");
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

    }
    public String transAtoB(String inputExpression,int a,int b) { //a->b进制转换
        String res="";
        String replaceInput = inputExpression.replaceAll("\\s", "");

        StringBuilder sb = new StringBuilder(replaceInput);
        int insertIndex ;
        char charToInsert = ' ';
        int k = 0;
        if(replaceInput.charAt(0)=='-') {
            insertIndex = 0;
            k++;
            sb.insert(insertIndex, '0');
            insertIndex = 2;
            k++;
            sb.insert(insertIndex, ' ');
        }
        for(int i=1;i<replaceInput.length();i++) {
            if(replaceInput.charAt(i)=='-'&&(replaceInput.charAt(i-1)==')'||Character.isDigit(replaceInput.charAt(i-1)))||isAlpha(replaceInput.charAt(i-1))) {// 减号则后面插入空格
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
        Pattern pattern = Pattern.compile("[0-9a-f]+|[-+*/%><&|~\\\\.^()N()]");
        Matcher matcher = pattern.matcher(modifiedString);

        ArrayList<String> infixExpression = new ArrayList<>();

        while (matcher.find()) {
            String tmp = matcher.group();
            infixExpression.add(tmp);
        }
        for(String o : infixExpression) {
            if( isLong(o)) {
                res+=transNum(o,a,b);
            }
            else res+=o;
        }

        return res;
    }
    public String transNum(String num,int a,int b) {
        BigInteger bigInteger = new BigInteger(num, a);
        long number = bigInteger.longValue();//解析a进制到十进制
        String res="";
        if(b==2) {
            res=Long.toBinaryString(number);
        }
        else if(b==8) {
            res=Long.toOctalString(number);
        }
        else if(b==10) {
            res=Long.toString(number);
        }
        else if(b==16) {
            res=Long.toHexString(number);
        }

        return res;
    }
    public void transAnswer(int a,int b) {
        String tmp = outputMap.get("answer");
        outputMap.put("answer", transNum(tmp,a,b));

    }
    public static boolean isLong(String input) { // 判断字符串是否是整数
        return input.matches("[0-9a-f]+");
    }
    public static boolean isAlpha(char ch) { // 判断字符串是否是a-f
        if(ch>='a'&&ch<='f')return true;
        return false;
    }
    @Override
    public Map<String, String> getOutPutMap() {
        return outputMap;
    }
    public static void main(String[] args) {//测试

        String A = "-124<19+274.3-243432";
        ProgrammerModel testModel = new ProgrammerModel(A);
        testModel.count();
        System.out.println(testModel.outputMap);
        testModel.setNumberSystem(2);
        System.out.println(testModel.outputMap);
        testModel.setNumberSystem(8);
        System.out.println(testModel.outputMap);
        testModel.setNumberSystem(10);
        System.out.println(testModel.outputMap);
        testModel.setNumberSystem(16);
        System.out.println(testModel.outputMap);
        testModel.setNumberSystem(8);
        System.out.println(testModel.outputMap);


    }

}