package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.EventHandler;

/**
 * Main class to launch application
 *
 * App made by: Piotr Kucharski, Dominik Zabłotny
 * AGH EAIiIB 2018
 */
public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SmartRobot");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
