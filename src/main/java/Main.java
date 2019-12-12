import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;
import gnu.io.CommPort;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int tmp = 0;
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Введите номер COM порта: ");
        for (SerialPort port : ports) {
            System.out.println(tmp + " " + port.getSystemPortName());
            tmp++;
        }
        int index = (new Scanner(System.in)).nextInt() - 1;
        Arduino port = new Arduino(ports[index].getSystemPortName(), 9600);
        Thread thread = new Thread(() -> {
            while (port.openConnection()) {

            }
        });
        thread.start();
        thread.join();

    }
}

