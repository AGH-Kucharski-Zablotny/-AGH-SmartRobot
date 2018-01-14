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

//        return robots.get(centerIndex);
        return centerIndex;
    }

    /**
     * Method finds robot that is more or less between two antennas (that will mean that robot is at side of the triangle
     * formed by antennas, at the center of the side)
     * @param firstAntennaIndex index of the first antenna that is considered
     * @param secondAntennaIndex index of the second antenna that is considered
     * @return robot that is more or less in the centre of the line formed by two antennas
     */
    public Robot findRobotBetweenTwoAntennas(int firstAntennaIndex, int secondAntennaIndex)
    {
        Robot bestRobot = robots.get(0);

        for (int i = 0; i < robots.size() - 1; i++)
        {
            if(Math.abs(robots.get(i).getSignals()[firstAntennaIndex] -
                    robots.get(i).getSignals()[secondAntennaIndex]) < 1 &&
                Math.abs(robots.get(i).getSignals()[firstAntennaIndex] -
                    robots.get(i).getSignals()[secondAntennaIndex]) <
                    Math.abs(bestRobot.getSignals()[firstAntennaIndex] - bestRobot.getSignals()[secondAntennaIndex]))
            {
                bestRobot = robots.get(i);
            }
        }

        return bestRobot;
    }

    public boolean isMainRobotInRing(double[] antennaToAntennaMaxVal, double[] antennaToSideMaxVal)
    {
        double[] ringRadius = new double[3];

        for(int i = 0; i < 3; i++)
        {
            ringRadius[i] = Math.abs(antennaToAntennaMaxVal[i] - antennaToSideMaxVal[i]);

            if(Math.abs(robots.get(robots.size() - 1).getSignals()[i] -
                    Math.min(antennaToAntennaMaxVal[i], antennaToSideMaxVal[i])) > ringRadius[i])
            {
                return false;
            }
        }
        return true;
    }
}
