package model;
import controller.GeneralController;
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

public class FunctionGraphModel extends Application {


    int width = 1000;
    int height = 600;
    int centerX = width / 2;
    int centerY = height / 2;
    double scaleFactor = 0.0001;
    int measureGap = 100; //坐标轴度量
    String inputExpression;
    Scene scene;
    public void run(){
        launch();
    }

    public void setInputExpression(String input){
        this.inputExpression = input;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Function");
        scene = new Scene(getPane(),width,height);
        scene.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                // 向下滚动
                measureGap *= 2;
                try {
                    scene.setRoot(getPane());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (deltaY > 0) {
                // 向上滚动
                measureGap /= 1.5;
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

    private Pane getPane() throws Exception{
        BorderPane thisPane = new BorderPane();

        //折线绘制
        Polyline polyline = new Polyline();
        ObservableList<Double> pointList = polyline.getPoints();

        //函数，确定定义域
        for(int xi = -(width/2); xi < width/2; xi++){
            pointList.add(centerX+(double)(xi));
            pointList.add(centerY-(f(xi*1.0/measureGap))*measureGap);
        }
        //
        thisPane.getChildren().add(polyline);


        //坐标纸绘制
        Line xLine = new Line(0, centerY, width, centerY);  //x轴
        Line yLine = new Line(centerX, 0, centerX, height); //y轴
        thisPane.getChildren().add(new Text(width*0.98, centerY + 0.02*height, "x"));
        thisPane.getChildren().add(new Text(centerX + 0.01*width, 0.02*height, "y"));
        thisPane.getChildren().add(new Text(centerX + 0.005*width, centerY + 0.02*height, "0"));
        for(int gap = 0; gap < width/2; gap += measureGap){//x坐标线
            Line xMeasure1 = new Line(centerX + gap, centerY, centerX + gap, centerY-0.01*height);
            Line xMeasure2 = new Line(centerX - gap, centerY, centerX - gap, centerY-0.01*height);

            int num = gap/measureGap;
            if(num != 0){
                thisPane.getChildren().add(new Text(centerX+gap-0.002*width, centerY + 0.02*height, String.valueOf(num)));
                thisPane.getChildren().add(new Text(centerX-gap-0.004*width, centerY + 0.02*height, String.valueOf(-num)));
            }

            thisPane.getChildren().add(xMeasure1);
            thisPane.getChildren().add(xMeasure2);
        }
        for(int gap = 0; gap < height/2; gap += measureGap){//y坐标线
            Line yMeasure1 = new Line(centerX, centerY+gap, centerX+0.007*width, centerY+gap);
            Line yMeasure2 = new Line(centerX, centerY-gap, centerX+0.007*width, centerY-gap);
            int num = gap/measureGap;
            if(num != 0){
                thisPane.getChildren().add(new Text(centerX - 0.01*width, centerY + 0.01*height-gap, String.valueOf(num)));
                thisPane.getChildren().add(new Text(centerX - 0.015*width, centerY + 0.01*height+gap, String.valueOf(-num)));
            }
            thisPane.getChildren().add(yMeasure1);
            thisPane.getChildren().add(yMeasure2);
        }
        thisPane.getChildren().add(new Group(xLine, yLine));
        return thisPane;
    }


    private double f(double x){
        String realExpression = inputExpression.replace("x", String.valueOf(x));
        GeneralModel temp = new GeneralModel(realExpression);
        temp.count();
        String result = temp.getOutPutMap().get("answer");

        return Double.valueOf(result);
       // return x*x;
    }



}
