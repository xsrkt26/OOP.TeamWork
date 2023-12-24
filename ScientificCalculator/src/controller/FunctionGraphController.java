package controller;

import model.FunctionGraphModel;
/**
 * 函数绘图方法接口
 * @author ：kiyotaka
 * @date ：2023/12/12 18:14
 */
public class FunctionGraphController {

    public void transmitData(String s){
        FunctionGraphModel.setInputExpression(s) ;
    };
    public void testDraw() {
        FunctionGraphModel.launch(FunctionGraphModel.class);
    }

    public void draw() throws Exception {
        try {
            FunctionGraphModel.draw();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws InterruptedException {
        FunctionGraphController fs = new FunctionGraphController();
        fs.transmitData("sinx+cosx");
        fs.testDraw();
        //实际使用时
        //fs.draw();
    }

}