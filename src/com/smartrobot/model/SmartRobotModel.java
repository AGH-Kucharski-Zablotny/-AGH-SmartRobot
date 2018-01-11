package com.smartrobot.model;

import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class allows to load and create antennas from file, creates dummy robots, main robot, activates "should escape"
 * algorithm and gives output to user interface with decision to stay or to escape
 */
public class SmartRobotModel
{
    /**
     * Const variables determines size of land field to operate over with.
     */
    private final int WIDTH;
    private final int HEIGHT;

    private Antenna[] antennas;
    private RobotManager robotManager;
    private int amountOfDummyRobots;

    /**
     * Variables needed to calculate signal from antennas
     */
    private double A;
    private double n;

    /**
     * Constructor initializes all variables to given parameters from controller with given radius
     * @param width - width of the land field
     * @param height - height of the land field
     */
    public SmartRobotModel(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;
        antennas = new Antenna[3];          // There are 3 antennas always so it will be hard coded.
        robotManager = new RobotManager();
    }

    /**
     * Method initializes all antennas and robots with configuration given in file.
     * @param configFilePath file with configuration
     * @throws IOException exception is thrown when file cannot be opened or be read
     */
    public void initializeModel(String configFilePath) throws IOException
    {
        loadParametersFromFile(configFilePath);
        generateRandomAntennas();
        generateDummyRobots();
        generateMainRobot();
    }

    /**
     * Method executes algorithm of finding robots nearest to the antennas, makes intervals of signal to be in to be
     * safe and returns decision of staying or not
     * @return true if main robot is in limited interval (should stay), false otherwise (should escape)
     */
    public boolean shouldMainRobotStay()
    {
        Robot[] robotsClosestToAntennas = findRobotsClosestToAntennas();

        double[] minVal = new double[3];
        double[] maxVal = new double[3];

        // Make intervals
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

        return makeDecision(minVal, maxVal);
    }

    /**
     * Method decides if main robot is in interval made by antennas
     * @param minVal minimum values of every interval
     * @param maxVal maximum values of every interval
     * @return true if main robot is in interval, false otherwise
     */
    private boolean makeDecision(@NotNull double[] minVal, double[] maxVal)
    {
        if(robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[0] < minVal[0] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[0] > maxVal[0] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[1] < minVal[1] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[1] > maxVal[1] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[2] < minVal[2] &&
                robotManager.getRobots().get(robotManager.getRobots().size() - 1).getSignals()[2] > maxVal[2])
        {
            // Stay
            return true;
        }
        else
        {
            // Evacuate
            return false;
        }
    }

    /**
     * Method finds robots with biggest signal which are closest to antennas and returns them
     * @return robots array with biggest antennas signals
     */
    private Robot[] findRobotsClosestToAntennas()
    {
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

        return robotsClosestToAntennas;
    }

    /**
     * Method loads signal function and amount of robots from file and stores it class
     * @param pathName - path to file with config
     * @throws IOException - exception is thrown when file is not found or reading is not possible
     */
    private void loadParametersFromFile(String pathName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(pathName));

        // Read A
        A = Double.parseDouble(br.readLine());
        System.out.println("Read A: " + A);

        // Read n
        n = Double.parseDouble(br.readLine());
        System.out.println("Read n: " + n);

        // Read amount of robots
        amountOfDummyRobots = Integer.parseInt(br.readLine());
        System.out.println("Read amount: " + amountOfDummyRobots);

        br.close();
    }

    /**
     * Method generates new antennas at random location and makes sure that they make triangle.
     */
    private void generateRandomAntennas()
    {
        for (int i = 0; i < 3; i++)
        {
            // DEBUG
            System.out.println("Generating Antenna " + i + "...");

            antennas[i] = new Antenna((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT));

            // DEBUG
            System.out.println("Anntena " + i + " placed at: (" + antennas[i].getX() + ", " + antennas[i].getY() + ")");
        }

        // Triangle condition
        // Only changes one vertex because when it moves it will make triangle
        while (!triangleCondition())
        {
            // DEBUG
            System.out.println("Antennas doesn't make triangle, setting up again...");

            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            antennas[0].setX(x);
            antennas[0].setY(y);
        }
    }

    /**
     * Method creates dummy robots with random position in amount from class and adds it to robotManager
     */
    private void generateDummyRobots()
    {
        for (int k = 0; k < amountOfDummyRobots; k++)
        {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);

            double[] signals = new double[3];

            // Calculate signal
            for (int i = 0; i < 3; i++)
            {
                signals[i] = antennas[i].calculateSignalFromAntenna(A, n, x, y);
            }

            // Add new robot to manager
            Robot robot = new Robot(signals[0], signals[1], signals[2], x, y);
            robotManager.addNewRobot(robot);

            // DEBUG
            System.out.println("Robot placed at: (" + robot.getX() + ", " + robot.getY() + ") with signals: (" +
                    robot.getSignals()[0] + ", " + robot.getSignals()[1] + ", " + robot.getSignals()[2] + ")");

        }
    }

    /**
     * Method creates main robot at random position, calculates its signals, adds to last position in robotManager list
     */
    private void generateMainRobot()
    {
        // DEBUG
        System.out.println("Generating main robot...");

        int x = (int) (Math.random() * WIDTH);
        int y = (int) (Math.random() * HEIGHT);

        double[] signals = new double[3];

        // Calculate signal
        for (int i = 0; i < 3; i++)
        {
            signals[i] = antennas[i].calculateSignalFromAntenna(A, n, x, y);
        }

        Robot robot = new Robot(signals[0], signals[1], signals[2], x, y);
        robotManager.addNewRobot(robot);

        // DEBUG
        System.out.println("Main robot placed at: (" + robot.getX() + ", " + robot.getY() + ") with signals: (" +
                robot.getSignals()[0] + ", " + robot.getSignals()[1] + ", " + robot.getSignals()[2] + ")");

    }

    /**
     * Method returns if antennas make triangle
     * @return true if antennas' vertices make triangle, false if not
     */
    private boolean triangleCondition()
    {
        double vertex1 = Math.sqrt(Math.pow(antennas[0].getX() - antennas[1].getX(), 2) + Math.pow(antennas[0].getY() - antennas[1].getY(), 2));
        double vertex2 = Math.sqrt(Math.pow(antennas[0].getX() - antennas[2].getX(), 2) + Math.pow(antennas[0].getY() - antennas[2].getY(), 2));
        double vertex3 = Math.sqrt(Math.pow(antennas[1].getX() - antennas[2].getX(), 2) + Math.pow(antennas[1].getY() - antennas[2].getY(), 2));

        if(vertex1+vertex2 > vertex3 && vertex1+vertex3 > vertex2 && vertex2+vertex3 > vertex1)
        {
            return true;
        }
        return false;
    }
}
