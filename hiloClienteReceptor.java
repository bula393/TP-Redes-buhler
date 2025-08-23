import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
public class hiloClienteReceptor extends Thread {
    DatagramSocket sock;
    public hiloClienteReceptor(DatagramSocket sock) {
        this.sock = sock;
    }
    public void run() {
        try {
            byte[] buffer = new byte[4096];
            boolean confirmation = true;
            FileOutputStream foss = new FileOutputStream("/home/alumno/Escritorio/prueba/carpetas/experimento.png");
            while (confirmation) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                sock.receive(packet);
                if (packet.getLength() == 0) {
                    confirmation = false;
                }
                foss.write(packet.getData(), 0, packet.getLength());
            }
            foss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sock.close();
        this.interrupt();
    }
    
}