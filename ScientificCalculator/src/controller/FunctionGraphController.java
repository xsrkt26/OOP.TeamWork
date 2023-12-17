package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.FunctionGraphModel;
import model.testModel;

import java.util.TreeMap;

public class FunctionGraphController {

    public void transmitData(String s){
        testModel.setInputExpression(s) ;
    };
    public void draw() {
        testModel.launch(testModel.class);
    }

    public static void main(String[] args) throws InterruptedException {
        FunctionGraphController fs = new FunctionGraphController();
        fs.transmitData("x^3");
        System.out.println(Thread.currentThread().getName());
        fs.draw();
    }

}
