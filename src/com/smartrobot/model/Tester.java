package com.smartrobot.model;

import java.io.IOException;

public class Tester
{
    public static void main(String[] args)
    {
        SmartRobotModel smartRobotModel = new SmartRobotModel(800,600);

        try
        {
            smartRobotModel.initializeModel("./src/sample/parameters.ini");
            if(smartRobotModel.shouldMainRobotStay())
            {
                System.out.println("Main robot should stay");
            }
            else
            {
                System.out.println("Main robot should escape");
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
