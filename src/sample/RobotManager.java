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

    public int findIndexOfRobotWithBiggestSignal(int antennaIndex)
    {
        int biggestRobotIndex = 0;

        for (int i = 0; i < robots.size() - 1; i++)
        {
            if(robots.get(i).getSignals()[antennaIndex] > robots.get(biggestRobotIndex).getSignals()[antennaIndex])
            {
                biggestRobotIndex = i;
            }
        }

        return biggestRobotIndex;
    }
}
