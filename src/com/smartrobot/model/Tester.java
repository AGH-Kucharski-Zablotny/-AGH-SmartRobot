package com.smartrobot.model;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class made for testing and visualizing model (mostly DEBUG)
 */
public class Tester extends Application
{
    private static SmartRobotModel smartRobotModel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args)
    {
        smartRobotModel = new SmartRobotModel(WIDTH,HEIGHT);

        try
        {
            smartRobotModel.initializeModel("./src/com/smartrobot/model/parameters.ini", false);
            launch();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        // Draw robots
        stage.setTitle("Hello World");
        Group root = new Group();

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawRobots(gc);
        drawAntennas(gc);

        root.getChildren().add(canvas);

        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();

        if(smartRobotModel.shouldMainRobotStay(false))
        {
            System.out.println("Main robot should stay");
        }
        else
        {
            System.out.println("Main robot should escape");
        }
    }

    private void drawAntennas(GraphicsContext gc)
    {
        gc.setFill(Color.ORANGE);

        for (int i = 0; i < 3; i++)
        {
            gc.fillOval(smartRobotModel.getAntennas()[i].getX(), smartRobotModel.getAntennas()[i].getY(), 10, 10);
        }
    }

    private void drawRobots(GraphicsContext gc)
    {
        // Black for dummy robots
        gc.setFill(Color.BLACK);

        for (int i = 0; i < smartRobotModel.getRobotManager().getRobots().size() - 1; i++)
        {
            gc.fillOval(smartRobotModel.getRobotManager().getRobots().get(i).getX(), smartRobotModel.getRobotManager().getRobots().get(i).getY(), 10, 10);
        }

        // Red for main robot
        gc.setFill(Color.RED);
        gc.fillOval(smartRobotModel.getRobotManager().getRobots().get(smartRobotModel.getRobotManager().getRobots().size() - 1).getX(),
                smartRobotModel.getRobotManager().getRobots().get(smartRobotModel.getRobotManager().getRobots().size() - 1).getY(), 10, 10);
    }
}
