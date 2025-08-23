import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
public class hiloClienteEnvio extends Thread {
    DatagramSocket sock;
    public hiloClienteEnvio(DatagramSocket sock) {
        this.sock = sock;
    }
    public void enviar_archivo(InetAddress receptor,int port,String ruta_archivo) throws Exception {
        try (DatagramSocket socket = new DatagramSocket();
             FileInputStream fis = new FileInputStream(ruta_archivo)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, receptor, port);
                socket.send(packet);
            }

            // Enviar un paquete vacío para indicar fin de archivo
            DatagramPacket endPacket = new DatagramPacket(new byte[0], 0, receptor, port);
            socket.send(endPacket);

            System.out.println("Archivo enviado correctamente por UDP.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            byte[] inBuf = new byte[1000];
            DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
            sock.receive(inPkt);
            hiloClienteEnvio hilo = new hiloClienteEnvio(sock);
            hilo.start();
            enviar_archivo(inPkt.getAddress(), inPkt.getPort(),inPkt.getData().toString());
            System.out.println("Archivo enviado.");
            sock.close();
            this.interrupt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}