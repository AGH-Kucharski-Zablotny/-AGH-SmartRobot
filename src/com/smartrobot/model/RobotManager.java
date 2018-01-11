package com.smartrobot.model;

import java.util.ArrayList;

/**
 * Class stores and manages all robots
 */
public class RobotManager
{
    // Main robot is at the end of the robots list
    private ArrayList<Robot> robots;

    /**
     * Constructor initializes robots list
     */
    public RobotManager()
    {
        robots = new ArrayList<Robot>();
    }

    public ArrayList<Robot> getRobots()
    {
        return robots;
    }

    /**
     * Method adds new robot to list of robots
     * @param robot new robot to be added
     */
    public void addNewRobot(Robot robot)
    {
        robots.add(robot);
    }

    /**
     * Method finds index of robot with biggest signal to antenna given in parameter from list of all robots
     * @param antennaIndex index of antenna to bo sorted by
     * @return index of robot with biggest signal (the one closest to antenna)
     */
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

    // @TODO Should we even consider this?
    public int findMassCenterOfAntennasIndex()
    {
        int centerIndex = 0;

        for (int i = 0; i < robots.size() - 1; i++)
        {
            if (Math.abs(robots.get(i).getSignals()[0] - robots.get(i).getSignals()[1]) < 5 &&
                    Math.abs(robots.get(i).getSignals()[0] - robots.get(i).getSignals()[2]) < 5 &&
                    Math.abs(robots.get(i).getSignals()[1] - robots.get(i).getSignals()[2]) < 5)
            {
                if ((Math.abs(robots.get(i).getSignals()[0] - robots.get(i).getSignals()[1]) <
                                Math.abs(robots.get(centerIndex).getSignals()[0] - robots.get(centerIndex).getSignals()[1]) &&
                        Math.abs(robots.get(i).getSignals()[0] - robots.get(i).getSignals()[2]) <
                                Math.abs(robots.get(centerIndex).getSignals()[0] - robots.get(centerIndex).getSignals()[2]) &&
                        Math.abs(robots.get(i).getSignals()[1] - robots.get(i).getSignals()[2]) <
                                Math.abs(robots.get(centerIndex).getSignals()[1] - robots.get(centerIndex).getSignals()[2])))
                {
                    centerIndex = i;
                }
            }
        }

        return centerIndex;
    }
}
