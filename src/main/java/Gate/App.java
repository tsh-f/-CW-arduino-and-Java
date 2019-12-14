package Gate;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 200,100);
        TextArea text = new TextArea();
        text.setText("Введите номер COM порта: ");
        primaryStage.setScene(scene);
        primaryStage.show();
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Введите номер COM порта: ");

        for (int i = 0; i < ports.length; i++) {
            System.out.println(i + " " + ports[i].getSystemPortName());
        }

        int index = (new Scanner(System.in)).nextInt();
        SerialPort comPort = SerialPort.getCommPort(ports[index].getSystemPortName());

        comPort.setBaudRate(9600);
        comPort.setNumDataBits(8);
        comPort.setParity(SerialPort.NO_PARITY);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        comPort.openPort();

        byte[] buff = new byte[8];
        long bytes = 8;

        comPort.readBytes(buff, bytes);
        while (comPort.isOpen()) {
            comPort.readBytes(buff, bytes);
            System.out.print(new String(buff));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}