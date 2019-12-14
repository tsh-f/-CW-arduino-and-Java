import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
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

        while(comPort.isOpen()){
            comPort.readBytes(buff, bytes);
            System.out.print(new String(buff));
        }

    }
}
