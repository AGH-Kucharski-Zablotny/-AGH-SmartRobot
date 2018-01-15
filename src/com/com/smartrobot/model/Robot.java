package com.smartrobot.model;

/**
 * Class defines single robot entity with its position in 2D space and signals
 */
public class Robot
{
    // Array with signals strength
    private double[] signals;

    // Coords for rendering
    private int x;
    private int y;


    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public double[] getSignals()
    {
        return signals;
    }

    /**
     * Constructor initializes all variables and gives them data given in parameters
     * @param signal1 signal from antenna 1
     * @param signal2 signal from antenna 2
     * @param signal3 signal from antenna 3
     * @param x x coordinate at 2D space (for rendering)
     * @param y y coordinate at 2D space (for rendering)
     */
    public Robot(double signal1, double signal2, double signal3, int x, int y)
    {
        signals = new double[3];

        this.signals[0] = signal1;
        this.signals[1] = signal2;
        this.signals[2] = signal3;
        this.x = x;
        this.y = y;
    }
}
