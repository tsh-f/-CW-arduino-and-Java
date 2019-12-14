package Gate;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.Semaphore;

public class App extends Application {
    int index;
    private Semaphore lock;
    private VBox root;
    private TextArea text;
    private TextField field;
    private Button submit;
    private Scene scene;


    {
        lock = new Semaphore(1);
        root = new VBox();
        text = new TextArea();
        field = new TextField();
        submit = new Button("Enter");
        text.setPrefSize(500, 400);
        text.setText("Enter COM port number:\n");
        text.setEditable(false);
        root.getChildren().addAll(text, field, submit);
        scene = new Scene(root, 500, 500);
        submit.setOnAction(e -> {
            index = Integer.parseInt(field.getText());
            lock.release();
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Gate");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread uiThread = new Thread(() -> {

            try {
                SerialPort[] ports = SerialPort.getCommPorts();

                for (int i = 0; i < ports.length; i++) {
                    text.appendText(i + " >> " + ports[i].getSystemPortName() + "\n");
                }
                lock.acquire();
//                SerialPort comPort = SerialPort.getCommPort(ports[index].getSystemPortName());

//                comPort.setBaudRate(9600);
//                comPort.setNumDataBits(8);
//                comPort.setParity(SerialPort.NO_PARITY);
//                comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
//                comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
//                comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
//                comPort.openPort();

//                byte[] buff = new byte[8];
//                long bytes = 8;

//                comPort.readBytes(buff, bytes);
//        while (comPort.isOpen()) {
//            comPort.readBytes(buff, bytes);
//            System.out.print(new String(buff));
//        }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        uiThread.start();
        uiThread.join();

    }

    public static void main(String[] args) {
        launch(args);
    }
}