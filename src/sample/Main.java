package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main extends Application
{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int RADIUS = 10;

    private static Antenna[] antennas;
    private static RobotManager robotManager;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Group root = new Group();

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawRobots(gc);
        drawAntennas(gc);

        root.getChildren().add(canvas);

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

        System.out.println("Initialization done! \nStarting evacuation algorithm...");

        // Biggest 1. antenna signal
        int a;
        double d;
        Robot[] robotsClosestToAntennas = new Robot[3];

        // Find "antennas"
        for(int i = 0; i < 3; i++)
        {
            a = robotManager.findIndexOfRobotWithBiggestSignal(i);

            d = Math.sqrt(Math.pow(robotManager.getRobots().get(a).getX() - antennas[i].getX(), 2) +
                    Math.pow(robotManager.getRobots().get(a).getY() - antennas[i].getY(), 2));

            robotsClosestToAntennas[i] = robotManager.getRobots().get(a);

            System.out.println("Robot with biggest " + i + ". signal: " +
                    "Robot at (" + robotManager.getRobots().get(a).getX() + ", " + robotManager.getRobots().get(a).getY() +
                    ") with singals: (" + robotManager.getRobots().get(a).getSignals()[0] + ", " +
                    robotManager.getRobots().get(a).getSignals()[1] + ", " +
                    robotManager.getRobots().get(a).getSignals()[2] + "), distance: " + d);
        }

        // DEBUG: Reminder
        for (int i = 0; i < 3; i++)
        {
            System.out.println("Antenna " + i + " is at coords: (" + antennas[i].getX() + ", " + antennas[i].getY() + ")");
        }

        double[] minVal = new double[3];
        double[] maxVal = new double[3];

        // Create intervals
        for (int i = 0; i < 3; i++)
        {
            minVal[i] = robotsClosestToAntennas[i].getSignals()[i];

            if (robotsClosestToAntennas[(i + 1) % 3].getSignals()[i] < robotsClosestToAntennas[(i + 2) % 3].getSignals()[i])
            {
                maxVal[i] = robotsClosestToAntennas[(i + 1) % 3].getSignals()[i];
            }
            else
            {
                maxVal[i] = robotsClosestToAntennas[(i + 2) % 3].getSignals()[i];
            }

            System.out.println("Interval for antenna " + i + ": [" + minVal[i] + ", " + maxVal[i] + "]" );
        }

        // Decide if sould evacuate
        if(robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[0] < minVal[0] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[0] > maxVal[0] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[1] < minVal[1] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[1] > maxVal[1] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[2] < minVal[2] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[2] > maxVal[2])
        {
            System.out.println("Main robot should stay");
        }
        else
        {
            System.out.println("Main robot should escape");
        }

        // @TODO: MASS CENTER TO BE COMPARED TO LIMIT THE RESULT

    }


    public static void main(String[] args)
    {

        // args will have pathname with parameters in that order in new lines:
        // A
        // n
        // Amount of robots to be simulated

        System.out.println("File path is: " + args[0]);

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));

            // Read A
            double A = Double.parseDouble(br.readLine());
            System.out.println("Read A: " + A);

            // Read n
            double n = Double.parseDouble(br.readLine());
            System.out.println("Read n: " + n);

            // Read amount of robots
            int robotsAmount = Integer.parseInt(br.readLine());
            System.out.println("Read amount: " + robotsAmount);

            br.close();

            // Generate antennas
            antennas = new Antenna[3];

            for (int i = 0; i < 3; i++)
            {
                System.out.println("Generating Antenna " + i + "...");
                antennas[i] = new Antenna((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT));
                System.out.println("Anntena " + i + " placed at: (" + antennas[i].getX() + ", " + antennas[i].getY() + ")");
            }

            // Triangle condition
            while (!triangleCondition())
            {
                System.out.println("Antennas doesn't make triangle, setting up again...");
                int x = (int) (Math.random() * WIDTH);
                int y = (int) (Math.random() * HEIGHT);
                antennas[0].setX(x);
                antennas[0].setY(y);
            }


            // Generate new robots
            System.out.println("Generating Robots...");
            robotManager = new RobotManager();

            for (int k = 0; k < robotsAmount; k++)
            {
                int x = (int) (Math.random() * WIDTH);
                int y = (int) (Math.random() * HEIGHT);

                double[] s = new double[3];

                // Calculate RSSI
                for (int i = 0; i < 3; i++)
                {
                    s[i] = A - (10 * n * Math.log(Math.sqrt(Math.pow(antennas[i].getX() - x, 2) + Math.pow(antennas[i].getY() - y, 2))));
                }

                Robot robot = new Robot(s[0], s[1], s[2], x, y);
                robotManager.addNewRobot(robot);
                System.out.println("Robot placed at: (" + robot.getX() + ", " + robot.getY() + ") with signals: (" +
                        robot.getSignals()[0] + ", " + robot.getSignals()[1] + ", " + robot.getSignals()[2] + ")");
            }

            // Main robot generation
            System.out.println("Generating main robot...");
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);

            double[] s = new double[3];

            // Calculate RSSI
            for (int i = 0; i < 3; i++)
            {
                s[i] = A - (10 * n * Math.log(Math.sqrt(Math.pow(antennas[i].getX() - x, 2) + Math.pow(antennas[i].getY() - y, 2))));
            }

            Robot robot = new Robot(s[0], s[1], s[2], x, y);
            robotManager.addNewRobot(robot);
            System.out.println("Main robot placed at: (" + robot.getX() + ", " + robot.getY() + ") with signals: (" +
                    robot.getSignals()[0] + ", " + robot.getSignals()[1] + ", " + robot.getSignals()[2] + ")");

            System.out.println("Initialization completed, turning GUI on...");

            launch();


        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void drawAntennas(GraphicsContext gc)
    {
        gc.setFill(Color.ORANGE);

        for (int i = 0; i < 3; i++)
        {
            gc.fillOval(antennas[i].getX(), antennas[i].getY(), RADIUS, RADIUS);
        }
    }

    private void drawRobots(GraphicsContext gc)
    {
        // Black for dummy robots
        gc.setFill(Color.BLACK);

        for (int i = 0; i < robotManager.getRobots().size() - 1; i++)
        {
            gc.fillOval(robotManager.getRobots().get(i).getX(), robotManager.getRobots().get(i).getY(), RADIUS, RADIUS);
        }

        // Red for main robot
        gc.setFill(Color.RED);
        gc.fillOval(robotManager.getRobots().get(robotManager.getRobots().size() - 1).getX(),
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getY(), RADIUS, RADIUS);
    }

    private static boolean triangleCondition()
    {
        double a = Math.sqrt(Math.pow(antennas[0].getX() - antennas[1].getX(), 2) + Math.pow(antennas[0].getY() - antennas[1].getY(), 2));
        double b = Math.sqrt(Math.pow(antennas[0].getX() - antennas[2].getX(), 2) + Math.pow(antennas[0].getY() - antennas[2].getY(), 2));
        double c = Math.sqrt(Math.pow(antennas[1].getX() - antennas[2].getX(), 2) + Math.pow(antennas[1].getY() - antennas[2].getY(), 2));

        if(a+b > c && a+c > b && b+c > a)
        {
            return true;
        }
        return false;
    }
}
