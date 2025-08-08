import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



public class persona1 {

    public void enviar_lista(){
        File carpeta = new File("ruta_de_la_carpeta");
        String lista_archivos;

        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            System.out.println("Archivos y directorios en la carpeta:");
            for (File archivo : archivos) {
                lista_archivos += archivo.getName() + ',';
            }
        }

        byte ip[] = {(byte) 127,0,0, (byte) 1};
        InetAddress server = InetAddress.getByAddress(ip);
        byte[] outBuf = lista_archivos..;
        DatagramPacket outPkt = new DatagramPacket(outBuf, outBuf.length,server, 8888);
        sock.send(outPkt);
        byte[] inBuf = new byte[1000];
        DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
        sock.receive(inPkt);
        System.out.println("Recevied...");

        String reply = new String(inBuf, 0, inPkt.getLength(), "US-ASCII");
        System.out.println(reply);
        sock.close();
    }
}

    public static void main(String args[]) throws Exception {
        File carpeta = new File("ruta_de_la_carpeta");
        String lista_archivos

        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            System.out.println("Archivos y directorios en la carpeta:");
            for (File archivo : archivos) {
                System.out.println(archivo.getName());
            }
        }
        DatagramSocket sock = new DatagramSocket();
        Scanner s1 = new Scanner(System.in);
        String ruta_archivo =  s1.nextLine();
        File archivo = new File(ruta_archivo);
        Path archivo_salida = Paths.get(ruta_archivo);
        byte ip[] = {(byte) 127,0,0, (byte) 1};
        InetAddress server = InetAddress.getByAddress(ip);
        byte[] outBuf = archivo_salida.;
        DatagramPacket outPkt = new DatagramPacket(outBuf, outBuf.length,server, 8888);
        sock.send(outPkt);
        byte[] inBuf = new byte[1000];
        DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
        sock.receive(inPkt);
        System.out.println("Recevied...");

        String reply = new String(inBuf, 0, inPkt.getLength(), "US-ASCII");
        System.out.println(reply);
        sock.close();
        System.out.println("Socket closed...");
}
