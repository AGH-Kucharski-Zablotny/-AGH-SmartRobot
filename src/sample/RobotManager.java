package sample;

import java.util.ArrayList;

public class RobotManager
{
    private ArrayList<Robot> robots;

    public RobotManager()
    {
        robots = new ArrayList<Robot>();
    }

    public ArrayList<Robot> getRobots()
    {
        return robots;
    }

    public void addNewRobot(Robot robot)
    {
        robots.add(robot);
    }

//    public void
}
