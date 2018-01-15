//package sample;
//
//
//import com.smartrobot.model.SmartRobotModel;
//import javafx.fxml.FXML;
//import javafx.scene.Group;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//import java.io.IOException;
//
//import static javafx.application.Application.launch;
//
//public class Controller
//{
//    private SmartRobotModel model;
//    private boolean check = false;
//    @FXML
//    private Canvas canvas;
//
//    public Controller()
//    {
//
//    }
//
//    private void drawRobots(GraphicsContext graphicsContext)
//    {
//        graphicsContext.setFill(Color.RED);
//        graphicsContext.fillOval(model.getRobotManager().getRobots().get(model.getRobotManager().getRobots().size() - 1).getX(), model.getRobotManager().getRobots().get(model.getRobotManager().getRobots().size() - 1).getY(), 10, 10);
//
//        graphicsContext.setFill(Color.GREY);
//        for (int i = 0; i < model.getRobotManager().getRobots().size() - 1; i++)
//        {
//            graphicsContext.fillOval(model.getRobotManager().getRobots().get(i).getX(), model.getRobotManager().getRobots().get(i).getY(), 10, 10);
//        }
//    }
//
//    private void drawAntennas(GraphicsContext graphicsContext)
//    {
//        graphicsContext.setFill(Color.BLUE);
//        for (int i = 0; i < 3; i++)
//        {
//            graphicsContext.fillOval(model.getAntennas()[i].getX(), model.getAntennas()[i].getY(), 10, 10);
//        }
//    }
//
//    public void checkboxAction()
//    {
//        check = !check;
//        System.out.println("CheckBox matched!");
//    }
//
//    @FXML
//    public void startButtonAction()
//    {
//        model = new SmartRobotModel(420, 305);
//        try
//        {
//            model.initializeModel("./src/com/smartrobot/model/parameters.ini", check);
//        }
//        catch (IOException e)
//        {
//            e.getCause().printStackTrace();
//            e.printStackTrace();
//        }
//
//
//        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//
//        drawRobots(graphicsContext);
//        drawAntennas(graphicsContext);
//
//        Group root = new Group();
//        root.getChildren().add(canvas);
//
//        System.out.println("Canvas ON!");
//    }
//
//    public void decideButtonAction()
//    {
//
//    }
//
//    public void resetButtonAction()
//    {
//
//    }
//
//    public void quitButtonAction()
//    {
//
//    }
//
//
//
//}
////////////////////

package sample;

        import com.smartrobot.model.SmartRobotModel;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.Group;
        import javafx.scene.canvas.Canvas;
        import javafx.scene.canvas.GraphicsContext;
        import javafx.scene.control.Button;
        import javafx.scene.control.CheckBox;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.Region;
        import javafx.scene.paint.Color;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

        import static javafx.application.Application.launch;

public class Controller implements Initializable {

    @FXML
    private CheckBox checkbox;

    @FXML
    private Button startButton;

    @FXML
    private Button decideButton;

    @FXML
    private Region region;

    @FXML
    private Button resetButton;

    @FXML
    private Button quitButton;

    @FXML
    private Canvas canvas;

    private SmartRobotModel model;
    private GraphicsContext graphicsContext;
    private boolean check = false;


    @FXML
    void checkboxAction(ActionEvent event)
    {
        check = !check;
        System.out.println("CheckBox matched!");
    }

    @FXML
    void startButtonAction(MouseEvent event)
    {
        model = new SmartRobotModel(420, 305);
        try
        {
            model.initializeModel("./src/com/smartrobot/model/parameters.ini", check);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        System.out.println("Canvas ON!");
    }

    @FXML
    void decideButtonAction(ActionEvent event)
    {

    }

    @FXML
    void resetButtonAction(ActionEvent event) {

    }

    @FXML
    void quitButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        graphicsContext = canvas.getGraphicsContext2D();
        model = new SmartRobotModel((int)canvas.getWidth(), (int)canvas.getHeight());
    }
}
