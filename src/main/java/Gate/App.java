package Gate;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;

public class App extends Application {
    int index;
    private VBox root;
    private TextArea text;
    private TextField field;
    private Button submit;
    private Scene scene;
    private byte[] buff;
    private long bytes;
    private SerialPort comPort;
    private SerialPort[] ports;


    {
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
            buttonAction();
            readBytesOfCOMPort();
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gate");
        primaryStage.setScene(scene);
        primaryStage.show();

        ports = SerialPort.getCommPorts();

        for (int i = 0; i < ports.length; i++) {
            text.appendText(i + " >> " + ports[i].getSystemPortName() + "\n");
        }
    }

    private void buttonAction(){
        field.setVisible(false);
        submit.setVisible(false);
        text.setText("");
        index = Integer.parseInt(field.getText());
        comPort = SerialPort.getCommPort(ports[index].getSystemPortName());

        comPort.openPort();
        comPort.setBaudRate(9600);
        comPort.setNumDataBits(8);
        comPort.setParity(SerialPort.NO_PARITY);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
    }

    private void readBytesOfCOMPort() {
        buff = new byte[13];
        bytes = 13;
        String tmp;
        Scanner scanner = new Scanner(comPort.getInputStream());
        while(comPort.isOpen()){
            if(scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }
//        comPort.readBytes(buff, bytes);
//        while (comPort.isOpen()) {
//            comPort.readBytes(buff, bytes);
//            text.appendText(new String(buff));
//        }
    }

//    class MainUI implements Runnable {
//
//        @Override
//        public void run() {
//
//                ports = SerialPort.getCommPorts();
//
//                for (int i = 0; i < ports.length; i++) {
//                    text.appendText(i + " >> " + ports[i].getSystemPortName() + "\n");
//                }
//
//                buff = new byte[8];
//                bytes = 8;
////                comPort.readBytes(buff, bytes);
////                while (comPort.isOpen()) {
////                    comPort.readBytes(buff, bytes);
////                    text.appendText(new String(buff));
////                }
//
//        }
//    }

//    class ReadBytesOfCOMPort implements Runnable {
//
//        @Override
//        public void run() {
//            try {
//                lock.acquire();
//                comPort.readBytes(buff, bytes);
//                while (comPort.isOpen()) {
//                    comPort.readBytes(buff, bytes);
//                    text.appendText(new String(buff));
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    public static void main(String[] args) {
        launch(args);
    }
}