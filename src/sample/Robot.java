package sample;

public class Robot
{
    // RSSI(d) = A - 10n log(d)
    private double signal1;
    private double signal2;
    private double signal3;

    // Coords for rendering
    private int x;
    private int y;

    public double getSignal1()
    {
        return signal1;
    }

    public double getSignal2()
    {
        return signal2;
    }

    public double getSignal3()
    {
        return signal3;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Robot(double signal1, double signal2, double signal3, int x, int y)
    {
        this.signal1 = signal1;
        this.signal2 = signal2;
        this.signal3 = signal3;
        this.x = x;
        this.y = y;
    }
}
