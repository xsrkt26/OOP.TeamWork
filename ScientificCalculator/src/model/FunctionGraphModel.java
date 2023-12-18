package model;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javafx.scene.layout.VBox;
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
                measureGap *= 1.5;
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (deltaY > 0) {
                // 向上滚动
                measureGap /= 1.2;
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
                measureGap *= 1.5;
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (deltaY > 0) {
                // 向上滚动
                measureGap /= 1.2;
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
        VBox vbox = new VBox();

        //创建文本框
        TextField textField = new TextField();
        textField.setPrefColumnCount(3);
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
        vbox.getChildren().addAll(textField, submitButton);
        thisPane.setCenter(vbox);

        //折线绘制
        Polyline polyline = new Polyline();
        ObservableList<Double> pointList = polyline.getPoints();

        //函数，确定定义域
        for (int xi = -(width / 2); xi < width / 2; xi++) {
            pointList.add(centerX + (double) (xi));
            pointList.add(centerY - (f(xi * 1.0 / measureGap) * measureGap));
        }
        //
        thisPane.getChildren().add(polyline);


        //坐标纸绘制
        Line xLine = new Line(0, centerY, width, centerY);  //x轴
        Line yLine = new Line(centerX, 0, centerX, height); //y轴
        thisPane.getChildren().add(new Text(width * 0.98, centerY + 0.02 * height, "x"));
        thisPane.getChildren().add(new Text(centerX + 0.01 * width, 0.02 * height, "y"));
        thisPane.getChildren().add(new Text(centerX + 0.005 * width, centerY + 0.02 * height, "0"));
        for (int gap = 0; gap < width / 2; gap += measureGap) {//x坐标线
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
        for (int gap = 0; gap < height / 2; gap += measureGap) {//y坐标线
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
        return thisPane;
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
            GeneralGraphModel gm = new GeneralGraphModel(true);
            gm.setInputExpression(inputExpression);
            gm.count();
            String ans = gm.getOutPutMap().get("answer");
            return Double.valueOf(ans);
        } catch (Exception e) {
            System.out.println("error");
            return 0;
        }
        //  return Math.tan(x);
    }

    public static void main(String[] args) {
        GeneralGraphModel gm = new GeneralGraphModel(true);
        gm.setInputExpression("tanx");
        gm.count();
        String ans = gm.getOutPutMap().get("answer");
        System.out.println(ans);
    }
}
