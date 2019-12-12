import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int tmp = 0;
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Введите номер COM порта: ");
        Arrays.stream(ports).forEach((x -> System.out.println((tmp + 1) + " " + x.getSystemPortName())));
        int index = (new Scanner(System.in)).nextInt() - 1;
        Arduino port = new Arduino(ports[index].getSystemPortName(), 9600);
        while (port.openConnection()){
            System.out.println(port.serialRead());
        }
    }
}
