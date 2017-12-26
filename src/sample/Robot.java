package sample;

public class Robot
{
    // RSSI(d) = A - 10n log(d)
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
