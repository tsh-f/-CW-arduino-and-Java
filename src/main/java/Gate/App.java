package Gate;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class App extends Application {
    private TextArea text;
    private TextField field;
    private Button submit;
    private Scene scene;
    private byte[] buff;
    private long bytes;
    private SerialPort comPort;
    private SerialPort[] ports;

    {
        VBox root = new VBox();
        text = new TextArea();
        field = new TextField();
        submit = new Button("Enter");
        text.setPrefSize(300, 300);
        text.setText("Enter COM port number:\n");
        text.setEditable(false);
        root.getChildren().addAll(text, field, submit);
        scene = new Scene(root, 400, 350);


        submit.setOnAction(e -> {
            openCOMPort();
            readBytesOfCOMPort();
        });
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gate");
        primaryStage.setScene(scene);
        primaryStage.show();

        ports = SerialPort.getCommPorts();

        for (int i = 0; i < ports.length; i++) {
            text.appendText(i + " >> " + ports[i].getSystemPortName() + "\n");
        }
    }

    private void openCOMPort() {
        field.setVisible(false);
        submit.setVisible(false);
        text.setText("");
        int index = Integer.parseInt(field.getText());
        comPort = SerialPort.getCommPort(ports[index].getSystemPortName());

        comPort.openPort();
        comPort.setBaudRate(9600);
        comPort.setNumDataBits(8);
        comPort.setParity(SerialPort.NO_PARITY);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);

    }

    private void readBytesOfCOMPort() {
        buff = new byte[15];
        bytes = 15;

        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        while (comPort.isOpen()) {
                            comPort.readBytes(buff, bytes);
                            if (buff[0] != 0) {
                                text.appendText("(" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ") ");
                                text.appendText(new String(buff));
                                Arrays.fill(buff, (byte) 0);
                            }
                            text.end();
                        }
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}