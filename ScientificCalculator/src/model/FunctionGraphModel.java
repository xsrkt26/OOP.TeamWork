package model;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FunctionGraphModel extends Application {


    static int width = 1000;
    static int height = 600;
    static int centerX = width / 2;
    static int centerY = height / 2;
    static double scaleFactor = 0.0001;
    static int measureGap = 100; //坐标轴度量
    static String inputExpression;
    static String xExpression;
    static Scene scene;
    static double fontSize = 12;
    static Font nowFont = new Font(fontSize);
    static final double EP = 1e-16;


    public static void setInputExpression(String input) {
        inputExpression = replaceNameInput(input);
        xExpression = replaceNameInput(input);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Function");
        scene = new Scene(getPane(), width, height);
        scene.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                // 向下滚动
                measureGap *= 1.1;
                fontSize *= 1.1;
                nowFont = new Font(fontSize);
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (deltaY > 0) {
                // 向上滚动
                measureGap /= 1.1;
                fontSize /= 1.1;
                nowFont = new Font(fontSize);
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((event) -> {
            System.out.println("Closing Stage");
        });
    }


    public static void draw() throws Exception{
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Function");
        scene = new Scene(getPane(), width, height);
        scene.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                // 向下滚动
                measureGap *= 1.2;
                fontSize *= 1.2;
                nowFont = new Font(fontSize);
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (deltaY > 0) {
                // 向上滚动
                measureGap /= 1.2;
                fontSize /= 1.2;
                nowFont = new Font(fontSize);
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((event) -> {
            System.out.println("Closing Stage");
        });
    }


    private static Pane getPane() throws Exception {
        BorderPane thisPane = new BorderPane();
        BorderPane backPane = new BorderPane();
        backPane.getChildren().add(thisPane);
        //创建文本框
        HBox hbox = new HBox(0.01*width);
        TextField textField = new TextField();
        textField.setPrefColumnCount(10);
        textField.setPromptText("请输入函数表达式");
        Button submitButton = new Button("绘制");
        submitButton.setOnAction(e -> {
            String inputText = textField.getText();
            setInputExpression(inputText);
            try {
                scene.setRoot(getPane());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setPadding(new Insets(0.02*height));
        hbox.getChildren().addAll(textField, submitButton);
        backPane.setTop(hbox);
        //折线绘制
        Polyline polyline = new Polyline();
        ObservableList<Double> pointList = polyline.getPoints();

        //重置按钮
        HBox resetBox = new HBox();
        resetBox.setPadding(new Insets(0.02*width));
        Button resetButton = new Button("reset");
        resetButton.setOnAction(e -> {
            try{
                centerX = width/2;
                centerY = height/2;
                setInputExpression("0");
                measureGap = 100;
                scene.setRoot(getPane());
            }catch (Exception e1){
                throw new RuntimeException();
            }
        });
        resetBox.setAlignment(Pos.BOTTOM_RIGHT);
        resetBox.getChildren().add(resetButton);
        backPane.setBottom(resetBox);


        //函数，确定定义域
        for (int xi = -(width / 2)*5; xi < (width / 2)*5; xi++) {
            pointList.add(centerX + (double) (xi));
            pointList.add(centerY - (f(xi * 1.0 / measureGap) * measureGap));
        }
        //
        thisPane.getChildren().add(polyline);


        //坐标纸绘制
        Line xLine = new Line(0, centerY, width, centerY);  //x轴
        Line yLine = new Line(centerX, 0, centerX, height); //y轴
        Text xText = new Text("x");
        Text yText = new Text("y");
        Text zeroText = new Text("0");
        xText.setFont(nowFont);
        yText.setFont(nowFont);
        zeroText.setFont(nowFont);
        thisPane.getChildren().add(new Text(width * 0.98, centerY + 0.02 * height, xText.getText()));
        thisPane.getChildren().add(new Text(centerX + 0.01 * width, 0.02 * height, yText.getText()));
        thisPane.getChildren().add(new Text(centerX + 0.005 * width, centerY + 0.02 * height, zeroText.getText()));
        for (int gap = 0; gap < (width / 2)*10; gap += measureGap) {//x坐标线
            Line xMeasure1 = new Line(centerX + gap, centerY, centerX + gap, centerY - 0.01 * height);
            Line xMeasure2 = new Line(centerX - gap, centerY, centerX - gap, centerY - 0.01 * height);

            int num = gap / measureGap;
            if (num != 0) {
                thisPane.getChildren().add(new Text(centerX + gap - 0.002 * width, centerY + 0.02 * height, String.valueOf(num)));
                thisPane.getChildren().add(new Text(centerX - gap - 0.004 * width, centerY + 0.02 * height, String.valueOf(-num)));
            }

            thisPane.getChildren().add(xMeasure1);
            thisPane.getChildren().add(xMeasure2);
        }
        for (int gap = 0; gap < (height / 2)*10; gap += measureGap) {//y坐标线
            Line yMeasure1 = new Line(centerX, centerY + gap, centerX + 0.007 * width, centerY + gap);
            Line yMeasure2 = new Line(centerX, centerY - gap, centerX + 0.007 * width, centerY - gap);
            int num = gap / measureGap;
            if (num != 0) {
                thisPane.getChildren().add(new Text(centerX - 0.01 * width, centerY + 0.01 * height - gap, String.valueOf(num)));
                thisPane.getChildren().add(new Text(centerX - 0.015 * width, centerY + 0.01 * height + gap, String.valueOf(-num)));
            }
            thisPane.getChildren().add(yMeasure1);
            thisPane.getChildren().add(yMeasure2);
        }
        thisPane.getChildren().add(new Group(xLine, yLine));



        thisPane.setOnMousePressed(e -> {
            thisPane.setOnMouseDragged(e1 ->{
                double nowX = e1.getX();
                double nowY = e1.getY();

                centerX -= (int)((e.getX()-nowX)*0.07);
                centerY -= (int)((e.getY()-nowY)*0.05);
//                thisPane.setTranslateX(nowX-e.getX());
//                thisPane.setTranslateY(nowY-e.getY());

                try {
                    scene.setRoot(getPane());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

        });

        return backPane;
    }


    private static String replaceNameInput(String input) {
        input = input.replaceAll("atan", "T");
        input = input.replaceAll("asin", "S");
        input = input.replaceAll("acos", "O");
        input = input.replaceAll("asec", "U");
        input = input.replaceAll("acsc", "I");
        input = input.replaceAll("acot", "J");
        input = input.replaceAll("sin", "s");
        input = input.replaceAll("cos", "o");
        input = input.replaceAll("tan", "t");
        input = input.replaceAll("sec", "u");
        input = input.replaceAll("csc", "i");
        input = input.replaceAll("cot", "j");
        input = input.replaceAll("log", "l");
        input = input.replaceAll("ln", "n");
        input = input.replaceAll("mod", "d");
        input = input.replaceAll("abs", "a");
        return input;
    }

    private static double f(double x) throws Exception {
        try {
            if (x < 0) {
                inputExpression = xExpression.replaceAll("x", '(' + String.valueOf(x) + ')');
            } else {
                inputExpression = xExpression.replaceAll("x", String.valueOf(x));
            }
            GeneralModel gm = new GeneralModel(true);
            gm.setInputExpression(inputExpression);
            gm.count();
            String ans = gm.getOutPutMap().get("answer");
            return Double.valueOf(ans);
        } catch (Exception e) {
            return 0;
        }
      //  return Math.tan(x);
    }

    public static void main(String[] args) {
        GeneralModel gm = new GeneralModel(true);
        gm.setInputExpression("tanx");
        gm.count();
        String ans = gm.getOutPutMap().get("answer");
        System.out.println(ans);
    }
}

