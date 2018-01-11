package com.smartrobot.model;

/**
 * Class defines antenna as signal source with coordinates (x,y)
 */
public class Antenna
{
    /**
     * Coordinates of antenna (for render usage)
     */
    private int x;
    private int y;

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Constructor makes new Antenna at location x,y given for render
     * @param x x coordinate at 2D space
     * @param y y coordinate at 2D space
     */
    public Antenna(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Method calculates signal strengthe based on equation RSSI(d) = A - 10*n*log(d) where d is distance between object
     * and antenna, with parameters given to function
     * @param A parameter of equation
     * @param n parameter of equation
     * @param robotX x coordinate of robot with signal to be calculated
     * @param robotY y coordinate of robot with signal to be calculated
     * @return signal strength given as: A - 10 * n * log(distance(antenna, robot))
     */
    public double calculateSignalFromAntenna(double A, double n, int robotX, int robotY)
    {
       return (A - (10 * n * Math.log(Math.sqrt(Math.pow(x - robotX, 2) + Math.pow(y - robotY, 2)))));
    }
}
